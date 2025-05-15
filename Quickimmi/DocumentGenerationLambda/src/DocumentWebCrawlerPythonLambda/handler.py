import json
import time
from src.processor.hearing_details_processor import HearingDetailsProcessor
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def handler(event, context):
    for record in event.get('Records', []):
        start_time = time.time()
        # Process each SQS message
        process_sqs_message(record)
        end_time = time.time()
        logger.info(f"This requests completed in {end_time - start_time:.5f} seconds")

def process_sqs_message(record):
    message_body = json.loads(record['body'])
    logger.info(f"Received message: {message_body}")
    # Check if task_id is present in the message
    task_id = message_body.get('id')
    if not task_id:
        logger.error(f"Missing task_id in message: {message_body}")
        raise ValueError(f"Missing task_id in message: {message_body}")
    
    # Process the message based on the event type
    try:
        event_type = message_body.get('formName')
        logger.info(f"Event type: {event_type}")
        if not event_type:
            raise ValueError(f"Missing event_type in message: {message_body}")
        if event_type and event_type == 'eoir_hearing_details':
            HearingDetailsProcessor(task_id, message_body, local_testing=False).process()
        else:
            raise ValueError(f"Unsupported event type: {event_type}")
    
    except ValueError as ve:
        logger.error(f"Value error when process task {task_id}: {str(ve)}")
        logger.info(f"Update db with failed for {task_id}")
        # DatabaseUtils().update_task(task_id, Constants.TASK_FAILED, data=None)
        raise ve

    except Exception as e:
        logger.error(f"Exception when process task {task_id}: {str(e)}")
        logger.info(f"Update db with failed for {task_id}")
        # DatabaseUtils().update_task(task_id, Constants.TASK_FAILED, data=None)
        raise e    

# 245846729
# 246427486 
# 246427455 Not a valid A-number
if __name__ == "__main__":
    event_data = {
        "Records": [
            {
            "body": "{\"task_id\": \"123\", \"a_number\": \"245846729\", \"type\": \"eoir_hearing_details\"}"
            }
        ]
    }
    handler(event_data, None)
