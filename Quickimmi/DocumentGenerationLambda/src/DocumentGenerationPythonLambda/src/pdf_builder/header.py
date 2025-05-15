from reportlab.lib.colors import grey
from reportlab.lib.pagesizes import letter  # type: ignore


class HeaderCreator:
    def __init__(self, canvas, margin=80, font_name="Helvetica", font_size=9):
        self.canvas = canvas
        self.margin = margin
        self.font_name = font_name
        self.font_size = font_size
        self.width, self.height = letter
        self.section_width = (self.width - 2 * self.margin) / 3
        self.header_data = None
        # Save the original showPage method
        self.original_show_page = self.canvas.showPage
        # Override the showPage method
        self.canvas.showPage = self.show_page_with_header

    def draw_header(self, data):
        if not data:
            return
        self.canvas.setFont(self.font_name, self.font_size)
        # Define positions for logo, contact, and location
        logo_x = self.margin + self.section_width / 2
        contact_x = self.margin + self.section_width * 1.5
        location_x = self.margin + self.section_width * 2.3

        # Draw the logo centered in the first section
        logo_width = 150
        logo_height = 40
        self.canvas.drawImage(
            data[0],
            logo_x - logo_width / 2,
            self.height - 80,
            width=logo_width,
            height=logo_height,
            mask="auto",
            preserveAspectRatio=True,
        )

        # Draw contact details and office locations
        self.draw_centered_text(data[1], contact_x, self.height - 50)
        self.draw_centered_text(data[2], location_x, self.height - 50)

        # Draw shorter and grey separator lines between the sections
        self.canvas.setStrokeColor(grey)  # Set the color of the line to grey
        self.canvas.line(
            self.margin + self.section_width,
            self.height - 40,
            self.margin + self.section_width,
            self.height - 70,
        )
        self.canvas.line(
            self.margin + self.section_width * 2,
            self.height - 40,
            self.margin + self.section_width * 2,
            self.height - 70,
        )

    def draw_centered_text(self, text, center_x, start_y):
        lines = text.split("\n")
        max_width = max(
            self.canvas.stringWidth(line, self.font_name, self.font_size)
            for line in lines
        )
        current_y = start_y
        for line in lines:
            self.canvas.drawString(center_x - max_width / 2, current_y, line)
            current_y -= 12  # Line height adjustment

    def show_page_with_header(self):
        """
        Custom showPage method that draws the header before showing a new page.
        """
        if self.header_data:
            self.draw_header(self.header_data)
        self.original_show_page()  # Call the original showPage method

    def set_header_data(self, header_data):
        self.header_data = header_data
