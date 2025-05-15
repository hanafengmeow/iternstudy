from bs4 import BeautifulSoup
import html2text
import json

class WeGreenHTMLParser:
    @staticmethod
    def get_markdown_page_from_html(html):
        # Initialize an empty map to store extracted data
        result_map = {}

        # Parse the HTML using BeautifulSoup
        soup = BeautifulSoup(html, features="html.parser")

        # Extract data from entry-header
        entry_header = soup.find('header', class_='entry-header')
        if entry_header:
            title = entry_header.find('h1', class_='entry-title').text.strip()
            result_map['title'] = title

        # Extract data from entry-content
        entry_content = soup.find('div', class_='entry-content')

        if entry_content:
            # Find the div with an id containing "mes_text_"
            mes_text_div = entry_content.find('div', id=lambda x: x and 'mes_text_' in x)
            if mes_text_div:
                testimonial_text = mes_text_div.text.strip()
                result_map['Client\'s Testimonial'] = testimonial_text

            paragraphs_with_strong = entry_content.select('p:has(strong)')

            for p in paragraphs_with_strong:
                # Find the <strong> tag inside the <p> tag
                strong_tag = p.find('strong')
                if strong_tag:
                    # Extract the text content of the first <strong> tag as the key
                    key = strong_tag.text.strip().rstrip(':')

                    # Extract the remaining text content within the <p> tag as the value
                    value = p.get_text(separator=' ', strip=True).replace(strong_tag.get_text(strip=True), '', 1).strip()

                    if value.startswith(key + ":"):
                        value = value[len(key + ":"):].strip()
                    # Update the result_map with the key and the value

                    if key.endswith("Testimonial"):
                        continue
                    result_map[key] = value

            case_summary_heading = entry_content.find('strong', string='Case Summary:')
            if case_summary_heading:
                # Find the parent div containing the "Case Summary:" heading
                case_summary_div = case_summary_heading.find_parent('div')
                # Find all elements within the Case Summary div
                case_summary_elements = case_summary_div.find_all(['p', 'div', 'ul', 'li', 'em', 'a'])

                # Extract the text content of each element and add to the result_map
                text = ""
                for case_summary_element in case_summary_elements:
                    if case_summary_element == case_summary_heading.find_parent('p'):
                        continue
                    if case_summary_element.name == 'p':
                        text += case_summary_element.get_text(separator='\n') + '\n'
                    else:
                        text += case_summary_element.get_text() + '\n'

                # Add "Case Summary" as the key and the appended text as the value
                result_map["Case Summary"] = text.strip()

        json_string = json.dumps(result_map, indent=2)
        # print(json_string)
        return "", json_string



# def main():
#     # Read HTML content from the file
#     with open('/Users/zechengli/Desktop/RoundBlock/wegreen.html', 'r', encoding='utf-8') as file:
#         html_content = file.read()
#
#     # Convert HTML content to bytes
#     html_bytes = html_content.encode('utf-8')
#
#     # Do something with the byte stream if needed
#
#     # Convert bytes back to text
#     html_text = html_bytes.decode('utf-8')
#
#     # Call the function and print the result
#     date, result = WeGreenHTMLParser.get_markdown_page_from_html(html_text)
#     print(result)
#     # for key, value in result.items():
#     #
#     #     if key == "Case Summary":
#     #         # Split the case summary value into multiple lines based on "\n"
#     #         lines = value.split("\n")
#     #         for line in lines:
#     #             print(f"  {line}")
#     #     else:
#     #         print(f"{key}:{value}")
#
#     print("=" * 40)  # Separate entries with a line of equal signs
#
#
# if __name__ == "__main__":
#     main()

