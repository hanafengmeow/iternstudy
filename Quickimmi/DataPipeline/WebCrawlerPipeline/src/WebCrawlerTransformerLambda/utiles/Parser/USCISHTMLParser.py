from bs4 import BeautifulSoup
import html2text

class USCISHTMLParser:
    @staticmethod
    def get_markdown_page_from_html(html):
        # Parse the HTML using BeautifulSoup
        soup = BeautifulSoup(html, 'html.parser')

        # remove useless divs:
        # translations
        # alert-message
        trans_divs = soup.find_all('div', class_='translations')
        for trans in trans_divs:
            trans.extract()

        alter_divs = soup.find_all('div', class_='messages messages--info')
        for alter in alter_divs:
            alter.extract()

        main_content_tag = '.container.container--main'
        container = soup.select_one(main_content_tag)

        if container is None:
            raise ValueError("Couldn't find the page main content with the tag: %s," % main_content_tag)

        # Change section title to h2
        # Find elements with the accordion__header
        elements = container.find_all("div", class_="accordion__header")

        # Replace the found elements with headers
        for element in elements:
            # use h2 to replace all the section header
            # this script will split the whole doc based on h2
            try:
                new_tag = soup.new_tag("h2")
                new_tag.string = element.string
                element.replace_with(new_tag)
            except Exception:
                pass

        # Convert the HTML to Markdown
        md_text = html2text.html2text(str(container))

        # Get last reviewed date
        date_element = soup.select_one('.field.field--name-field-display-date.field__item time.datetime')
        if date_element:
            last_update_date = date_element.text
        else:
            last_update_date = None

        return last_update_date, md_text

