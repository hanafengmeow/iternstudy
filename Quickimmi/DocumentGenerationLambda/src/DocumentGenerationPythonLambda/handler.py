import logging
import json
from src.database.database_utils import DatabaseUtils
from src.document_orchestrator import DocumentOrchestrator
from resources.mock_event_data import MOCK_EVENT_DATA

def handler(event, context):
    try:
        for record in event["Records"]:
            # Retrieve the message body
            message_body = json.loads(record["body"])
            # Process the message as needed
            print("Received message: %s" % message_body)
            metadata = message_body.get("metadata")
            print("type of metadata %s" % type(metadata))
            case_data = json.loads(metadata)
            taskid = message_body.get("id")
            print("taskid id: %s" % taskid)
            case_data["taskId"] = taskid
            print("case data: %s" % case_data)

            document_type = case_data.get("documentType")
            if document_type == "cover_letter_for_affirmative_asylum":
                validate_asylum_input(case_data)
                DocumentOrchestrator.build_asylum_cover_letter(case_data)
            elif document_type == "eoir_coverletter":
                validate_eoir_input(case_data)
                DocumentOrchestrator.build_eoir_cover_letter(case_data)
            elif document_type == "eoir_proofofservice":
                validate_eoir_input(case_data)
                DocumentOrchestrator.build_eoir_proof_of_service(case_data)
            elif document_type == "personal_statement":
                validate_asylum_input(case_data)
                DocumentOrchestrator.build_personal_statement(case_data)
            elif document_type == "asylum_pleading":
                validate_asylum_input(case_data)
                DocumentOrchestrator.build_asylum_pleading(case_data)
            elif document_type == "marriage_license_english":
                validate_marriage_license_input(case_data)
                DocumentOrchestrator.build_english_marriage_license(case_data)
            elif document_type == "certificate_of_translation":
                DocumentOrchestrator.build_certificate_of_translation(case_data)
            else:
                logging.error("Unsupported document type received: %s", document_type)
                raise ValueError("Unsupported document type %s".format(document_type))
    except Exception as e:
        logging.error("Failed to generate document: %s", str(e))
        # update the task status to failed
        db = DatabaseUtils()
        db.update_database_after_generation_task_failure(
            taskid, case_data.get("caseId"), case_data.get("documentName")
        )
        # Update document generation task status to failed
        raise Exception(f"Failed to generate document {str(e)}")

def validate_asylum_input(case_data):
    case_id = case_data.get("caseId")
    user_id = case_data.get("userId")
    if not case_id or not user_id:
        logging.error("caseId or userId is missing from the event data.")
        raise ValueError("caseId or userId is required for generating document")

def validate_eoir_input(case_data):
    case_id = case_data.get("caseId")
    user_id = case_data.get("userId")
    if not case_id or not user_id:
        logging.error("caseId or userId is missing from the event data.")
        raise ValueError("caseId or userId is required for generating document")
    alienNumber = case_data.get("client", {}).get("alienNumber", None)
    if not alienNumber:
        logging.error("alienNumber is missing from the event data.")
        raise ValueError("alienNumber is required for generating document")
    
def validate_marriage_license_input(case_data):
    marriage_certificate = case_data.get("marriageCertificate", {})
    case_id = case_data.get("caseId")
    user_id = case_data.get("userId")
    if not case_id or not user_id:
        logging.error("caseId or userId is missing from the event data.")
        raise ValueError("caseId or userId is required for generating document")
    if not marriage_certificate:
        logging.error("Marriage certificate is missing from the event data.")
        raise ValueError("Marriage certificate is required for generating document")


if __name__ == "__main__":
    handler(MOCK_EVENT_DATA, None)