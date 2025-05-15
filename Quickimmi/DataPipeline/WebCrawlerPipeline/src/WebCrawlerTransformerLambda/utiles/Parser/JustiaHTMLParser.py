from bs4 import BeautifulSoup
import json
import re

class JustiaHTMLParser:
    @staticmethod
    def get_markdown_page_from_html(html):
        soup = BeautifulSoup(html, 'html.parser')
        data = {}

        # Extract Name
        name_element = soup.find('h1', class_='fn lawyer-name')
        data['Name'] = name_element.text.strip() if name_element else ""

        # Extract Address
        address_elements = soup.select('div.adr.small-font')
        city_city = []
        state_state = []
        street_street = []
        for address_element in address_elements:
            street = address_element.find('div', class_='street-address').text.strip() if address_element.find('div', class_='street-address') else ''
            locality_element = address_element.find_next_sibling('div', class_='small-font')
            city = locality_element.find('span', class_='locality').text.strip() if locality_element and locality_element.find('span', class_='locality') else ''
            state = locality_element.find('span', class_='region').text.strip() if locality_element and locality_element.find('span', class_='region') else ''
            zipcode = locality_element.find('span', class_='postal-code').text.strip() if locality_element and locality_element.find('span', class_='postal-code') else ''

            city_city.append(city)
            state_state.append(state)
            street_street.append(street)
        data['City'] = city if city_city else ""
        data['State'] = state if state_state else ""
        data['Street'] = street if street_street else ""

        # Extract Phone Number
        phone_container = soup.select_one('.flex-col-wrapper .flex-col.width-40.reset-width-below-tablet .small-font span.value')
        phone_number = None
        if phone_container and phone_container.find('span'):
            phone_number = phone_container.find('span').text.strip()

        data['Phone'] = phone_number if phone_number else ""

        # Extract Business Name
        business_name_element = soup.find('span', class_='role color-dove-gray small-font')
        data['Business Name'] = business_name_element.text.strip() if business_name_element else ""

        # Extract Websites
        website_elements = soup.select('dt.dsc-term a[href]')
        websites = [element['href'].strip() for element in website_elements]
        data['Website'] = websites if websites else ""


        # block -> block-wrapper block -> class has-no-list-styles -> li -> the one contained inside <strong></strong>
        # Extract Languages
        languages_block = soup.select_one('.block .block-wrapper.block ul.has-no-list-styles')
        languages = []
        if languages_block:
            language_items = languages_block.find_all('li')
            for item in language_items:
                language_strong = item.find('strong')
                if language_strong:
                    languages.append(language_strong.get_text(strip=True))

        data['Languages'] = languages if languages else ""

        return "", json.dumps(data, indent=2)