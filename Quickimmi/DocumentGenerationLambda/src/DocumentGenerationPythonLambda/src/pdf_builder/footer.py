from reportlab.lib.units import inch
from reportlab.lib.pagesizes import letter


class FooterCreator:
    def __init__(self, canvas, template, format_settings):
        self.canvas = canvas
        self.footer_data = template.get("footer", [])
        format_settings = format_settings.get("footer", {})

        self.original_show_page = canvas.showPage
        self.canvas.showPage = self.show_page_with_footer

        # Extract formatting settings from format_config with defaults
        self.font_name = format_settings.get("font_name", "Helvetica")
        self.font_size = format_settings.get("font_size", 9)
        self.x_position = format_settings.get("x_position", 1 * inch)
        self.y_position = format_settings.get("y_position", 1 * inch)
        self.line_height = format_settings.get("line_height", self.font_size * 1.5)
        self.alignment = format_settings.get("alignment", "left")
        self.show_page_number = format_settings.get("show_page_number", False)

    def draw_footer(self):
        footer_y = self.y_position
        page_width = letter[0]
        self.canvas.setFont(self.font_name, self.font_size)

        for line in reversed(self.footer_data):
            x = self.calculate_text_position(page_width, line, self.alignment)
            self.canvas.drawString(x, footer_y, line)
            footer_y += self.line_height

        if self.show_page_number:
            page_num = f"{self.canvas.getPageNumber()}"
            x_page_num = self.calculate_text_position(
                page_width, page_num, self.alignment
            )
            self.canvas.drawString(x_page_num, footer_y, page_num)

    def calculate_text_position(self, page_width, text, alignment):
        if alignment == "left":
            return self.x_position
        elif alignment == "center":
            return (
                page_width
                - self.canvas.stringWidth(text, self.font_name, self.font_size)
            ) / 2
        elif alignment == "right":
            return (
                page_width
                - self.x_position
                - self.canvas.stringWidth(text, self.font_name, self.font_size)
            )

    def show_page_with_footer(self):
        """
        Custom showPage method that draws the footer before showing a new page.
        """
        self.draw_footer()
        self.original_show_page()

    def finalize_footers(self):
        """
        Ensure the footer is added to the last page of the document.
        """
        self.draw_footer()
