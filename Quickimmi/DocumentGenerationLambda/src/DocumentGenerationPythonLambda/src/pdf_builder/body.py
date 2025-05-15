import json
import re
from reportlab.lib.pagesizes import letter
from reportlab.lib.units import inch
from reportlab.pdfbase import pdfmetrics
from reportlab.lib.colors import red, black, white, grey, Color


class BodyCreator:
    def __init__(self, canvas, format_settings):
        self.canvas = canvas
        self.basic_settings = {
            "page_size": "letter",
            "margin_x": 72,
            "margin_y": 108,
            "section_break": 2,
            "font_color": black,
            "font_name": "Helvetica",
            "font_size": 11,
            "alignment": "left",
            "line_height_factor": 1.15,
            "start_new_page": False,
        }
        # Merge passed-in default settings
        self.default_format_settings = {
            **self.basic_settings,
            **format_settings.get("body", {}).get("default", {}),
        }
        # self.current_settings = self.default_format_settings.copy()
        self.apply_settings(self.default_format_settings)
        # init the y_position
        self.y_position = self.height - self.margin_y

    def apply_settings(self, settings):
        # Set settings dynamically based on current_settings
        self.width, self.height = self._parse_page_size(settings["page_size"])
        self.margin_x = settings["margin_x"]
        self.margin_y = settings["margin_y"]
        self.body_area_width = self.width - 2 * self.margin_x
        self.x_position = settings.get("x_position", self.margin_x)
        self.x_wrap_position = settings.get("x_wrap_position", self.margin_x)
        self.section_break = settings["section_break"]
        self.font_color = settings["font_color"]
        self.font_name = settings["font_name"]
        self.font_size = settings["font_size"]
        self.alignment = settings["alignment"]
        self.line_height_factor = settings["line_height_factor"]
        self.line_height = self.font_size * self.line_height_factor
        self.underline_phrases = settings.get("underline_phrases", None)
        self.start_new_page = settings["start_new_page"]
        # Set canvas properties
        self.canvas.setFont(self.font_name, self.font_size)
        self.canvas.setFillColor(self.font_color)

    def _parse_page_size(self, page_size):
        page_sizes = {"letter": letter}
        return page_sizes.get(page_size, letter)

    def update_section_font_settings(self, section_settings):
        # Temporarily apply section-specific settings
        updated_settings = {**self.default_format_settings, **section_settings}
        self.apply_settings(updated_settings)
        self.y_position = section_settings.get("y_position", self.y_position)

    def wrap_text(self, text, width):
        wrapped_lines = []
        # Split text into paragraphs based on new lines
        paragraphs = text.split("\n")
        # Width of a single space
        space_width = pdfmetrics.stringWidth(" ", self.font_name, self.font_size)
        # Number of spaces to prepend
        extra_space_count = int((self.x_wrap_position - self.x_position) / space_width)
        # loop through each paragraph
        for paragraph in paragraphs:
            # Check for completely empty paragraphs, Preserve empty lines as blank strings
            if not paragraph:
                wrapped_lines.append("")
                continue

            # Track the line number within the paragraph
            line = []
            line_number = 0
            # words = paragraph.split(" ")
            words = list(paragraph) if self._is_chinese(paragraph) else paragraph.split(" ")
            separator = "" if self._is_chinese(paragraph) else " "
            for word in words:
                if word:  # Only process non-empty words
                    test_line = (
                        separator.join(line + [word]) if line else word
                    )  # Build potential line with the word
                    line_width = pdfmetrics.stringWidth(
                        test_line, self.font_name, self.font_size
                    )

                    if line_width <= width:
                        line.append(word)
                    else:
                        if line_number == 0:
                            wrapped_lines.append(separator.join(line))
                        else:
                            # Add extra spaces to subsequent lines
                            wrapped_lines.append(
                                " " * extra_space_count + separator.join(line)
                            )
                        line = [word]  # Start new line with current word
                        line_number += 1
                else:  # Handle extra spaces
                    line.append("")  # Append empty string to represent extra space

            # Add the last formed line of the paragraph
            if line_number == 0:
                wrapped_lines.append(separator.join(line))
            else:
                wrapped_lines.append(" " * extra_space_count + separator.join(line))
        return wrapped_lines
    
    def _is_chinese(self, text):
        # Check if text contains Chinese characters
        for ch in text:
            if '\u4e00' <= ch <= '\u9fff':
                return True
        return False
    
    def draw_content(self, item):
        if isinstance(item, str):
            self.draw_text(item)
        elif isinstance(item, list) and item:
            self.draw_checkboxes(item)

    def check_and_prepare_new_page(self):
        if self.y_position - self.line_height < self.margin_y:
            self.set_a_new_page()

    def draw_text(self, item):
        lines = self.wrap_text(item, self.body_area_width)
        for line in lines:
            # if the line is empty, just move the y_position, no need to create a new page
            if line == "":
                self.y_position -= self.line_height
                continue
            self.check_and_prepare_new_page()
            self.draw_line(line)

    def draw_checkboxes(self, item):
        for i in item:
            self.check_and_prepare_new_page()
            self.draw_checkbox(i)

    def draw_line(self, line):
        start_x = self._calculate_start_x(line)
        self.canvas.drawString(start_x, self.y_position, line)
        if self.underline_phrases:
            self._underline_text_by_phrases(line, self.underline_phrases)
        self.y_position -= self.line_height

    def draw_section_break(self):
        self.y_position -= self.line_height * self.section_break

    def _calculate_start_x(self, line):
        text_width = pdfmetrics.stringWidth(line, self.font_name, self.font_size)
        if self.alignment == "center":
            return self.x_position + (self.body_area_width - text_width) / 2
        elif self.alignment == "right":
            return self.x_position + self.body_area_width - text_width
        return self.x_position

    def set_a_new_page(self):
        self.canvas.showPage()
        self.y_position = self.height - self.margin_y
        self.canvas.setFont(self.font_name, self.font_size)

    def _underline_text_by_phrases1(self, line, phrases):
        start_x = self._calculate_start_x(line)
        current_x = start_x
        for phrase, occurrence in phrases:
            start_indices = []
            start = 0
            # Find all occurrences of the phrase in the line, including leading spaces
            while True:
                start = line.find(phrase, start)
                if start == -1:
                    break
                start_indices.append(start)
                start += len(phrase)
            if len(start_indices) < occurrence:
                continue  # The specified occurrence is not in this line
            # Get the start index of the required occurrence
            phrase_start_index = start_indices[occurrence - 1]
            phrase_end_index = phrase_start_index + len(phrase)
            # Calculate the x positions for the start and end of the phrase
            phrase_start_x = current_x + pdfmetrics.stringWidth(
                line[:phrase_start_index], self.font_name, self.font_size
            )
            phrase_end_x = current_x + pdfmetrics.stringWidth(
                line[:phrase_end_index], self.font_name, self.font_size
            )
            # Draw the underline
            self.canvas.line(
                phrase_start_x, self.y_position - 2, phrase_end_x, self.y_position - 2
            )

    def _underline_text_by_phrases(self, line, phrases, additional_x_offset=0):
        start_x = self._calculate_start_x(line) + additional_x_offset
        current_x = start_x
        for phrase, occurrence in phrases:
            start_indices = []
            start = 0
            # Find all occurrences of the phrase in the line
            while True:
                start = line.find(phrase, start)
                if start == -1:
                    break
                start_indices.append(start)
                start += len(phrase)
            if len(start_indices) < occurrence:
                continue  # The specified occurrence is not in this line
            # Get the start index of the required occurrence
            phrase_start_index = start_indices[occurrence - 1]
            phrase_end_index = phrase_start_index + len(phrase)
            # Calculate the x positions for the start and end of the phrase
            phrase_start_x = current_x + pdfmetrics.stringWidth(
                line[:phrase_start_index], self.font_name, self.font_size
            )
            phrase_end_x = current_x + pdfmetrics.stringWidth(
                line[:phrase_end_index], self.font_name, self.font_size
            )
            # Draw the underline
            self.canvas.line(
                phrase_start_x, self.y_position - 2, phrase_end_x, self.y_position - 2
            )

    def _underline_text_by_index(self, start_x, line, underline):
        words = line.split()
        start_word_index, end_word_index = underline

        # Calculate the starting point of the underline
        text_before_underline = " ".join(words[:start_word_index])
        underline_start_x = start_x + pdfmetrics.stringWidth(
            text_before_underline, self.font_name, self.font_size
        )

        # Calculate the width of the underline
        text_to_underline = " ".join(words[start_word_index : end_word_index + 1])
        underline_width = pdfmetrics.stringWidth(
            text_to_underline, self.font_name, self.font_size
        )

        # Draw the underline
        self.canvas.line(
            underline_start_x,
            self.y_position - 2,
            underline_start_x + underline_width,
            self.y_position - 2,
        )

    def draw_checkbox(self, i):
        x = i.get("x", self.x_position)
        y = i.get("y", self.y_position)
        boxFillColor = i.get("boxFillColor", "lightgrey")
        boxStrokeColor = i.get("boxStrokeColor", "lightgrey")
        boxSize = i.get("boxSize", 8)
        label_offset = i.get("label_offset", 5)
        label = i.get("label", "")
        underline_phrases = i.get("underline_phrases", [])
        checked = i.get("checked", False)
        checkmark_color = i.get("checkmark_color", black)
        checkmark_thickness = i.get("checkmark_thickness", 1)

        # Draw the checkbox square
        self.canvas.setStrokeColor(boxStrokeColor)
        self.canvas.setFillColor(boxFillColor)
        self.canvas.rect(x, y, boxSize, boxSize, fill=1, stroke=1)

        # Fill the checkbox if checked
        if checked:
            self.canvas.setStrokeColor(checkmark_color)
            self.canvas.setLineWidth(checkmark_thickness)

            # Calculate checkmark points
            left = x + boxSize * 0.2
            bottom = y + boxSize * 0.3
            middle = x + boxSize * 0.4
            top = y + boxSize * 0.8
            right = x + boxSize * 0.8

            # Draw checkmark
            p = self.canvas.beginPath()
            p.moveTo(left, bottom + (top - bottom) / 2)
            p.lineTo(middle, bottom)
            p.lineTo(right, top)
            self.canvas.drawPath(p)

            # Restore settings for drawing the box or other elements
            self.canvas.setStrokeColor(boxStrokeColor)
            self.canvas.setFillColor(boxFillColor)
            self.canvas.setLineWidth(
                1
            )  # Restore the line width to default for subsequent drawings

        # Add label if provided
        if label:
            label_x = x + boxSize + label_offset
            available_width = self.width - self.margin_x - label_x
            wrapped_label_lines = self.wrap_text(label, available_width)
            self.canvas.setFillColor(self.font_color)
            # self.canvas.drawString(label_x, y, label)
            for line in wrapped_label_lines:
                self.canvas.drawString(label_x, y, line)
                if underline_phrases:
                    self._underline_text_by_phrases(
                        line,
                        underline_phrases,
                        additional_x_offset=label_x - self._calculate_start_x(line),
                    )
                y -= self.line_height

        # Update y_position after drawing checkbox (assuming you want space after checkbox)
        self.y_position = y
