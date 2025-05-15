from bs4 import BeautifulSoup
import json


class ResearchJournalHTMLParser:
    @staticmethod
    def get_markdown_page_from_html(html):
        # Parse the HTML using BeautifulSoup
        soup = BeautifulSoup(html, 'html.parser')

        # Prepare data container
        data = {}

        # Extract Journal Name
        journal_name_element = soup.find('h1')
        if journal_name_element:
            journal_name = journal_name_element.text.strip()
        else:
            journal_name = None
        data['Journal Name'] = journal_name

        # Extract Field
        field_element = soup.select_one('.breadcrumbs li:nth-last-child(2) a')
        if field_element:
            field = field_element.text.replace(
                'Best Journals\n                     - ', '').strip()
        else:
            field = None
        data['Field'] = field

        # Extract World Rank
        world_rank_element = soup.select(
            '.conference-table .row:-soup-contains("Research Ranking")')
        # In the key-value pair in the JSON, this value is embedded/sub key-value pairs
        world_rank = {}
        if world_rank_element:
            for entry in world_rank_element:
                category = entry.select_one('.ps').text.strip('()')
                rank_text1 = entry.select_one('.ps')
                if rank_text1:
                    if rank_text1.find_next('span'):
                        rank_text = rank_text1.find_next('span').text.strip()
                        world_rank[category] = int(rank_text)
        data['World Rank'] = world_rank

        # Extract (Number of) Best Scientists
        scientists = None
        scientists_element1 = soup.select_one(
            '.row:-soup-contains("Number of Best scientists")')
        if scientists_element1:
            scientists_element = scientists_element1.find_next('span')
            if scientists_element:
                if scientists_element.find_next('span'):
                    scientists = scientists_element.find_next('span').text.strip()
        data['Best Scientists'] = scientists

        # Extract Documents
        documents = None
        documents_element1 = soup.select_one(
            '.row:-soup-contains("Documents by best scientists")')
        if documents_element1:
            documents_element = documents_element1.find_next('span')
            if documents_element:
                if documents_element.find_next('span'):
                    documents = documents_element.find_next('span').text.strip()
        data['Documents'] = documents

        # Extract Impact Score
        impact_score_element = soup.select_one('.conference-top__ranking span')
        if impact_score_element:
            impact_score = impact_score_element.text.strip()
        else:
            impact_score = None
        data['Impact Score'] = impact_score

        # Extract Publisher
        publisher_element = soup.select_one(
            '.row:-soup-contains("Publisher") img')
        if publisher_element:
            publisher = publisher_element['alt']
        else:
            publisher = None
        data['Publisher'] = publisher

        # Extract ISSN
        issn = None
        issn_element1 = soup.select_one(
            '.row:-soup-contains("ISSN:")')
        if issn_element1:
            issn_element2 = issn_element1.find_next('span')
            if issn_element2:
                if issn_element2.find_next('span'):
                    issn = issn_element2.find_next('span').text.strip()
        data['ISSN'] = issn

        # Extract Website
        website_element = soup.select_one(
            '.button[href^="https://"]')
        if website_element:
            website = website_element['href']
        else:
            website = None
        data['Website'] = website

        # Extract Top Research Topics
        top_research_topics_element = soup.select_one('.tab-slide ul')
        # In the key-value pair in JSON, this value is an array
        top_research_topics = []
        if top_research_topics_element:
            for li in top_research_topics_element.select('li'):
                topic_with_percentage = li.text.strip()
                top_research_topics.append(topic_with_percentage)
        data['Top Research Topics'] = top_research_topics

        return "", json.dumps(data, indent=2)

# # Example usage
# import os
#
# if __name__ == "__main__":
#     folder_path = r'./journal_html_samples'  # Replace with the actual folder path
#     for filename in os.listdir(folder_path):
#         if filename.endswith(".html"):
#             file_path = os.path.abspath(os.path.join(folder_path, filename))
#             with open(file_path, "rb") as f:
#                 html_string = f.read()
#             parsed_info = ResearchJournalHTMLParser.get_markdown_page_from_html(html_string)
#             print(f"File: {filename}")
#             print(parsed_info)
#             print("\n" + "=" * 50 + "\n")  # Separating output for each file
