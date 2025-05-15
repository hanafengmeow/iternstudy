from collections import namedtuple

# Define a namedtuple to represent the Env structure
Env = namedtuple('Env', ['name', 'db_secret_name', 'generated_document_bucket'])


class Constants:
    # Create a dictionary to map regions to their respective Env objects
    REGION_ENV_MAP = {
        "us-west-1": Env(
            name="dev",
            db_secret_name="AuroraMySqlCdkV2Stack-dev-rds-credentials",
            generated_document_bucket="quickimmi-generated-document-bucket"
        ),
        "us-east-1": Env(
            name="prod",
            db_secret_name="AuroraMySqlCdkV2Stack-rds-credentials",
            generated_document_bucket="quickimmi-generated-document-bucket-prod"
        )
    }

    ASYLUM_COVERLETTER_TEMPLATE = "resources/templates/asylum_coverletter_template.json"
    ASYLUM_PERSONAL_STATEMENT_TEMPLATE = (
        "resources/templates/asylum_personal_statement_template.json"
    )
    EOIR_COVERLETTER_TEMPLATE = (
        "resources/templates/asylum_eoir_coverletter_template.json"
    )
    EOIR_PROOFOFSERVICE_TEMPLATE = (
        "resources/templates/asylum_eoir_proofofservice_template.json"
    )
    MARRIAGE_LICENSE_TEMPLATE = ("resources/templates/marriage_license_english.json")
    EOIR_PLEADING_TEMPLATE = "resources/templates/asylum_pleading_template.json"
    ASYLUM_CERTIFICATE_OF_TRANSLATION_TEMPLATE = ("resources/templates/asylum_certificate_of_translation_template.json")
    DEFAULT_SHIPPING_METHOD = "USPS"
    ASYLUM_COVERLETTER_PROMPT = "config/prompts/PromptAsylumCoverLetter.txt"
    ASYLUM_SHIPPING_ADDRESS_PROMPT = "config/prompts/PromptAsylumShippingAddress.txt"
    ASYLUM_PERSONAL_STATEMENT_PROMPT = (
        "config/prompts/PromptAsylumPersonalStatement.txt"
    )
    TRANSLATE_TO_CHINESE_PROMPT = "config/prompts/PromptTranslateToChinese.txt"
    ASYLUM_CASE_SUMMARY_PROMPT = "config/prompts/PromptAsylumCaseSummary.txt"
    NATIONALITY_TO_COUNTRY_PROMPT = "config/prompts/PromptNationalityToCountry.txt"
