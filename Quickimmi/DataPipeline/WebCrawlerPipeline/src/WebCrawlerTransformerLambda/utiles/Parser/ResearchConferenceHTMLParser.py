from bs4 import BeautifulSoup
import json


class ResearchConferenceHTMLParser:
    @staticmethod
    def get_markdown_page_from_html(html):
        # Parse the HTML using BeautifulSoup
        soup = BeautifulSoup(html, 'html.parser')

        # Prepare data container
        data = {}

        # Extract Conference Name
        conference_name_element = soup.find('h1')
        if conference_name_element:
            conference_name = conference_name_element.text.strip()
        else:
            conference_name = None
        data['Conference Name'] = conference_name

        # Extract Field
        field_element = soup.select_one('.breadcrumbs li:nth-last-child(2) a')
        if field_element:
            field = field_element.text.replace(
                'Best Conferences\n                     - ', '').strip()
        else:
            field = None
        data['Field'] = field

        # Extract World Rank
        world_rank_element = soup.select(
            '.conference-table .row:-soup-contains("Research Ranking")')
        # In the key-value pair in the JSON, this value is embedded/sub key-value pairs
        world_rank = {}
        for entry in world_rank_element:
            category = entry.select_one('.ps').text.strip('()')
            rank_text1 = entry.select_one('.ps')
            if rank_text1:
                if rank_text1.find_next('span'):
                    rank_text = rank_text1.find_next('span').text.strip()
                    world_rank[category] = int(rank_text)
        data['World Rank'] = world_rank

        # Extract Impact Score
        impact_score_element = soup.select_one('.conference-top__ranking span')
        if impact_score_element:
            impact_score = impact_score_element.text.strip()
        else:
            impact_score = None
        data['Impact Score'] = impact_score

        # Extract Conference Call for Papers
        call_for_papers_element = soup.select_one('#tab-2')
        call_for_papers = []
        if call_for_papers_element:
            topics_element = call_for_papers_element.find_next('br')
            if topics_element:
                for topic in topics_element.text.split('\n'):
                    if topic.strip():
                        call_for_papers.append(topic.strip())
        data['Conference Call for Papers'] = call_for_papers

        # Extract Top Research Topics
        top_research_topics_element = soup.select_one('#top + ul')
        # In the key-value pair in JSON, this value is an array
        top_research_topics = []
        if top_research_topics_element:
            for li in top_research_topics_element.select('li'):
                topic = li.text.strip()
                top_research_topics.append(topic)
        data['Top Research Topics'] = top_research_topics

        # Extract Official Website
        website_element = soup.select_one('.button[href^="https://"]')
        if website_element:
            website = website_element['href']
        else:
            website = None
        data['Official Website'] = website

        return "", json.dumps(data, indent=2)

# # Example usage
# import os
#
# if __name__ == "__main__":
#     folder_path = r'./conference_html_samples'  # Replace with the actual folder path
#     for filename in os.listdir(folder_path):
#         if filename.endswith(".html"):
#             file_path = os.path.abspath(os.path.join(folder_path, filename))
#             print(f"Processing file: {file_path}")  # Added for debugging
#             with open(file_path, "rb") as f:
#                 html_string = f.read()
#             parsed_info = ResearchConferenceHTMLParser.get_markdown_page_from_html(html_string)
#             print(f"File: {filename}")
#             print(parsed_info)
#             print("\n" + "=" * 50 + "\n")  # Separating output for each file
