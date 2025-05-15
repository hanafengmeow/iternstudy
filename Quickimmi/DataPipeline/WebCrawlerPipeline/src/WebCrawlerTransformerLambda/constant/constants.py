import os

# Task Status
TASK_START = "START"
TASK_FINISH = "FINISH"
TASK_FAIL = "FAIL"
# Content Type
HTML_CONTENT_TYPE = "text/html"
PDF_CONTENT_TYPE = "application/pdf"
# WebCrawlerFinishQueue SQS
FINISH_QUEUE_TASKID = "taskId"
FINISH_QUEUE_URLID = "urlId"
FINISH_QUEUE_SOURCE = "source"
FINISH_QUEUE_S3KEY = "s3Key"
FINISH_QUEUE_PARENT3KEY = "parent3Key"
# DynamoDB raw page
RAW_PAGE_DB_NAME = os.environ.get("PAGECRAWLINGTASK_TABLE_NAME")
RAW_PAGE_TASKID = "taskId"
RAW_PAGE_URLID = "urlId"
RAW_PAGE_CONTENTTYPE = "contentType"
RAW_PAGE_CREATEAT = "createAt"
RAW_PAGE_DEPTH = "depth"
RAW_PAGE_PARENT = "parent"
RAW_PAGE_S3KEY = "s3Key"
RAW_PAGE_STATUS = "status"
# DynamoDB transformer data, DocumentMainTable data, UrlToPageFigerPrint data
TRANSFORMER_DATA_DB_NAME = os.environ.get("PAGETRANSFORMTASK_TABLE_NAME")
DocumentMainTable_DATA_DB_NAME = os.environ.get("DOCUMENT_TABLE_NAME")
UrlToPageFigerPrint_DATA_DB_NAME = os.environ.get("URLTOPAGEFIGERPRINT_TABLE_NAME")
TRANSFORMER_TASKSID = "taskId"
TRANSFORMER_PAGE_FINGERPRINT = "pageFingerprint"
TRANSFORMER_URL = "link"
TRANSFORMER_SOURCE = "source"
TRANSFORMER_TITLE = "title"
TRANSFORMER_S3KEY = "s3Key"
TRANSFORMER_LastUpdateDate = "lastUpdateDate"
SOURCE_To_PAGE_LSI = "SourceFingerprintLSI"
# s3 raw page
RAWPAGE_BUCKET_NAME = os.environ.get("WEBCRAWLERWORKER_BUCKET_NAME")
# s3 transformer data
TRANSFORMER_BUCKET_NAME = os.environ.get("WEBCRAWLERTRANSFORMER_BUCKET_NAME")
# source
USCIS_SOURCE = "uscis"
# wegreened
wegreened_SOURCE = "wegreened"
wegreened_eb1 = "eb1"
wegreened_niw = "niw"
wegreened_type = "greenCardType"
# research jouranl
RESEARCH_JOURNAL_SOURCE = "research-journal"
RESEARCH_CONFERENCE_SOURCE = "research-conference"
RESEARCH_SCIENTISTS_SOURCE = "research-scientists"
RESEARCH_UNIVERSITY_SOURCE = "research-university"
# justia
JUSTIA_SOURCE = "justia-lawyer"
