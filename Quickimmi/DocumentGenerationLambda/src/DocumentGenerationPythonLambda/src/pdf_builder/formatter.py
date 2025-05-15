from reportlab.lib.pagesizes import letter
from reportlab.platypus import Table, TableStyle
from reportlab.lib import colors


class Formatter:
    def __init__(self, canvas, document_type):
        self.canvas = canvas
        self.document_type = document_type
        self.width, self.height = letter
        self.original_show_page = canvas.showPage
        self.canvas.showPage = self.show_page_with_formatting
        self.current_page = 1

    def draw(self):
        if self.document_type in ["eoir_coverletter", "eoir_proofofservice"]:
            self.eoir_formatter()
        if self.document_type == "asylum_pleading":
            self.asylum_pleading_formatter()

    def eoir_formatter(self):
        # Your formatting code here
        self.draw_vertical_numbers_28()
        self.draw_vertical_lines()

        # Formatting that applies only to the first page
        if self.current_page == 1:
            self.draw_parentheses()
            self.draw_line(72, 258.9, 325, 258.9)

        # Formatting that applies only to the 2nd page
        if self.current_page == 2 and self.document_type == "eoir_proofofservice":
            # Data for 4 rows and 3 columns
            data = [["", "", ""] for _ in range(4)]
            # Table margins and column widths
            margin_x = 74
            column_widths_proportions = [0.25, 0.5, 0.25]
            # Table height
            table_y_position = 300
            table_height = 180
            row_heights_proportions = [0.25, 0.25, 0.25, 0.25]
            # Colors
            grid_color = colors.black
            background_color = colors.transparent

            # Draw table
            self.draw_table(
                data,
                margin_x,
                table_height,
                row_heights_proportions,
                grid_color,
                background_color,
                table_y_position,
                column_widths_proportions,
            )

    def asylum_pleading_formatter(self):
        self.draw_vertical_numbers_35()
        self.draw_vertical_lines()

    def show_page_with_formatting(self):
        self.draw()  # Draw formatting if the document type matches
        self.original_show_page()  # Continue with the original showPage functionality
        # Increment the page count after each new page is shown
        self.current_page += 1

    def draw_vertical_numbers_28(self):
        self.canvas.setFont("Helvetica", 8)
        self.canvas.setFillColor(colors.black)

        start_y = self.height - 91
        right_align_x = 56
        step = 22.35

        for num in range(1, 29):
            self.canvas.drawRightString(right_align_x, start_y, str(num))
            start_y -= step

    def draw_vertical_numbers_35(self):
        self.canvas.setFont("Helvetica", 8)
        self.canvas.setFillColor(colors.black)

        # Top starting point (fixed upper margin) and step calculation
        upper_margin = self.height - 80
        total_height = 647  # Example height: adjust based on the document requirements
        step = total_height / 35

        # Drawing numbers within the defined area
        for num in range(1, 35 + 1):
            position_y = upper_margin - (num - 1) * step
            self.canvas.drawRightString(56, position_y, str(num))

    def draw_vertical_lines(self):
        self.canvas.setLineWidth(1)
        self.canvas.setStrokeColor(colors.black)

        line_x1 = 64
        line_x2 = 68
        line_x3 = 576

        self.canvas.line(line_x1, 0, line_x1, self.height)
        self.canvas.line(line_x2, 0, line_x2, self.height)
        self.canvas.line(line_x3, 0, line_x3, self.height)

    def draw_parentheses(self):
        self.canvas.setFont("Times-Roman", 12)
        center_x = 326
        start_y = 426.5
        step = 13.8

        for i in range(13):
            self.canvas.drawCentredString(center_x, start_y, ")")
            self.last_y = start_y
            start_y -= step

    def draw_line(self, x1, y1, x2, y2):
        self.canvas.setLineWidth(1)
        self.canvas.setStrokeColor(colors.black)
        # Draw a line from the left margin to the bottom of the last parenthesis
        self.canvas.line(x1, y1, x2, y2)

    def draw_table(
        self,
        data,
        margin_x,
        table_height,
        row_heights_proportions,
        grid_color,
        background_color,
        table_y_position,
        column_widths_proportions,
    ):
        """
        Draw a customizable table.

        Args:
        - data (list of list of str): Content for the table cells.
        - margin_x (float): The left and right margin for the table's position.
        - row_heights (list of float): Heights of each row.
        - grid_color (reportlab.lib.colors.Color): Color of the grid lines.
        - background_color (reportlab.lib.colors.Color): Background color of the cells.
        - table_y_position (float): The y-coordinate for the table's position.
        - proportions (list of float): Proportions of each column (should sum to 1).
        """
        # Calculate table width based on the page width and margins
        table_width = self.width - 2 * margin_x
        column_widths = [table_width * p for p in column_widths_proportions]
        row_heights = [table_height * p for p in row_heights_proportions]

        # Create a table object with specified dimensions and data
        table = Table(data, colWidths=column_widths, rowHeights=row_heights)

        # Define style for the table
        style = TableStyle(
            [
                ("GRID", (0, 0), (-1, -1), 1, grid_color),  # Grid color
                ("BACKGROUND", (0, 0), (-1, -1), background_color),  # Background color
                ("ALIGN", (0, 0), (-1, -1), "CENTER"),  # Center align text in cells
                (
                    "VALIGN",
                    (0, 0),
                    (-1, -1),
                    "MIDDLE",
                ),  # Middle align text vertically in cells
                ("FONTNAME", (0, 0), (-1, -1), "Helvetica"),  # Font of the text
                ("FONTSIZE", (0, 0), (-1, -1), 12),  # Font size of the text
            ]
        )

        table.setStyle(style)
        # Draw the table at the specified position
        table.wrapOn(self.canvas, 0, 0)
        table.drawOn(self.canvas, margin_x, table_y_position)
