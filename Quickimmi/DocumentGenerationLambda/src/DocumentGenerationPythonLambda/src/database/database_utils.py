from sqlalchemy.orm import Session
from src.database.models import Document
from src.database.models import FormGenerationTask
from src.database.models import ApplicationCase
from src.database.db_config import SessionLocal
import logging
import time

class DatabaseUtils:
    def create_document(self, document):
        session = SessionLocal()
        try:
            session.add(document)
            session.commit()
            logging.info(f"Document record created - document id: {document.id}")
        except Exception as e:
            session.rollback()
            logging.error(f"Failed to create document: {str(e)}")
            raise Exception("Failed to create document") from e
        finally:
            session.close()
            
    def update_database_after_generation_task_failure(self, task_id, case_id, document_type):
        session = SessionLocal()
        try:
            generation_status = "Failed"
            current_time = int(time.time() * 1000)
            # Check if the document already exists by case id and type
            existing_document = self.get_document_by_case_id_and_type(
                case_id, document_type
            )
            if existing_document:
                # Update the existing document record
                self.update_document(
                    document_id = existing_document.id,
                    updated_at = current_time,
                    generation_status = generation_status,
                    s3_location = ""
                )
            else:
                # Document does not exist
                logging.error(f"Document {document_type} - {case_id} missing from document table")
                raise ValueError(f"Document {document_type} - {case_id} missing from document table")

            document_id = existing_document.id
            self.update_form_generation_task(
                task_id, document_id, "", generation_status, current_time
            )
        except Exception as e:
            session.rollback()
            logging.error(
                f"Failed to update database after generation task failure: {str(e)}"
            )
            raise Exception(
                f"Failed to update database after generation task failure : {str(e)}"
            )
        finally:
            session.close()

    def get_document_by_case_id_and_type(self, case_id, type):
        session = SessionLocal()
        try:
            document = (
                session.query(Document)
                .filter_by(case_id=case_id, type=type, identify="applicant")
                .one_or_none()
            )
            logging.info(f"Document found in database: {document}")
            return document
        except Exception as e:
            logging.error(
                f"Failed to get document by case id {case_id} and type {type}: {str(e)}"
            )
            raise Exception(f"Failed to get document by case id {case_id} and type {type}") from e
        finally:
            session.close()

    def update_document(self, document_id, updated_at, generation_status, s3_location, error_message=None):
        session = SessionLocal()
        try:
            # Retrieve the document within the same session to ensure it's managed
            document = session.query(Document).filter(Document.id == document_id).one()
            # Update the fields that need changing
            document.updated_at = updated_at
            document.created_by = "System"
            document.status = generation_status
            document.s3_location = s3_location
            if error_message:
                if document.info:
                    if "error" in document.info:
                        document.info["error"]["source"] = "Python Lambda"
                        document.info["error"]["message"] = error_message
                    else:
                        document.info["error"] = {"source": "Python Lambda", "message": error_message}
                else:
                    document.info = {"error": {"source": "Python Lambda", "message": error_message}}
            session.add(document)
            session.commit()
            print(f"Document record updated - document id: {document.id}")
        except Exception as e:
            # Rollback in case of exception
            session.rollback()
            logging.error(f"Failed to update document {document.id}: {str(e)}")
            raise Exception(f"Fail to update Document {document.id}") from e
        finally:
            session.close()

    def get_form_generation_task_by_task_id(self, task_id):
        session = SessionLocal()
        try:
            form_generation_task = (
                session.query(FormGenerationTask)
                .filter(FormGenerationTask.id == task_id)
                .one()
            )
            print(f"Got form_generation_task {form_generation_task} ")
            return form_generation_task
        except Exception as e:
            logging.error(f"Failed to get generation task by task id {task_id}: {str(e)}")
            raise Exception(f"Failed to get generation task by task id {task_id}") from e
        finally:
            session.close()

    def update_form_generation_task(
        self, task_id, document_id, s3_location, status, updated_at
    ):
        session = SessionLocal()
        try:
            # Retrieve the form_generation_task within the same session to ensure it's managed
            form_generation_task = (
                session.query(FormGenerationTask)
                .filter(FormGenerationTask.id == task_id)
                .one()
            )
            print(f"Got form_generation_task {form_generation_task} ")
            # Update the fields that need changing
            form_generation_task.document_id = document_id
            form_generation_task.s3_location = s3_location
            form_generation_task.status = status
            form_generation_task.updated_at = updated_at
            session.add(form_generation_task)
            session.commit()
            print(
                f"Form generation task record updated - task id: {form_generation_task.id}"
            )
        except Exception as e:
            # Rollback in case of exception
            session.rollback()
            logging.error(
                f"Failed to update form generation task {task_id}: {e}", exc_info=True
            )
            raise Exception(f"Fail to update Form generation task {task_id}") from e
        finally:
            session.close()

    def update_application_case_description(self, case_id, description):
        session = SessionLocal()
        application_case = None
        try:
            # Retrieve the application_case within the same session to ensure it's managed
            application_case = (
                session.query(ApplicationCase)
                .filter(ApplicationCase.id == case_id)
                .one()
            )
            print(f"Got application_case {application_case}")
            # Update the fields that need changing
            application_case.description = description
            session.add(application_case)
            session.commit()
            print(f"Application case record updated - case id: {application_case.id}")
        except Exception as e:
            # Rollback in case of exception
            session.rollback()
            if application_case:
                logging.error(
                    f"Failed to update application case {application_case.id}: {str(e)}"
                )
            else:
                logging.error(
                    f"Failed to retrieve application case {case_id}: {str(e)}"
                )
            raise Exception(f"Fail to update Application case {case_id}") from e
        finally:
            session.close()
