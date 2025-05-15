import logging
from constant.constants import *
from utiles.S3Utils import S3Utils
import json
from HTMLParserManager import HTMLParserManager
from utiles.Parser.PDFParser import PDFParser
from utiles.DynamoDBUtils import DynamoDBUtils
import traceback
import re
from urllib.parse import urlparse

logger = logging.getLogger()
logger.setLevel(logging.INFO)

raw_pages_table = DynamoDBUtils(RAW_PAGE_DB_NAME, logger, RAW_PAGE_TASKID, RAW_PAGE_URLID)
transformer_data_table = DynamoDBUtils(TRANSFORMER_DATA_DB_NAME, logger, TRANSFORMER_TASKSID, TRANSFORMER_PAGE_FINGERPRINT)
documentMainTable_data_table = DynamoDBUtils(DocumentMainTable_DATA_DB_NAME, logger, TRANSFORMER_SOURCE, TRANSFORMER_PAGE_FINGERPRINT)
urlToPageFingerPrint_data_table = DynamoDBUtils(UrlToPageFigerPrint_DATA_DB_NAME, logger, TRANSFORMER_SOURCE, TRANSFORMER_URL)
raw_page_s3 = S3Utils(logger, RAWPAGE_BUCKET_NAME)
transformer_s3 = S3Utils(logger, TRANSFORMER_BUCKET_NAME)


# event: {
#     "taskId": "wegreened-1700364906303",
#     "source": "wegreened",
#     "urlId": "https://www.wegreened.com/blog/sustainable-development",
#     "s3Key": "wegreened-1700364906303/www-wegreened-com-blog-sustainable-development"}
#
def handle_request(event, context):
    try:
        for record in event['Records']:
            logger.info("starting process message: %s", record["body"])
            body = json.loads(record["body"])
            raw_item = get_raw_item_by_keys(body[FINISH_QUEUE_TASKID], body[FINISH_QUEUE_URLID])
            s3keys = []
            s3keys.append(body[FINISH_QUEUE_S3KEY])
            if body[FINISH_QUEUE_SOURCE] == "research-university":
                s3keys.append(body[FINISH_QUEUE_PARENT3KEY])
            process_item(raw_item, body[FINISH_QUEUE_SOURCE], s3keys)
        logger.info('Successfully processed {} records.'.format(len(event['Records'])))
    except Exception as e:
        error_message = "Failed to process event: {} - {}".format(json.dumps(event), str(e)) + "\n" + traceback.format_exc()
        logger.error(error_message)
        raise e

def get_raw_item_by_keys(taskID, urlID):
    raw_item = raw_pages_table.get_by_key(pk_value=taskID, sk_value=urlID)
    if not raw_item:
        raise Exception(f"Unable to find item for taskID: {taskID}, urlID: {urlID}")
    return raw_item

def process_item(raw_item, source, s3keys):
    try:
        taskID = raw_item[RAW_PAGE_TASKID]
        urlID = raw_item[RAW_PAGE_URLID]
        content_type = raw_item[RAW_PAGE_CONTENTTYPE]
        s3key = s3keys[0]
        object_binary_data = raw_page_s3.get_object_by_s3_key(s3key)
        if HTML_CONTENT_TYPE in content_type:
            if source == "research-university":
                parent3key = s3keys[1]
                parent_binary_data = raw_page_s3.get_object_by_s3_key(parent3key)
                last_update_date, article_md = process_2html_content(raw_item, s3keys, object_binary_data, parent_binary_data, taskID, source)
            else:
                last_update_date, article_md = process_html_content(raw_item, s3key, object_binary_data, taskID, source)
        elif PDF_CONTENT_TYPE in content_type:
            if PDFParser.is_instr_PDF(urlID) and source == USCIS_SOURCE:
                last_update_date, article_md = process_pdf_content(raw_item, s3key, object_binary_data, taskID)
            else:
                logger.warning(
                    f"PDF URL for item {taskID}, {urlID} does not match the expected pattern: instr.pdf in {USCIS_SOURCE}")
                return
        else:
            logger.info(f"Unsupported content type {content_type} for item {taskID}, {urlID}")
            return

        #pagefingerprint = content_body_md5
        content_body_md5 = raw_page_s3.get_content_md5(article_md)

        if not content_body_md5:
            logger.error("Fail to hash content with MD5 for raw item %s", raw_item)
            return

        will_save = False
        document_item = documentMainTable_data_table.get_by_key(source, content_body_md5)
        if document_item:
            update_UrlToPageFigerPrint_data_table(source, urlID, content_body_md5, document_item[TRANSFORMER_S3KEY])
            # item exist on the document DB, update its shortest url
            if len(document_item[TRANSFORMER_URL]) > len(urlID):
                documentMainTable_data_table.update_item_attribute(source, content_body_md5, TRANSFORMER_URL, urlID)
            # if item exist on the transformer DB, update its shortest url
            transformer_item = transformer_data_table.get_by_key(source, content_body_md5)
            if transformer_item and len(transformer_item[TRANSFORMER_URL] > len(urlID)):
                transformer_data_table.update_item_attribute(taskID, content_body_md5, TRANSFORMER_URL, urlID)

            logger.info(f"Stop continuing to parse duplicate document item {source}, {taskID} {content_body_md5}, {urlID}")
        else:
            old_url_Page_Item = urlToPageFingerPrint_data_table.get_by_key(source, urlID)
            if old_url_Page_Item:
                documentMainTable_data_table.delete_by_key(source, old_url_Page_Item[TRANSFORMER_PAGE_FINGERPRINT])
                transformer_s3.delete_object_by_s3_key(old_url_Page_Item[TRANSFORMER_S3KEY])
                urlToPageFingerPrint_data_table.query_and_delete_by_lsi(SOURCE_To_PAGE_LSI, TRANSFORMER_SOURCE, source, TRANSFORMER_PAGE_FINGERPRINT, old_url_Page_Item[TRANSFORMER_PAGE_FINGERPRINT])
                logger.info(f"Delete old item {source}, {urlID}, {old_url_Page_Item[TRANSFORMER_PAGE_FINGERPRINT]} from main DB")
            will_save = True

        if not will_save:
            return

        save_article(s3key, raw_item, article_md, content_body_md5, last_update_date, source)


    except Exception as e:
        stack_trace = traceback.format_exc()
        error_message = "\n" + stack_trace
        logger.error("Failed to process item %s, %s %s", raw_item, str(e), error_message)
        raise e

