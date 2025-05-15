import json
import os
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def save_json_to_local(json_data, file_name):
    with open(file_name, 'w') as f:
        json.dump(json_data, f, indent=2)
    logger.info(f"Data saved to {file_name}")
    

def save_txt_to_local(txt_data, file_name):
        file_path = os.path.join(os.getcwd(), file_name)
        with open(file_path, 'w') as text_file:
            text_file.write(str(txt_data))
        logger.info(f"String data saved to {file_name}")