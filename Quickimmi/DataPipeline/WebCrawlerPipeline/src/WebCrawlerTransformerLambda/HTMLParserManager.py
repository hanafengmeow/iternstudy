from utiles.Parser.ResearchScientistHTMLParser import ResearchScientistHTMLParser
from utiles.Parser.ResearchUniversityHTMLParser import ResearchUniversityHTMLParser
from utiles.Parser.ResearchConferenceHTMLParser import ResearchConferenceHTMLParser
from utiles.Parser.ResearchJournalHTMLParser import ResearchJournalHTMLParser
from utiles.Parser.WeGreenHTMLParser import WeGreenHTMLParser
from utiles.Parser.USCISHTMLParser import USCISHTMLParser
from utiles.Parser.JustiaHTMLParser import JustiaHTMLParser
from constant.constants import *

class HTMLParserManager:
    @staticmethod
    def get_parser(source):
        # Decide which parser to use based on the source
        if source == USCIS_SOURCE:
            return USCISHTMLParser()
        elif source == wegreened_SOURCE:
            return WeGreenHTMLParser()
        elif source == RESEARCH_JOURNAL_SOURCE:
            return ResearchJournalHTMLParser()
        elif source == RESEARCH_CONFERENCE_SOURCE:
            return ResearchConferenceHTMLParser()
        elif source == RESEARCH_SCIENTISTS_SOURCE:
            return ResearchScientistHTMLParser()
        elif source == RESEARCH_UNIVERSITY_SOURCE:
            return ResearchUniversityHTMLParser()
        elif source == JUSTIA_SOURCE:
            return JustiaHTMLParser()
        else:
            raise ValueError(f"Unsupported source {source}")
