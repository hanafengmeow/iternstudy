import fitz
import PyPDF2
import io
from PIL import Image
from datetime import datetime
import re

class PDFParser:
    @staticmethod
    def get_text_with_condition(span, title_font_size, title_prefix, subtitle_prefix, is_color_match):
        text = span['text']
        if span['size'] == title_font_size:
            return f"{title_prefix}{text} " if title_prefix else text
        elif is_color_match:
            return f"{subtitle_prefix}{text} " if subtitle_prefix else text
        return text

    @staticmethod
    def is_color_match(rect, img, img_border, background_color, zoom_f):
        """
        Determine if the color of a designated region in an image closely resembles a specified background color.
        """
        rect = fitz.Rect(*tuple(xy * zoom_f for xy in rect))
        if img_border.contains(rect):
            color = img.getpixel((rect.x0, rect.y0))
            bg_color = tuple(c / 255 for c in color)
            return all(abs(bg_color[i] - background_color[i]) < 0.01 for i in range(3))
        return False

    @staticmethod
    def extract_text_from_PDF_page(page, background_color, title_font_size, title_prefix, subtitle_prefix, zoom_f=3):
        """
        Extract title and content from lines within blocks based on a specified background color and font size in a PDF page.
        """
        markdown_text = ""

        all_words = page.get_text("dict", sort=True)

        mat = fitz.Matrix(zoom_f, zoom_f)
        pixmap = page.get_pixmap(matrix=mat)
        img = Image.open(io.BytesIO(pixmap.tobytes()))
        img_border = fitz.Rect(0, 0, img.width, img.height)

        for block in all_words['blocks']:
            if block['type'] == 0:
                is_subtitle = PDFParser.is_color_match(block['bbox'], img, img_border, background_color, zoom_f)
                for line in block['lines']:
                    linetext = ""
                    for span in line['spans']:
                        extracted_text = PDFParser.get_text_with_condition(span, title_font_size, title_prefix, subtitle_prefix, is_subtitle)
                        linetext += extracted_text
                    markdown_text += f"{linetext}  \n"

        return markdown_text

    @staticmethod
    def fetch_PDF_parser_config():
        # TODO get config from APPConfig in future
        # try:
        #     # Create an AppConfig client
        #     client = boto3.client('appconfig')
        #
        #     # Retrieve configuration from AWS AppConfig
        #     response = client.get_configuration(
        #         Application="PDF-Parser-config",
        #         Environment="PDF",
        #         Configuration="PDF-Parser-config",
        #         ClientId="PDF_Parser_ID"
        #     )
        #
        #     config = response['Content'].read().decode('utf-8')
        #     pdf_parser_config = json.loads(config)
        # except Exception as e:
        #     logger.info(f"An error occurred when retrieving PDF parser config from AppConfig: {str(e)}")
        pdf_parser_config = {
            'background_color': (0.8784, 0.8784, 0.8784),
            'title_font_size': 14
        }  # Set default values in case of an exception
        return pdf_parser_config

    @staticmethod
    def get_markdown_page_from_pdf(pdf_binary_data):
        pdf_parser_config = PDFParser.fetch_PDF_parser_config()
        pdf_document = fitz.open(stream=pdf_binary_data, filetype="pdf")
        md_text = ""

        for page_num in range(len(pdf_document)):
            page = pdf_document[page_num]
            extracted_markdown = PDFParser.extract_text_from_PDF_page(
                page,
                background_color=pdf_parser_config.get('background_color', (0.8784, 0.8784, 0.8784)),
                title_font_size=pdf_parser_config.get('title_font_size', 14),
                title_prefix="# ",
                subtitle_prefix="## "
            )
            md_text += extracted_markdown

        pdf_reader = PyPDF2.PdfReader(io.BytesIO(pdf_binary_data))

        if pdf_reader.is_encrypted:
            return "", md_text

        date = pdf_reader.metadata.get('/ModDate', None)
        if date:
            last_update_date = datetime.strptime(date.replace("'", ""), "D:%Y%m%d%H%M%S%z").strftime("%m/%d/%Y")
        else:
            last_update_date = None
        return last_update_date, md_text

    @staticmethod
    def is_instr_PDF(url):
        regex_pattern = r"instr\.pdf$"
        return re.search(regex_pattern, url)