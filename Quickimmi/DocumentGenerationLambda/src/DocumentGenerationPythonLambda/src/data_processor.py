import re
from src.content_composer import ContentComposer
from datetime import datetime

class DataProcessor:
    def __init__(self):
        self.set_state_data()

    def process_eoir_proofofservice_data(self, data):
        # Process attorney name
        data["attorney"]["firstName"] = data["attorney"]["firstName"].capitalize()
        data["attorney"]["lastName"] = data["attorney"]["lastName"].capitalize()
        # format attorney address
        data["attorney"]["address"] = data["attorney"]["address"].strip().title()
        data["attorney"]["city"] = data["attorney"]["city"].strip().title()
        # Process client firstName and lastName
        data["client"]["firstNameUpper"] = data["client"]["firstName"].upper()
        data["client"]["lastNameUpper"] = data["client"]["lastName"].upper()
        # Get state full name
        data["attorney"]["attorneyBarStateFull"] = self.abbreviation_to_state[
            data["attorney"]["attorneyBarState"]
        ]
        # Get title based on client gender
        data["client"]["genderTitle"] = self._get_title_based_on_gender(
            data["client"]["gender"]
        )
        data["client"]["additionalApplicantsUpper"] = self._process_additional_names(
            data["client"]["additionalApplicants"]
        )
        data["client"]["additionalApplicantAlienNumber"] = self._process_additional_anumber(
            data["client"]["additionalApplicantAlienNumber"]
        )
        data["client"]["alienNumber"] = self._format_anumber(data["client"]["alienNumber"])
        # Format court address
        data["caseInfo"]["courtAddress"] = data["caseInfo"]["courtAddress"].strip().upper()
        data["caseInfo"]["courtCity"] = data["caseInfo"]["courtCity"].strip().upper()
        data["caseInfo"]["courtState"] = data["caseInfo"]["courtState"].strip().upper()
        data["caseInfo"]["judgeName"] = data["caseInfo"]["judgeName"].strip().title()
        data["caseInfo"]["hearingDate"] = self._format_date(data["caseInfo"]["hearingDate"])
        return data

    def process_eoir_coverletter_data(self, data):
        # Process attorney name
        data["attorney"]["firstName"] = data["attorney"]["firstName"].capitalize()
        data["attorney"]["lastName"] = data["attorney"]["lastName"].capitalize()
        # format attorney address
        data["attorney"]["address"] = data["attorney"]["address"].strip().title()
        data["attorney"]["city"] = data["attorney"]["city"].strip().title()
        # Process client firstName and lastName
        data["client"]["firstNameUpper"] = data["client"]["firstName"].upper()
        data["client"]["lastNameUpper"] = data["client"]["lastName"].upper()
        # This is only required for the cover letter, since proof of service does not have this content
        document_cleanup = self._remove_special_characters(data["document"])
        data["documentUpper"] = document_cleanup.upper()
        # Format alienNumber
        data["client"]["additionalApplicantsUpper"] = self._process_additional_names(
            data["client"]["additionalApplicants"]
        )
        data["client"]["additionalApplicantAlienNumber"] = self._process_additional_anumber(
            data["client"]["additionalApplicantAlienNumber"]
        )
        data["client"]["alienNumber"] = self._format_anumber(data["client"]["alienNumber"])
        # Format court address
        data["caseInfo"]["courtAddress"] = data["caseInfo"]["courtAddress"].strip().upper()
        data["caseInfo"]["courtCity"] = data["caseInfo"]["courtCity"].strip().upper()
        data["caseInfo"]["courtState"] = data["caseInfo"]["courtState"].strip().upper()
        data["caseInfo"]["judgeName"] = data["caseInfo"]["judgeName"].strip().title()
        data["caseInfo"]["hearingDate"] = self._format_date(data["caseInfo"]["hearingDate"])
        return data

    def process_eoir_pleading_data(self, data):
        data["client"]["firstName"] = data["client"]["firstName"].capitalize()
        data["client"]["lastName"] = data["client"]["lastName"].capitalize()
        data["attorney"]["firstNameUpper"] = data["attorney"]["firstName"].upper()
        data["attorney"]["lastNameUpper"] = data["attorney"]["lastName"].upper()
        data["client"]["nativeLanguage"] = data["client"]["nativeLanguage"].capitalize()
        data["client"]["mostRecentEntryCity"] = data["client"][
            "mostRecentEntryCity"
        ].upper()
        data["client"]["mostRecentEntryState"] = data["client"][
            "mostRecentEntryState"
        ].upper()
        data["client"]["presentNationality"] = self.convert_nationality_to_country(
            data["client"]["presentNationality"]
        )
        return data
    
    def _format_date(self, date_str):
        if not date_str:
            return date_str 
        try:
            # Convert the date string "10/14/2025" to a datetime object
            date_obj = datetime.strptime(date_str, "%m/%d/%Y")
            # Format the datetime object to "October 14, 2025"
            formatted_date = date_obj.strftime("%B %d, %Y")
            return formatted_date
        except ValueError:
            # If the date format is incorrect, return the original string
            return date_str

    def _process_additional_names(self, additionalApplicants):
        result = ""
        if additionalApplicants.strip():
            upper_cased_string = additionalApplicants.upper()
            parts = upper_cased_string.split(",")
            trimmed_parts = [part.strip() + "," for part in parts]
            result = "\n".join(trimmed_parts)
        return result

    def _process_additional_anumber(self, numbers):
        result = ""
        if numbers.strip():
            upper_cased_string = numbers.upper()
            parts = upper_cased_string.split(",")
            formatted_parts = []
            for part in parts:
                formatted_part = self._format_anumber(part)
                formatted_parts.append("[" + formatted_part + "]")
            result = "\n".join(formatted_parts)
        return result
    
    def _format_anumber(self, alienNumber):
        # Check if alienNumber is an integer
        if isinstance(alienNumber, int):
            alienNumber = self._group_numbers(alienNumber)
            return f"A{alienNumber}"
        elif isinstance(alienNumber, str):
             # Keep only digits
            alienNumber = re.sub(r"[^\d]", "", alienNumber)
            # If there are digits, prepend "A-"
            if alienNumber:
                alienNumber = self._group_numbers(alienNumber)
                return f"A{alienNumber}"
            else:
                 # Return "A-" even if there are no digits
                return "A-" 
        else:
            # In case alienNumber is none of the above, return it as it is
            return alienNumber

    def _group_numbers(self, numbers):
        groups = [numbers[i:i+3] for i in range(0, len(numbers), 3)]
        # Join with a maximum of two hyphens
        if len(groups) > 3:
            return '-'.join(groups[:3]) + ''.join(groups[3:])
        else:
            return '-'.join(groups)

    def convert_nationality_to_country(self, nationality):
        country = ContentComposer().convert_nationality_to_country(nationality)
        # country = "chinese"
        if country.lower() == "chinese":
            country = "CHINA, PEOPLES REPUBLIC OF CHINA"
        return country.upper()

    def _get_title_based_on_gender(self, gender):
        if gender.lower() == "female":
            return "Ms."
        elif gender.lower() == "male":
            return "Mr."
        else:
            return ""

    def _remove_special_characters(self, input_string):
        return re.sub(r"[^A-Za-z0-9 ]+", "", input_string)

    def set_state_data(self):
        self.state_to_abbreviation = {
            "Alabama": "AL",
            "Alaska": "AK",
            "Arizona": "AZ",
            "Arkansas": "AR",
            "California": "CA",
            "Colorado": "CO",
            "Connecticut": "CT",
            "Delaware": "DE",
            "Florida": "FL",
            "Georgia": "GA",
            "Hawaii": "HI",
            "Idaho": "ID",
            "Illinois": "IL",
            "Indiana": "IN",
            "Iowa": "IA",
            "Kansas": "KS",
            "Kentucky": "KY",
            "Louisiana": "LA",
            "Maine": "ME",
            "Maryland": "MD",
            "Massachusetts": "MA",
            "Michigan": "MI",
            "Minnesota": "MN",
            "Mississippi": "MS",
            "Missouri": "MO",
            "Montana": "MT",
            "Nebraska": "NE",
            "Nevada": "NV",
            "New Hampshire": "NH",
            "New Jersey": "NJ",
            "New Mexico": "NM",
            "New York": "NY",
            "North Carolina": "NC",
            "North Dakota": "ND",
            "Ohio": "OH",
            "Oklahoma": "OK",
            "Oregon": "OR",
            "Pennsylvania": "PA",
            "Rhode Island": "RI",
            "South Carolina": "SC",
            "South Dakota": "SD",
            "Tennessee": "TN",
            "Texas": "TX",
            "Utah": "UT",
            "Vermont": "VT",
            "Virginia": "VA",
            "Washington": "WA",
            "West Virginia": "WV",
            "Wisconsin": "WI",
            "Wyoming": "WY",
        }
        self.abbreviation_to_state = {
            v: k for k, v in self.state_to_abbreviation.items()
        }
