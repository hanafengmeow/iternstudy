from src.content_composer import ContentComposer
from src.utils.template_utils import update_template_body, update_template_checkbox, update_template_format
from src.utils.loader_utils import load_json
from src.pdf_builder.assembler import DocumentAssembler
from src.constants import Constants
from src.utils.s3_utils import S3Utils
from src.database.database_utils import DatabaseUtils
from src.database.models import Document
from src.data_processor import DataProcessor
from datetime import datetime
from reportlab.lib.pagesizes import letter


class DocumentOrchestrator:
    @staticmethod
    def build_asylum_cover_letter(data):
        template = load_json(Constants.ASYLUM_COVERLETTER_TEMPLATE)

        # Update data
        content_composer = ContentComposer()
        # data["letter"] = content_composer.generate_asylum_cover_letter(data)
        data["letter"] = data["coverLetter"]
        data["shipping_address"] = content_composer.get_asylum_shipping_address(data)
        # data["letter"] = letter
        # data["shipping_address"] = shipping_address
        data["letter_date"] = datetime.now().strftime("%m/%d/%Y")
        # Update template based on updated data
        content_template = update_template_body(template["template"], data)
        # Generate the letter
        doc_assembler = DocumentAssembler(data, template, content_template)
        doc_assembler.generate_and_upload_letter()
        return

    @staticmethod
    def build_eoir_cover_letter(data):
        # Load EOIR Cover Letter Template
        template = load_json(Constants.EOIR_COVERLETTER_TEMPLATE)
        # Process Data
        data = DataProcessor().process_eoir_coverletter_data(data)
        # Update template based on updated data
        content_template = update_template_body(template["content"], data)
        # Generate the letter
        doc_assembler = DocumentAssembler(data, template, content_template)
        doc_assembler.generate_and_upload_letter()
        return

    @staticmethod
    def build_eoir_proof_of_service(data):
        # Load EOIR Proof of Service Template
        template = load_json(Constants.EOIR_PROOFOFSERVICE_TEMPLATE)
        # Process Data
        data = DataProcessor().process_eoir_proofofservice_data(data)
        data["letter_date"] = datetime.now().strftime("%B %d, %Y")
        # Update template based on updated data
        content_template = update_template_body(template["content"], data)
        # Generate the letter
        doc_assembler = DocumentAssembler(data, template, content_template)
        doc_assembler.generate_and_upload_letter()
        return

    @staticmethod
    def build_asylum_pleading(data):
        # Load EOIR Pleading Template
        template = load_json(Constants.EOIR_PLEADING_TEMPLATE)
        # Process Data
        data = DataProcessor().process_eoir_pleading_data(data)
        data["letter_date"] = datetime.now().strftime("%B %d, %Y")
        # Update template based on updated data
        content_template = update_template_body(template["content"], data)
        checkbox_template = update_template_checkbox(template["checkbox"], data)
        # Generate the letter
        doc_assembler = DocumentAssembler(
            data, template, content_template, checkbox_template
        )
        doc_assembler.generate_and_upload_letter()
        return
    
    @staticmethod
    def build_english_marriage_license(data):
        # Load Marriage License Template
        template = load_json(Constants.MARRIAGE_LICENSE_TEMPLATE)
        # Process Data
        # data = DataProcessor().process_marriage_license_data(data)
        # Update template based on updated data
        content_template = update_template_body(template["content"], data)
        # Generate the letter
        doc_assembler = DocumentAssembler(data, template, content_template)
        doc_assembler.generate_and_upload_letter()
        return

    @staticmethod
    def build_personal_statement(data):
        if data["language"] == "English":
            template = load_json(Constants.ASYLUM_PERSONAL_STATEMENT_TEMPLATE)
            # Update data
            data["letter_date"] = datetime.now().strftime("%m/%d/%Y")
            english_data = data.copy()
            english_data["title"] = "Personal Statement"
            english_data["personal_statement"] = data["personalStatement"]
            english_data["documentName"] = "personal_statement_english"
            english_data["closingStatement"] = "Yours Sincerely,"

            # Update template based on updated data
            content_template = update_template_body(template["template"], english_data)

            # Generate the letter
            doc_assembler = DocumentAssembler(english_data, template, content_template)
            doc_assembler.generate_and_upload_letter()
        else:
            template = load_json(Constants.ASYLUM_PERSONAL_STATEMENT_TEMPLATE)
            # Update data
            data["letter_date"] = datetime.now().strftime("%m/%d/%Y")
            original_data = data.copy()
            original_data["title"] = "个人陈述"
            original_data["personal_statement"] = data["personalStatementInOriginalLanguage"]
            original_data["documentName"] = "personal_statement_original"
            original_data["closingStatement"] = "感谢您的宝贵时间。"

            # Update template based on updated data
            content_template = update_template_body(template["template"], original_data)

            # Generate the letter
            doc_assembler = DocumentAssembler(original_data, template, content_template)
            doc_assembler.generate_and_upload_letter()
        return
    
    @staticmethod
    def build_certificate_of_translation(data):
        template = load_json(Constants.ASYLUM_CERTIFICATE_OF_TRANSLATION_TEMPLATE)
        # Update data
        data["letter_date"] = datetime.now().strftime("%m/%d/%Y")
        data["attorney"]["fullAddress"] = data["attorney"]["address"] + ", " + data["attorney"]["city"] + ", " + data["attorney"]["state"] + " " + data["attorney"]["zip"]
        data["attorney"]["formattedPhone"] = data["attorney"]["phone"][:3] + "-" + data["attorney"]["phone"][3:6] + "-" + data["attorney"]["phone"][6:]
        data["documentTitle"] = data["document"].replace("_", " ").title()

        # Update template based on updated data
        content_template = update_template_body(template["template"], data)
        template = update_template_format(template, data)

        print(template)

        # Generate the letter
        doc_assembler = DocumentAssembler(data, template, content_template)
        doc_assembler.generate_and_upload_letter()

        return