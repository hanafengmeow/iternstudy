/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum DocumentType {
  PASSPORT_MAIN("passport_main", Operation.REPLACE, GenerationType.USER_UPLOADED),
  ID_CARD("id_card", Operation.REPLACE, GenerationType.USER_UPLOADED),
  TRAVEL_ID("travel_id", Operation.REPLACE, GenerationType.USER_UPLOADED),
  PASSPORT_STAMP_PAGES("passport_stamp_pages", Operation.NEW, GenerationType.USER_UPLOADED),
  DELIVERY_PACKAGE_PHOTO("delivery_package_photo", Operation.NEW, GenerationType.USER_UPLOADED),
  G28("g-28", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_APPLICANT("g-28_for_applicant", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_SPOUSE("g-28_for_spouse", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_1("g-28_for_child_1", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_2("g-28_for_child_2", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_3("g-28_for_child_3", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_4("g-28_for_child_4", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_5("g-28_for_child_5", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_6("g-28_for_child_6", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_7("g-28_for_child_7", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_8("g-28_for_child_8", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_9("g-28_for_child_9", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_CHILD_10("g-28_for_child_10", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I589("i-589", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I589_SUPPLEMENT("i-589_supplement", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  PERSONAL_STATEMENT("personal_statement", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  PERSONAL_STATEMENT_ORIGINAL(
      "personal_statement_original", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  PERSONAL_STATEMENT_CHINESE(
      "personal_statement_chinese", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  PERSONAL_STATEMENT_ENGLISH(
      "personal_statement_english", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT(
      "certificate_of_translation_for_personal_statement",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  MARRIAGE_CERTIFICATE_CHINESE(
      "marriage_certificate_chinese", Operation.REPLACE, GenerationType.USER_UPLOADED),
  MARRIAGE_CERTIFICATE_ORIGINAL(
      "marriage_certificate_original", Operation.REPLACE, GenerationType.USER_UPLOADED),
  MARRIAGE_CERTIFICATE_ENGLISH(
      "marriage_certificate_english", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  CERTIFICATE_OF_TRANSLATION_FOR_MARRIAGE_CERTIFICATE(
      "certificate_of_translation_for_marriage_certificate",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  COVER_LETTER_FOR_AFFIRMATIVE_ASYLUM(
      "cover_letter_for_affirmative_asylum",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  I94("i94", Operation.REPLACE, GenerationType.USER_UPLOADED),
  EOIR28("eoir-28", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  ASYLUM_PLEADING("asylum_pleading", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  SUPPORTING_DOCUMENT("supporting_document", Operation.NEW, GenerationType.USER_UPLOADED),
  OTHER("other", Operation.NEW, GenerationType.USER_UPLOADED),
  EOIR_COVERLETTER_FOR_I589_FORM(
      "eoir_coverletter_for_i589_form", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  EOIR_COVERLETTER_FOR_PERSONAL_STATEMENT(
      "eoir_coverletter_for_personal_statement",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  EOIR_COVERLETTER_FOR_WRITTEN_PLEADING(
      "eoir_coverletter_for_written_pleading",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  EOIR_COVERLETTER_FOR_SUPPORTING_DOCUMENTS(
      "eoir_coverletter_for_supporting_documents",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  EOIR_PROOFOFSERVICE_FOR_I589_FORM(
      "eoir_proofofservice_for_i589_form", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  EOIR_PROOFOFSERVICE_FOR_PERSONAL_STATEMENT(
      "eoir_proofofservice_for_personal_statement",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  EOIR_PROOFOFSERVICE_FOR_WRITTEN_PLEADING(
      "eoir_proofofservice_for_written_pleading",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  EOIR_PROOFOFSERVICE_FOR_SUPPORTING_DOCUMENTS(
      "eoir_proofofservice_for_supporting_documents",
      Operation.REPLACE,
      GenerationType.SYSTEM_AUTO_GENERATED),
  CRAWL_EOIR_HEEARING_DETAILS(
      "crawl_eoir_heearing_details", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  MERGED_I589_FOR_DEFENSIVE_ASYLUM(
      "merged_i589_for_defensive_asylum", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PERSONAL_STATEMENT_FOR_DEFENSIVE_ASYLUM(
      "merged_personal_statement_for_defensive_asylum",
      Operation.REPLACE,
      GenerationType.SYSTEM_MERGED),
  MERGED_WRITTEN_PLEADING_FOR_DEFENSIVE_ASYLUM(
      "merged_written_pleading_for_defensive_asylum",
      Operation.REPLACE,
      GenerationType.SYSTEM_MERGED),
  MERGED_SUPPORTING_DOCUMENTS_FOR_DEFENSIVE_ASYLUM(
      "merged_supporting_documents_for_defensive_asylum",
      Operation.REPLACE,
      GenerationType.SYSTEM_MERGED),
  MERGED_DOCUMENT_FOR_AFFIRMATIVE_ASYLUM(
      "merged_document_for_affirmative_asylum", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_APPLICANT(
      "merged_passport_for_applicant", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_SPOUSE(
      "merged_passport_for_spouse", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_1(
      "merged_passport_for_child_1", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_2(
      "merged_passport_for_child_2", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_3(
      "MERGED_PASSPORT_FOR_CHILD_3", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_4(
      "merged_passport_for_child_4", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_5(
      "merged_passport_for_child_5", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_6(
      "merged_passport_for_child_6", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_7(
      "merged_passport_for_child_7", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_8(
      "merged_passport_for_child_8", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_9(
      "merged_passport_for_child_9", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  MERGED_PASSPORT_FOR_CHILD_10(
      "merged_passport_for_child_10", Operation.REPLACE, GenerationType.SYSTEM_MERGED),
  SIGNED_MERGED_I589_FOR_DEFENSIVE_ASYLUM(
      "signed_merged_i589_for_defensive_asylum", Operation.REPLACE, GenerationType.USER_SIGNED),
  SIGNED_MERGED_PERSONAL_STATEMENT_FOR_DEFENSIVE_ASYLUM(
      "signed_merged_personal_statement_for_defensive_asylum",
      Operation.REPLACE,
      GenerationType.USER_SIGNED),
  SIGNED_MERGED_WRITTEN_PLEADING_FOR_DEFENSIVE_ASYLUM(
      "signed_merged_written_pleading_for_defensive_asylum",
      Operation.REPLACE,
      GenerationType.USER_SIGNED),
  SIGNED_MERGED_SUPPORTING_DOCUMENTS_FOR_DEFENSIVE_ASYLUM(
      "signed_merged_supporting_documents_for_defensive_asylum",
      Operation.REPLACE,
      GenerationType.USER_SIGNED),
  SIGNED_MERGED_DOCUMENT_FOR_AFFIRMATIVE_ASYLUM(
      "signed_merged_document_for_affirmative_asylum",
      Operation.REPLACE,
      GenerationType.USER_SIGNED),
  SIGNED("signed", Operation.NEW, GenerationType.USER_SIGNED),
  APPLICATION_RECEIPT("application_receipt", Operation.REPLACE, GenerationType.USER_UPLOADED),
  BIOMETRICS_RECEIPT("biometrics_receipt", Operation.REPLACE, GenerationType.USER_UPLOADED),
  INTERVIEW_RECEIPT("interview_receipt", Operation.REPLACE, GenerationType.USER_UPLOADED),
  FINAL_RESULT_RECEIPT("final_result_receipt", Operation.REPLACE, GenerationType.USER_UPLOADED),

  // Family Based Documents
  //  I140("i-140", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I130("i-130", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I130a("i-130a", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I131("i-131", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I864("i-864", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I130_SUPPLEMENT("i-130-supplement", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I130a_SUPPLEMENT("i-130a-supplement", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I864_SUPPLEMENT("i-864-supplement", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I485_SUPPLEMENT("i-485-supplement", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  // Common documents
  I485("i-485", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  I765("i-765", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),

  BANK_STATEMENT("bank_statement", Operation.NEW, GenerationType.USER_UPLOADED),
  INVEST_STATEMENT("invest_statement", Operation.NEW, GenerationType.USER_UPLOADED),
  PROPERTY_STATEMENT("property_statement", Operation.NEW, GenerationType.USER_UPLOADED),
  MORTGAGE_STATEMENT("property_statement", Operation.NEW, GenerationType.USER_UPLOADED),
  PAY_STUB("pay_stub", Operation.NEW, GenerationType.USER_UPLOADED),
  TAX_RETURN("tax_return", Operation.NEW, GenerationType.USER_UPLOADED),

  GREEN_CARD("green-card", Operation.REPLACE, GenerationType.USER_UPLOADED),
  DIVORCE_JUDGMENT("divorce_judgment", Operation.NEW, GenerationType.USER_UPLOADED),
  VALID_VISA("valid_visa", Operation.REPLACE, GenerationType.USER_UPLOADED),
  BIRTH_CERTIFICATE_WITH_NOTARIZATION_TRANSLATION(
      "birth_certificate_with_notarization_translation",
      Operation.REPLACE,
      GenerationType.USER_UPLOADED),
  GREEN_CARD_OTHER_COUNTRIES(
      "green_card_other_countries", Operation.NEW, GenerationType.USER_UPLOADED),

  // Supporting Evidence Required:
  JOINT_REAL_ESTATE_PROPERTY(
      "joint_real_estate_property", Operation.NEW, GenerationType.USER_UPLOADED),
  JOINT_HEALTH_INSURANCE("joint_health_insurance", Operation.REPLACE, GenerationType.USER_UPLOADED),
  JOINT_RENTAL_CONTRACT("joint_rental_contract", Operation.REPLACE, GenerationType.USER_UPLOADED),
  JOINT_BANK_ACCOUNT_STATEMENT(
      "joint_bank_account_statement", Operation.NEW, GenerationType.USER_UPLOADED),
  JOINT_UTILITY_BILL("joint_utility_bill", Operation.REPLACE, GenerationType.USER_UPLOADED),
  PHONE_OF_PETITIONER_AND_BENEFICIARY(
      "phone_of_petitioner_and_beneficiary", Operation.REPLACE, GenerationType.USER_UPLOADED),
  ALL("all"),
  G28_FOR_BENEFICIARY(
      "g28_for_beneficiary", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_PETITIONER("g28_for_petitioner", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  G28_FOR_JOINT_SPONSOR(
      "g28_for_joint_sponsor", Operation.REPLACE, GenerationType.SYSTEM_AUTO_GENERATED),
  MERGE("merge");

  private String name;
  private Operation operation;
  private GenerationType generationType;

  DocumentType(String name) {
    this.name = name;
  }

  DocumentType(String name, Operation operation, GenerationType generationType) {
    this.name = name;
    this.operation = operation;
    this.generationType = generationType;
  }

  public Operation getOperation() {
    return operation;
  }

  public GenerationType getGenerationType() {
    return generationType;
  }

  public String getName() {
    return name;
  }

  public static DocumentType fromName(String name) {
    for (DocumentType type : DocumentType.values()) {
      if (type.name.equalsIgnoreCase(name)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No enum constant with name " + name);
  }
}