def process_html_content(raw_item, s3key, html_binary_data, taskID, source):
    try:
        html = html_binary_data.decode('utf-8')
        last_update_date, article_md = HTMLParserManager().get_parser(source).get_markdown_page_from_html(html)
        logger.info(f"HTML Process: Process HTML content with s3Key: {s3key} URL: {raw_item[RAW_PAGE_URLID]}")
        return last_update_date, article_md
    except Exception as e:
        logger.error("HTML Process: %s failed to process html item: %s with error %s",
                     taskID, json.dumps(raw_item[RAW_PAGE_URLID], indent=2), str(traceback.format_exc()))
        raise e

def process_2html_content(raw_item, s3keys, child_binary_data, parent_binary_data, taskID, source):
    try:
        child_html = child_binary_data.decode('utf-8')
        parent_html = parent_binary_data.decode('utf-8')
        last_update_date, article_md = HTMLParserManager().get_parser(source).get_markdown_page_from_html(parent_html, child_html)
        logger.info(f"HTML Process: Process HTML content with s3Key: {s3keys} URL: {raw_item[RAW_PAGE_URLID]}")
        return last_update_date, article_md
    except Exception as e:
        logger.error("HTML Process: %s failed to process html item: %s with error %s",
                     taskID, json.dumps(raw_item[RAW_PAGE_URLID], indent=2), str(traceback.format_exc()))
        raise e

def process_pdf_content(raw_item, s3key, pdf_binary_data, taskID):
    try:
        last_update_date, article_md = PDFParser.get_markdown_page_from_pdf(pdf_binary_data)
        logger.info(f"PDF Process: Process PDF content with s3Key: {s3key} URL: {raw_item[RAW_PAGE_URLID]}")
        return last_update_date, article_md
    except Exception as e:
        logger.error("PDF Process: %s failed to process pdf item: %s with error %s",
                     taskID, json.dumps(raw_item[RAW_PAGE_URLID], indent=2), str(traceback.format_exc()))
        raise e

def save_article(s3key, raw_item, article_md, content_body_md5, last_update_date, source):
    content_title = extract_title_from_markdown(article_md)
    document_article = build_documentMainTable_article(raw_item[RAW_PAGE_URLID], content_body_md5, last_update_date, source, s3key, content_title)
    if source == wegreened_SOURCE:
        document_article[wegreened_type] = extract_weGreened_url_type(raw_item[RAW_PAGE_URLID])

    transformer_article = build_transformer_article(raw_item[RAW_PAGE_TASKID], content_body_md5, raw_item[RAW_PAGE_URLID], source, s3key, content_title)

    documentMainTable_data_table.insert(document_article)
    transformer_data_table.insert(transformer_article)
    update_UrlToPageFigerPrint_data_table(source, raw_item[RAW_PAGE_URLID], content_body_md5, s3key)
    transformer_s3.save_object_to_s3(s3key, article_md)

def build_transformer_article(taskID, content_body_md5, urlID, source, s3Key, title):
    transformer_item = {}
    transformer_item[TRANSFORMER_TASKSID] = taskID
    transformer_item[TRANSFORMER_PAGE_FINGERPRINT] = content_body_md5
    transformer_item[TRANSFORMER_URL] = urlID
    transformer_item[TRANSFORMER_SOURCE] = source
    transformer_item[TRANSFORMER_S3KEY] = s3Key
    transformer_item[TRANSFORMER_TITLE] = title
    return transformer_item

def update_UrlToPageFigerPrint_data_table(source, url, content_body_md5, s3Key):
    data = {}
    data[TRANSFORMER_SOURCE] = source
    data[TRANSFORMER_URL] = url
    data[TRANSFORMER_PAGE_FINGERPRINT] = content_body_md5
    data[TRANSFORMER_S3KEY] = s3Key
    urlToPageFingerPrint_data_table.insert(data)

def build_documentMainTable_article(url, content_body_md5, last_update_date, source, s3key, content_title):
    article = {}
    article[TRANSFORMER_SOURCE] = source
    article[TRANSFORMER_PAGE_FINGERPRINT] = content_body_md5
    article[TRANSFORMER_URL] = url
    article[TRANSFORMER_S3KEY] = s3key
    article[TRANSFORMER_LastUpdateDate] = last_update_date
    article[TRANSFORMER_TITLE] = content_title
    return article

def extract_title_from_markdown(markdown_text):
    title_pattern = r'^\#\s+(.*)'  # Assumes that the title starts with "# " and captures everything after it
    match = re.search(title_pattern, markdown_text, re.MULTILINE)
    if match:
        # Extract and return the title (group 1 of the match)
        return match.group(1).strip()
    else:
        return ""

def extract_weGreened_url_type(url):
    parsed_url = urlparse(url)
    path = parsed_url.path
    categories = [wegreened_eb1, wegreened_niw]
    matching_categories = [category for category in categories if f"/{category}/" in path]

    return matching_categories[0] if matching_categories and len(matching_categories) > 0 else ""