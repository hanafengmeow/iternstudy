import os
import json
import ast
import requests
from twocaptcha import TwoCaptcha
from src.constants import Constants
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

class CaptchaError(Exception):
    """Custom exception for captcha-related errors."""
    pass

class DataRetrievalError(Exception):
    """Custom exception for errors retrieving data."""
    pass

class CrawlHearingDetails:
    def __init__(self, a_number):
        self.a_number = a_number
        self.data_url = Constants.EOIR_DATA_URL
        self.website_sitekey = Constants.EOIR_CAPTCHA_SITEKEY
        self.website_url = Constants.EOIR_WEBSITE_URL
        
    def get(self):
        try:
            captcha_result = self._solve_captcha()
            captcha_code = self._get_captcha_code(captcha_result)            
            content = self._request_data_with_solved_captcha_code(captcha_code)
            hearing_details = self._get_master_hearing_details(content)
            return hearing_details
        
        except CaptchaError as e:
            raise CaptchaError(f"{str(e)}") from e
        except DataRetrievalError as e:
            raise  DataRetrievalError(f"{str(e)}") from e
        except Exception as e:
            logger.error(f"Unexpected error occurred - {str(e)}")
            raise Exception(f"Unexpected error occurred {str(e)}") from e
            
    def _get_captcha_solver(self):
        api_key = os.getenv('APIKEY_2CAPTCHA', Constants.CAPTCHA_API_KEY)
        if not api_key:
            raise ValueError("APIKEY_2CAPTCHA not found in environment variables")
        return TwoCaptcha(api_key)
    
    def _solve_captcha(self):
        logger.info("Start solving captcha...")
        solver = self._get_captcha_solver()
        try:
            captcha_result = solver.hcaptcha(
                sitekey=self.website_sitekey, 
                url=self.website_url, 
                async_=True
            )
            logger.info(f"Captcha solved successfully - {captcha_result}")
            return str(captcha_result)
        except Exception as e:
            logger.error(f"Captcha solving failed - {str(e)}")
            raise CaptchaError(f"Captcha solving failed - {str(e)}") from e
    
    def _get_captcha_code(self, captcha_result):
        try:
            captcha_result_obj = ast.literal_eval(captcha_result)
            return captcha_result_obj['code']
        except (ValueError, SyntaxError) as e:
            logger.error(f"Failed to parse captcha result - {str(e)}")
            raise CaptchaError(f"Failed to parse captcha result - {str(e)}") from e
    
    def _request_data_with_solved_captcha_code(self, captcha_code):
        params = {"alienNumber": self.a_number, "languageCode": "EN"}
        # Get the headers from Constants, and update the Captcha-Token
        headers = {
            "Accept": "*/*",
            "Accept-Encoding": "gzip, deflate, br, zstd",
            "Accept-Language": "en-US,en;q=0.9",
            "Captcha-Token": captcha_code,
            "Connection": "keep-alive",
            "Host": "eoir-ws.eoir.justice.gov",
            "Origin": "https://acis.eoir.justice.gov",
            "Referer": "https://acis.eoir.justice.gov/",
            "Sec-Fetch-Dest": "empty",
            "Sec-Fetch-Mode": "cors",
            "Sec-Fetch-Site": "same-site",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
            "sec-ch-ua": '"Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"',
            "sec-ch-ua-mobile": "?0",
            "sec-ch-ua-platform": '"macOS"'
        }
        try:
            response = requests.get(self.data_url, params=params, headers=headers)
            logger.info(f"Eoir website request status - {response}")
            if response.status_code == 200:
                logger.info(f"Eoir website response received - {response.text}")
                return response.text
            else:
                logger.error(f"Eoir website request failed - {response} - {response.text}")
                raise DataRetrievalError(f"Eoir website request failed - {response} - {response.text}")
        except requests.RequestException as e:
            logger.error(f"Failed to retrieve EOIR data - {str(e)}")
            raise DataRetrievalError(f"Failed to retrieve EOIR data - {str(e)}") from e

    def _get_master_hearing_details(self, data):
        try:
            if isinstance(data, str):
                data = json.loads(data)
            alien_name = data.get("Data", {}).get("AlienName", "")
            alien_number = data.get("AlienNumber", "")
            court_address_full = data.get("Schedule", {}).get("HearingLocationAddress", "")
            judge_name = data.get("Schedule", {}).get("IJ_Name", "")
            hearing_date = data.get("Schedule", {}).get("AdjDate", "").split("T")[0]
            hearing_time = data.get("Schedule", {}).get("AdjTime", "")
            hearing_type = self._get_hearing_type(data.get("Schedule", {}).get("ScheduleType", ""))
            hearing_medium = self._get_hearing_medium(data.get("Schedule", {}).get("HearingMedium", ""))
            hearing_details = {
                "alienName": alien_name,
                "alienNumber": alien_number,
                "courtAddressFull": court_address_full,
                "judgeName": judge_name,
                "hearingDate": hearing_date,
                "hearingTime": hearing_time,
                "hearingType": hearing_type,
                "hearingMedium": hearing_medium
            }
            logger.info(f"Successfully parsed hearing details - {hearing_details}")
            return hearing_details
        except (json.JSONDecodeError, TypeError, KeyError) as e:
            logger.error(f"Failed to parse hearing details - {str(e)}")
            raise DataRetrievalError(f"Failed to parse hearing details - {str(e)}") from e
        
    def _get_hearing_type(self, hearing_type):
        if hearing_type == "MM":
            return Constants.MASTER
        elif hearing_type == "II":
            return Constants.INDIVIDUAL
        else:
            return Constants.OTHER
    
    def _get_hearing_medium(self, hearing_medium):
        if hearing_medium == "P":
            return Constants.MEDIUM_P
        elif hearing_medium == "C":
            return Constants.MEDIUM_C
        else:
            return Constants.OTHER