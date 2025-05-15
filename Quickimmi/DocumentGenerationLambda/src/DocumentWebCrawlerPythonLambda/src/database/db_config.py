import os

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
import boto3
import json
from src.constants import Constants


def get_secret():
    region_name = os.environ.get('AWS_REGION')
    secret_name = Constants.REGION_ENV_MAP.get(region_name).db_secret_name

    # Create a Secrets Manager client
    session = boto3.session.Session()
    client = session.client(service_name="secretsmanager", region_name=region_name)

    get_secret_value_response = client.get_secret_value(SecretId=secret_name)
    secret = get_secret_value_response["SecretString"]
    return json.loads(secret)


def db_engine():
    secrets = get_secret()
    username = secrets["username"]
    password = secrets["password"]
    hostname = secrets["host"]
    dbname = secrets["dbname"]

    DATABASE_URL = f"mysql+pymysql://{username}:{password}@{hostname}/{dbname}"
    engine = create_engine(DATABASE_URL, echo=True, pool_pre_ping=True)
    return engine


Engine = db_engine()
SessionLocal = sessionmaker(bind=Engine)
