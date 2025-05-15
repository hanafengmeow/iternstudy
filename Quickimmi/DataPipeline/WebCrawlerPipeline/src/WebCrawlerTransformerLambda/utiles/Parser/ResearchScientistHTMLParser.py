from bs4 import BeautifulSoup
import json


class ResearchScientistHTMLParser:
    @staticmethod
    def get_markdown_page_from_html(html):
        # Parse the HTML using BeautifulSoup
        soup = BeautifulSoup(html, 'html.parser')

        # Prepare data container
        data = {}

        # Extract Scholar Name
        scholar_name_element = soup.find('h1')
        if scholar_name_element:
            scholar_name = scholar_name_element.text.strip()
        else:
            scholar_name = None
        data['Scholar Name'] = scholar_name

        # Extract Field
        field = None
        discipline_element = soup.find('div', {'class': 'metrics-table__row'})
        if discipline_element:
            discipline_element1 = discipline_element.find('span', {'class': 'title'})
            if discipline_element1:
                field = discipline_element1.text.strip()
        data['Field'] = field

        # Extract "World Rank" field
        world_rank = None
        if discipline_element:
            world_rank_element = discipline_element.find(
                'span', {'class': 'desc'}, string='World Ranking')
            if world_rank_element:
                world_rank_element1 = world_rank_element.find_parent()
                if world_rank_element1:
                    world_rank = world_rank_element1.text.replace(
                        'World Ranking\n        ', '').strip()
        data['World Rank'] = world_rank

        # Extract "Employer"
        employer = None
        employer_element1 = soup.find(
            'div', class_='profile-head__info__location')
        if employer_element1:
            if employer_element1.find('a', href=True):
                employer = employer_element1.find('a', href=True).get_text(strip=True)
        data['Employer'] = employer

        # Extract "Nationality"
        nationality_element1 = soup.find('div', class_='profile-head__info__location')
        if nationality_element1:
            nationality_element = nationality_element1.find_all('a', href=True)
            nationality_text = None
            for element in nationality_element:
                if 'scientists-rankings' in element['href']:
                    nationality_text = element.get_text(strip=True)
                    break
        else:
            nationality_text = None
        data['Nationality'] = nationality_text

        # Extract "D-index" field
        d_index = None
        if discipline_element:
            d_index_element1 = discipline_element.find(
                'span', {'class': 'desc'}, string='D-index')
            if d_index_element1:
                d_index_element = d_index_element1.find_parent()
                if d_index_element:
                    d_index = d_index_element.text.replace(
                        'D-index\n        ', '').strip()
        data['D-index'] = d_index

        # Extract Citation
        if discipline_element:
            citation_element1 = discipline_element.find(
                'span', {'class': 'desc'}, string='Citations')
            if citation_element1:
                citation_element = citation_element1.find_parent()
                if citation_element:
                    data['Citation'] = citation_element.text.replace(
                        'Citations\n        ', '').strip()
                else:
                    data['Citation'] = None
            else:
                data['Citation'] = None
        else:
            data['Citation'] = None

        # Extract Publication
        if discipline_element:
            publication_element = discipline_element.find(
                'span', {'class': 'hide-tablet'})
            # print(publication_element)
            if publication_element:
                data['Publication'] = publication_element.text.replace(
                    'Publications\n        ', '').strip()
            else:
                data['Publication'] = None

        # Extract Awards and Achievements
        awards_element = soup.find('div', {'class': 'tab bg-white'})
        if awards_element:
            awards_slide_element = awards_element.find('div', {'class': 'tab-slide'})
            if awards_slide_element:
                awards_list = [
                    award.text.strip().replace(
                        '\n                                                                                ', ' ')
                    for award in awards_slide_element.find_all('p')
                ]
                data['Awards and Achievements'] = awards_list
            else:
                data['Awards and Achievements'] = None
        else:
            data['Awards and Achievements'] = None


    # Extract "What is he/she best known for?" text
        best_known_for_element = soup.find('h2', {'id': 'best'})
        if best_known_for_element:
            elements_between = best_known_for_element.find_all_next(
                ['h2', 'p', 'h3', 'ul', 'li'], string=True, recursive=False)
            # Limit the extraction up to the <h2 id="all"> element
            text_elements = []
            for element in elements_between:
                # print(element, "++++++++++++++++")
                # print(element.name == 'h2' or element.id == "tab-2")
                if element.name == 'h2' or element.id == "tab-2" or element.id == "tab-3":
                    break
                text_elements.append(element.get_text(
                    strip=True).replace('\n', ' '))
            data['What is he/she best known for?'] = text_elements
        else:
            data['What is he/she best known for?'] = None

        # Extract "What are the main themes of his/her work throughout his/her whole career to date?" text
        main_themes_for_element = soup.find('h2', {'id': 'all'})
        if main_themes_for_element:
            elements_between = main_themes_for_element.find_all_next(
                ['h2', 'p', 'h3', 'ul', 'li'], string=True, recursive=False)
            # Limit the extraction up to the <h2 > element
            text_elements = []
            for element in elements_between:
                # print(element, "++++++++++++++++")
                # print(element.name == 'h2' or element.id == "tab-2")
                if element.name == 'h2' or element.id == "tab-2" or element.id == "tab-3":
                    break
                text_elements.append(element.get_text(
                    strip=True).replace('\n', ' '))
            data['What are the main themes of his/her work throughout his/her whole career to date?'] = text_elements
        else:
            data['What are the main themes of his/her work throughout his/her whole career to date?'] = None

        return "", json.dumps(data, indent=2)


# # Example usage
# import os
#
# if __name__ == "__main__":
#     folder_path = r'./scientist_html_samples'  # Replace with the actual folder path
#     for filename in os.listdir(folder_path):
#         if filename.endswith(".html"):
#             file_path = os.path.abspath(os.path.join(folder_path, filename))
#             with open(file_path, "rb") as f:
#                 html_string = f.read()
#             parsed_info = ResearchScientistHTMLParser.get_markdown_page_from_html(html_string)
#             print(f"File: {filename}")
#             print(parsed_info)
#             print("\n" + "=" * 50 + "\n")  # Separating output for each file
