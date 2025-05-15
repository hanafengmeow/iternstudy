from src.database.models import FormGenerationTask
from src.database.models import ApplicationCase
from src.database.db_config import SessionLocal
import time
import logging
import json

logger = logging.getLogger()
logger.setLevel(logging.INFO)

class DatabaseUtils:
    def update_task(self, task_id, status, hearing_details):
        session = SessionLocal()
        try:
            # Retrieve the form_generation_task within the same session to ensure it's managed
            crawling_task = (
                session.query(FormGenerationTask)
                .filter(FormGenerationTask.id == task_id)
                .one()
            )
            logger.info(f"Got Task {crawling_task}")
            # Update the fields that need changing
            crawling_task.status = status
            crawling_task.updated_at = time.time()
            crawling_task.result = json.dumps(hearing_details)
            session.add(crawling_task)
            session.commit()
            logger.info(f"{FormGenerationTask.__tablename__ } updated - task id: {task_id}, status: {status}")
        except Exception as e:
            # Rollback in case of exception
            session.rollback()
            logger.error(
                f"Failed to update task {task_id}: {e}", exc_info=True
            )
            raise Exception(f"Fail to update task {task_id}") from e
        finally:
            session.close()

    def update_hearing_details_in_profile(self, case_id, hearing_details):
        session = SessionLocal()
        application_case = None
        try:
            # Retrieve the application_case within the same session to ensure it's managed
            application_case = (
                session.query(ApplicationCase)
                .filter(ApplicationCase.id == case_id)
                .one()
            )
            # TODO: Get profile from application_case and convert to profile object
            
            
            # TODO: Update the profile object with the hearing details
            
            session.add(application_case)
            session.commit()
            print(f"Application case record updated - case id: {application_case.id}")
        except Exception as e:
            # Rollback in case of exception
            session.rollback()
            if application_case:
                logger.error(
                    f"Failed to update application case {application_case.id}: {str(e)}"
                )
            else:
                logger.error(
                    f"Failed to retrieve application case {case_id}: {str(e)}"
                )
            raise Exception(f"Fail to update Application case {case_id}") from e
        finally:
            session.close()
