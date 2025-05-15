import json
import logging


def load_txt(file_path):
    try:
        with open(file_path, "r", encoding="utf-8") as file:
            return file.read()
    except FileNotFoundError:
        logging.error(f"Error: The file at {file_path} was not found.")
        raise
    except Exception as e:
        logging.error(f"An error occurred while reading the file: {str(e)}")
        raise
    return None


def load_json(file_path):
    with open(file_path, "r", encoding="utf-8") as file:
        return json.load(file)
