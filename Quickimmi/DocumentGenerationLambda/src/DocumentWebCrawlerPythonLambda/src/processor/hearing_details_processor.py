import re
from src.utils.local_save import save_json_to_local
from src.crawler.hearing_details_crawler import CrawlHearingDetails
from src.database.db_utils import DatabaseUtils
from src.constants import Constants
import logging
import json

logger = logging.getLogger()
logger.setLevel(logging.INFO)

class HearingDetailsProcessor:
    def __init__(self, task_id, message_body, local_testing=False):
        self.task_id = task_id
        self.message_body = message_body
        self.local_testing = local_testing

    def process(self):
        try:
            # Step 1: Extract and validate the A-number
            self.a_number = self.extract_a_number()
            # Step 2: Retrieve hearing details
            hearing_details = self.fetch_hearing_details(self.a_number)
            # Step 3: Save hearing details
            self.save_hearing_details(hearing_details)
        except ValueError as ve:
            raise ve
        except Exception as e:
            raise e

    def extract_a_number(self):
        task_metadata = json.loads(self.message_body.get('metadata'))
        a_number = task_metadata.get('a_number')
        if not a_number:
            raise ValueError(f"A-number is missing from the message body: {self.message_body}")

        cleaned_a_number = re.sub(r'\D', '', a_number)
        if len(cleaned_a_number) != 9:
            raise ValueError(f"Received an invalid A-number: {a_number}")

        logger.info(f"Valid A-number extracted: {cleaned_a_number}")
        return cleaned_a_number

    def fetch_hearing_details(self, a_number):
        # hearing_details is a JSON object or None
        hearing_details = CrawlHearingDetails(a_number).get()
        if not hearing_details:
            raise ValueError(f"Failed to get hearing details for A-number: {a_number}")
        logger.info(f"Successfully retrieved hearing details for A-number {a_number}: {hearing_details}")
        return hearing_details

    def save_hearing_details(self, hearing_details):
        try:
            if self.local_testing:
                save_json_to_local(hearing_details, f"output/hearing_detail_{self.a_number}.json")
            else:
                logger.info(f"Saving hearing details for task {self.task_id} - {hearing_details}")
                DatabaseUtils().update_task(self.task_id, Constants.TASK_SUCCESS, hearing_details)
        except Exception as e:
            error_message = f"Failed to save hearing details for task {self.task_id}: {str(e)}"
            logger.error(error_message)
            raise Exception(error_message) from e

