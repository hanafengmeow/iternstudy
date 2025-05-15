import openai
import os
import logging
from openai.error import OpenAIError
from typing import Any, Dict, Optional
from src.utils.loader_utils import load_txt
from src.constants import Constants


class ContentComposer:
    def __init__(self):
        """Initialize the Content Composer with the OPENAI_API_KEY"""
        self.setup_openai_api_key()

    @staticmethod
    def setup_openai_api_key():
        """Retrieves the OpenAI API key from an environment variable.

        Raises:
        - ValueError: If the OPENAI_API_KEY environment variable is not set.
        """
        api_key = os.getenv("OPENAI_API_KEY")
        if not api_key:
            logging.error("OPENAI_API_KEY environment variable is not set.")
            raise ValueError(
                "No API Key found. Set the OPENAI_API_KEY environment variable."
            )
        openai.api_key = api_key

    def compose(
        self, prompt: str, data: str, model: str = "gpt-4-turbo"
    ) -> Optional[str]:
        """Generates text based on the given prompt and data using OpenAI's API.

        Args:
        - prompt (str): The prompt to send to the model.
        - data (str): The data to send to the model as user content.
        - model (str): The model for generating the text. Default "gpt-4-turbo".

        Returns:
        - Optional[str]: The generated text or None if an error occurs.
        """
        try:
            response = openai.ChatCompletion.create(
                messages=[
                    {"role": "system", "content": prompt},
                    {"role": "user", "content": str(data)},
                ],
                model=model,
                max_tokens=1000,
                temperature=0.7,
            )
            return response["choices"][0]["message"]["content"]
        except OpenAIError as e:
            logging.error("Failed to generate the content: %s", str(e))
            return None

    def generate_asylum_cover_letter(self, data: Dict[str, Any]) -> Optional[str]:
        """Generates an asylum cover letter using client data.

        Args:
        - data (Dict[str, Any]): The dictionary containing client data.
        - cover_letter_prompt (str): The prompt for getting the cover letter.

        Returns:
        - Optional[str]: The generated cover letter as a string, or None if
        an error occurs.

        Raises:
        - ValueError: If client data missing from the provided data dictionary.
        """
        # Load prompt & template
        cover_letter_prompt = load_txt(Constants.ASYLUM_COVERLETTER_PROMPT)
        # Get client data
        client_data = data.get("client")
        if not client_data:
            logging.error("Client data is missing from the case data.")
            raise ValueError(
                "Client data is required for generating Asylum Cover Letter."
            )
        return self.compose(cover_letter_prompt, str(client_data))

    def get_asylum_shipping_address(self, data: Dict[str, Any]) -> None:
        """Get the shipping address data based on client residential address.

        Args:
        - data (Dict[str, Any]): The event data.

        Raises:
            ValueError: If the residential address missing from the client data.
        """
        # Load the prompt text from a file - moved to the caller or initializer
        shipping_address_prompt = load_txt(Constants.ASYLUM_SHIPPING_ADDRESS_PROMPT)

        # Validate required client data
        if "client" not in data or "residential_address" not in data["client"]:
            logging.error("Client residential address is missing.")
            raise ValueError(
                "Client residential address required for getting shipping address."
            )

        # Prepare data for composing the shipping address
        shipping_address_data = {
            "client_address": data["client"]["residential_address"],
            "shipping_method": data.get(
                "shipping_method", Constants.DEFAULT_SHIPPING_METHOD
            ),
        }

        # Compose the shipping address and update the main data dictionary
        return self.compose(shipping_address_prompt, shipping_address_data)

    def generate_personal_statement(self, data: Dict[str, Any]) -> Optional[str]:
        """Generates a personal statement using client data.

        Args:
        - data (Dict[str, Any]): The dictionary containing client data.

        Returns:
        - Optional[str]: The generated personal statement as a string, or None if
        an error occurs.

        Raises:
        - ValueError: If client data missing from the provided data dictionary.
        """
        # Load prompt & template
        personal_statement_prompt = load_txt(Constants.ASYLUM_PERSONAL_STATEMENT_PROMPT)
        # Get client data
        client_data = data

        return self.compose(personal_statement_prompt, str(client_data))

    def generate_chinese_personal_statement(self, data: str) -> Optional[str]:

        # Load prompt & template
        translate_to_chinese = load_txt(Constants.TRANSLATE_TO_CHINESE_PROMPT)
        # Get client data
        client_data = data

        return self.compose(translate_to_chinese, str(client_data))

    def generate_case_summary(self, data: Dict[str, Any]) -> Optional[str]:
        """Generates a case summary using client data.

        Args:
        - data (Dict[str, Any]): The dictionary containing client data.

        Returns:
        - Optional[str]: The generated case summary as a string, or None if
        an error occurs.

        Raises:
        - ValueError: If client data missing from the provided data dictionary.
        """
        # Load prompt & template
        case_summary_prompt = load_txt(Constants.ASYLUM_CASE_SUMMARY_PROMPT)
        # Get client data
        client_data = data

        return self.compose(case_summary_prompt, str(client_data))

    def convert_nationality_to_country(self, data: str) -> Optional[str]:
        nationality_to_country = load_txt(Constants.NATIONALITY_TO_COUNTRY_PROMPT)
        client_data = data
        return self.compose(nationality_to_country, str(client_data))
