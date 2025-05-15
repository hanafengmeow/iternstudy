from bs4 import BeautifulSoup
import json


class ResearchUniversityHTMLParser:
    @staticmethod
    def get_markdown_page_from_html(Mainhtml, Threadhtml):

        # Parse Main HTML content with BeautifulSoup
        soup = BeautifulSoup(Mainhtml, 'html.parser')

        # Extract a list of universities information
        data = {}

        # Extract Field
        field_element = soup.find('select', id='filterDisciplines')
        if field_element:
            selected_option = field_element.find('option', selected=True)
            if selected_option:
                data['Field'] = selected_option.string.strip()
        else:
            data['Field'] = None

        # Extract Universities' Information
        universities_info = []

        ranking_items = soup.find_all('div', {'class': [
                                      'cols university-item rankings-content__item  show',
                                      'cols university-item rankings-content__item show',
                                      'cols university-item rankings-content__item']})
        # print(ranking_items)

        for item in ranking_items:
            university_data = {}
            # Extract University Name
            university_name_element1 = item.find('h4')
            if university_name_element1:
                university_name_element = university_name_element1.find('a')
                if university_name_element:
                    university_data['University Name'] = university_name_element.string.strip()
                else:
                    university_data['University Name'] = None
            else:
                university_data['University Name'] = None

            # Extract World Rank
            world_rank_element = item.find('span', {'class': 'col col--3 py-0 px-0 position border'})
            if world_rank_element:
                university_data['World Rank'] = world_rank_element.text.strip().replace(
                    "\n            World", "")
            else:
                university_data['World Rank'] = None

            # Extract Publications dynamically
            publications_element1 = item.find('span', {'class': 'col col--2 py-0 hide-tablet ranking'})
            if publications_element1:
                publications_element = publications_element1.find_next()
                if publications_element:
                    university_data['Publications'] = publications_element.text.strip().replace(',', '')
                else:
                    university_data['Publications'] = None
            else:
                university_data['Publications'] = None

            # Extract D-Index
            d_index_elements = item.find_all('span', class_='col col--2 col--tablet py-0 ranking')
            for d_index_element in d_index_elements:
                d_index_text = d_index_element.find('span', {'class': 'show-tablet desc'}, string=lambda s: 'D-index' in s)
                if d_index_text:
                    if d_index_element.find('span', {'class': 'ranking no-wrap'}):
                        d_index_value = int(d_index_element.find('span', {'class': 'ranking no-wrap'}).text.replace(',', '').strip())
                        university_data['D-Index'] = d_index_value
            # Add university data to the list
            universities_info.append(university_data)

        # Add the universities' information to the main data
        data['Universities'] = universities_info

        # Parse Threadhtml
        soup1 = BeautifulSoup(Threadhtml, 'html.parser')

        # Extract each university information
        data1 = {}

        # Extract University Name
        university_name_element = soup1.find('h1')
        if university_name_element:
            university_name = university_name_element.text.strip()
        else:
            university_name = None
        data1['University Name'] = university_name

        # Extract Field
        field_element = soup1.select_one('.breadcrumbs li:nth-last-child(2) a')
        if field_element:
            field = field_element.text.replace('Best Universities\n                     - ', '').strip()
        else:
            field = None
        data1['Field'] = field

        # Extract Nationality
        nationality = None
        nationality_element1 = soup1.find('div', {'class': 'flex wrap info'})
        if nationality_element1:
            nationality_element2 = nationality_element1.find('span')
            if nationality_element2:
                nationality_element3 = nationality_element2.find('a')
                if nationality_element3:
                    nationality = nationality_element3.text.strip()
        data1['Nationality'] = nationality

        # Extract Scholars
        svg_authors_element = soup1.find('use', href='#svg-authors')
        scholars = None
        if svg_authors_element:
            if svg_authors_element.find_parent():
                strong_element = svg_authors_element.find_parent().find_next('strong')
                if strong_element:
                    scholars = int(strong_element.text.strip())
        data1['Scholars'] = scholars

        # Extract Best Scientist
        best_scientists = soup1.find_all('div', {'class': 'scientist-item'})
        best_scientist_data = []
        if best_scientists:
            for scientist in best_scientists:
                if scientist.find('h4'):
                    scientist_name = scientist.find('h4').string.strip()
                    best_scientist_data.append(scientist_name)
        data1['Best Scientist'] = best_scientist_data

        # Combine main and thread html information together if university name and field match
        data2 = {}

        for university_data in data.get('Universities', []):
            if university_data.get('University Name') == data1.get('University Name') and \
               data.get('Field') == data1.get('Field'):
                # Combine data and data1
                data2 = {**university_data, **data1}
                break  # Stop checking once a match is found

        return "", json.dumps(data2, indent=2)
    
# Example usage
# if __name__ == "__main__":
#     # Get HTML content (replace with the actual HTML file path)
#     with open(r'./university_main_html_samples/Best Animal Science and Veterinary Universities in the World Ranking 2023 _ Research.com.html', "rb") as f_main, \
#          open(r'./university_thread_html_samples/thread Best Animal Science and Veterinary Scientists in University of California, Davis.html', "rb") as f_thread:
#         main_html_string = f_main.read()
#         thread_html_string = f_thread.read()
#     # Parse the HTML and get the information
#     parsed_info = ResearchUniversityHTMLParser.get_markdown_page_from_html(main_html_string, thread_html_string)
#     if parsed_info:
#         print(parsed_info)
#     else:
#         print("No matching university found between data and data1.")

# import os
#
# if __name__ == "__main__":
#     main_folder_path = r'./university_main_html_samples'  # Replace with the actual folder path
#     thread_folder_path = r'./university_thread_html_samples'  # Replace with the actual folder path
#     for main_filename in os.listdir(main_folder_path):
#         if main_filename.endswith(".html"):
#             main_file_path = os.path.abspath(os.path.join(main_folder_path, main_filename))
#             for thread_filename in os.listdir(thread_folder_path):
#                 if thread_filename.endswith(".html"):
#                     thread_file_path = os.path.abspath(os.path.join(thread_folder_path, thread_filename))
#                     with open(main_file_path, "rb") as f_main, open(thread_file_path, "rb") as f_thread:
#                         main_html_string = f_main.read()
#                         thread_html_string = f_thread.read()
#                     parsed_info = ResearchUniversityHTMLParser.get_markdown_page_from_html(main_html_string, thread_html_string)
#                     if parsed_info:
#                         print(f"Files: {main_filename}, {thread_filename}")
#                         print(parsed_info)
#                         print("\n" + "=" * 50 + "\n")  # Separating output for each pair of files
#                     else:
#                         print("No matching university found between {} and {}.".format(main_filename, thread_filename))
