# Define a namedtuple to represent the Env structure
from collections import namedtuple

Env = namedtuple('Env', ['name', 'db_secret_name', 'generated_document_bucket'])

class Constants:
    CAPTCHA_API_KEY = "67b2f1c00d6bb42a2b6bf04940186c9d"
    EOIR_WEBSITE_URL = "https://acis.eoir.justice.gov/en/"
    EOIR_CAPTCHA_SITEKEY = "5e28069e-3532-4d77-a479-a3939690e810"
    EOIR_DATA_URL = "https://eoir-ws.eoir.justice.gov/api/Case/GetCaseInfo"
    TASK_SUCCESS = "Success"
    TASK_FAILED = "Failed"
    MASTER = "Master"
    INDIVIDUAL = "Individual"
    OTHER = "Other"
    MEDIUM_P = "IN-PERSON"
    MEDIUM_C = "INTERNET-BASED"

    REGION_ENV_MAP = {
        "us-west-1": Env(
            name="dev",
            db_secret_name="AuroraMySqlCdkV2Stack-dev-rds-credentials",
            generated_document_bucket="quickimmi-generated-document-bucket"
        ),
        "us-east-1": Env(
            name="prod",
            db_secret_name="TBD",
            generated_document_bucket="quickimmi-generated-document-bucket-prod"
        )
    }
