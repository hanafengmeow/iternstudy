from sqlalchemy import create_engine, Column, Integer, BigInteger, String, JSON, Boolean
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()


class Document(Base):
    __tablename__ = "document_table"
    id = Column(BigInteger, primary_key=True, autoincrement=True)
    user_id = Column(Integer, nullable=False)
    case_id = Column(BigInteger, nullable=False)
    status = Column(String(255), nullable=False)
    info = Column(JSON)
    type = Column(String(255), nullable=False)
    file_type = Column(String(255), nullable=False)
    name = Column(String(255), nullable=False)
    identify = Column(String(50), nullable=False)
    created_by = Column(String(255))
    s3_location = Column(String(255), nullable=False)
    created_at = Column(BigInteger, nullable=False)
    updated_at = Column(BigInteger, nullable=False)


class FormGenerationTask(Base):
    __tablename__ = "form_generation_task_table"
    id = Column(BigInteger, primary_key=True, autoincrement=True)
    user_id = Column(Integer)
    lawyer_id = Column(Integer)
    case_id = Column(BigInteger)
    document_id = Column(BigInteger)
    form_name = Column(String(100))
    custom_metadata = Column(JSON, name="metadata")
    status = Column(String(100))
    s3_location = Column(String(100))
    created_at = Column(BigInteger)
    updated_at = Column(BigInteger)


class ApplicationCase(Base):
    __tablename__ = "application_case_table"
    id = Column(BigInteger, primary_key=True, autoincrement=True)
    user_id = Column(Integer)
    applicant_name = Column(String(255))
    case_type = Column(String(100), name="type")
    summary = Column(JSON)
    description = Column(String(10000))
    reason = Column(String(255))
    current_step = Column(String(100))
    profile = Column(JSON)
    submitted = Column(Integer)
    submitted_at = Column(BigInteger)
    uscis_receipt_number = Column(String(100))
    assigned_lawyer = Column(Integer)
    created_at = Column(BigInteger)
    updated_at = Column(BigInteger)
    email = Column(String(200))
    created_by = Column(Integer)
    created_by_lawyer = Column(Boolean)
    progress = Column(JSON)
