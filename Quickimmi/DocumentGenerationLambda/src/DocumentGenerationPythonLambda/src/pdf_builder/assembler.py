import io
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from src.pdf_builder.header import HeaderCreator
from src.pdf_builder.footer import FooterCreator
from src.pdf_builder.body import BodyCreator
from src.pdf_builder.formatter import Formatter
from src.utils.s3_utils import S3Utils
from src.utils.string_utils import capitalize_first_letter
from src.constants import Constants
from src.database.database_utils import DatabaseUtils
from src.database.models import Document
import os
import time
import os
import logging


class DocumentAssembler:
    def __init__(self, data, template, content_template, checkbox_template=None):
        self.content_template = content_template
        self.format_settings = template.get("format", {})
        self.checkbox = checkbox_template
        self.data = data
        # self.file_name = self.get_file_name()
        self.file_name = data["documentName"] + ".pdf"
        self.buffer = io.BytesIO()
        self.canvas = canvas.Canvas(self.buffer, pagesize=letter)
        # Register Chinese font
        font_path = "resources/font/NotoSerifSC-Regular.ttf"
        pdfmetrics.registerFont(TTFont("NotoSerifSC-Regular", font_path))
        font_path = "resources/font/NotoSerifSC-Bold.ttf"
        pdfmetrics.registerFont(TTFont("NotoSerifSC-Bold", font_path))
        # Set components
        self.formatter = Formatter(self.canvas, self.data["documentType"])
        self.header_creator = HeaderCreator(self.canvas)
        self.header_creator.set_header_data(content_template["header"])
        self.footer_creator = FooterCreator(
            self.canvas, content_template, self.format_settings
        )
        self.body_creator = BodyCreator(self.canvas, self.format_settings)
        self.s3_utils = S3Utils()
        self.type = data["documentName"]
        # if data["documentType"] == "eoir_coverletter" or data["documentType"] == "eoir_proofofservice":
        #     self.type = data["documentType"] + "_" + "_".join(data["document"].lower().split())

    def generate_and_upload_letter(self, save_local=False):
        try:
            # First generate the document body
            self.generate_letter_body(self.content_template["body"])
            # Apply final formatting and save. Ensure the formatter is applied to the last page
            self.finalize_document_format()
            self.canvas.save()
            if save_local:
                # Save the file locally for testing
                self.save_to_local()
            else:
                # Save to S3 and get the uri
                self.buffer.seek(0)
                region_name = os.environ.get('AWS_REGION')
                generated_document_bucket = Constants.REGION_ENV_MAP.get(region_name).generated_document_bucket
                self.s3_uri = self.s3_utils.save_to_s3(
                    bucket_name=generated_document_bucket,
                    user_id=self.data["userId"],
                    case_id=self.data["caseId"],
                    file_name=self.file_name,
                    content=self.buffer.getvalue(),
                    content_type="application/pdf",
                )
                self.update_database("Success")
        except Exception as e:
            print(f"An error occurred: {e}")
            self.update_database("Failed", str(e))

    def finalize_document_format(self):
        # Directly draw the formatting if not already applied
        self.formatter.draw()

    def get_file_name(self):
        """
        Generates a file name based on the documentType and document label.
        """
        document_type = self.data["documentType"]

        # Check if the document type requires special formatting for the file name.
        if document_type in ["eoir_coverletter", "eoir_proofofservice"]:
            # Remove spaces and convert to upper case
            document_label = self.data.get("document", "")
            document_label = capitalize_first_letter(document_label, delimiter="_")
            return f"{document_label}_{document_type}.pdf"

        # Default file name format for other types.
        return f"{capitalize_first_letter(document_type)}.pdf"

    def generate_letter_body(self, body_data):
        for section, content in body_data.items():
            body_format_settings = self.format_settings.get("body", {})
            default_settings = body_format_settings.get("default", {})

            # Apply font settings if available for the section, otherwise default
            section_settings = body_format_settings.get(section, default_settings)
            self.body_creator.update_section_font_settings(section_settings)

            # Check if we need to start a new page based on section configuration before drawing any content
            if self.body_creator.start_new_page:
                self.body_creator.set_a_new_page()

            if content:
                for item in content:
                    item = str(item)
                    # Handle checkbox items differently
                    if item.startswith("checkbox"):
                        key = item.split(".")[1]
                        item = self.checkbox.get(key, [])

                    self.body_creator.draw_content(item)
                    # Adjust y_position as needed
                    self.body_creator.y_position -= self.body_creator.line_height
                # Insert a section break with extra line spacing
                self.body_creator.draw_section_break()

    def save_to_local(self):
        local_file_path = os.path.join("output", self.file_name)
        os.makedirs(os.path.dirname(local_file_path), exist_ok=True)
        with open(local_file_path, "wb") as local_file:
            local_file.write(self.buffer.getvalue())
        print(f"File saved locally at: {local_file_path} \n\n")

    def update_database(self, generation_status, error_message=None):
        # Init database utils
        db_utils = DatabaseUtils()
        self.current_time = int(time.time() * 1000)

        # Check if the document already exists by case id and type
        existing_document = db_utils.get_document_by_case_id_and_type(
            self.data["caseId"], self.type
        )
        if existing_document:
            # Update the existing document record
            if generation_status == "Failed":
                db_utils.update_document(
                    existing_document.id,
                    self.current_time,
                    generation_status,
                    error_message,
                )
            else:
                db_utils.update_document(existing_document.id, self.current_time, generation_status, self.s3_uri)
        else:
            # Document does not exist
            logging.error(
                f"Document missing from document table"
            )
            raise ValueError(
                f"Document missing from document table"
            )
        document_id = existing_document.id
        print(f"Document id: {document_id} created successfully")
        s3_location = self.s3_uri
        print(f"Document s3 location: {s3_location}")
        task_id = self.data["taskId"]
        print(f"Task id: {task_id}")
        db_utils.update_form_generation_task(
            task_id, document_id, s3_location, generation_status, self.current_time
        )

    # def build_document(self):
    #     document = Document(
    #         user_id=self.data["userId"],
    #         case_id=self.data["caseId"],
    #         status="uploaded",
    #         type=self.type,
    #         file_type="pdf",
    #         name=self.file_name,
    #         identify="applicant",
    #         created_by="System",
    #         s3_location=self.s3_uri,
    #         created_at=self.current_time,
    #         updated_at=self.current_time,
    #     )
    #     return document
