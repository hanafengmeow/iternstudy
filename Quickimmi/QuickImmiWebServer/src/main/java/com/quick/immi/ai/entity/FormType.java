/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum FormType {
  G28("g-28"),
  I589("i-589"),
  I589_SUPPLEMENT("i-589_supplement"),
  EOIR28("eoir-28"),
  ASYLUM_COVER_LETTER("asylum_coverletter"),
  COT("cot"),
  PERSONAL_STATEMENT("personal_statement"),
  EOIR_COVERLETTER_FOR_I589("eoir_coverletter_for_i589"),
  EOIR_COVERLETTER_FOR_PERSONAL_STATEMENT("eoir_coverletter_for_personal_statement"),
  EOIR_COVERLETTER_FOR_WRITTEN_PLEADING("eoir_coverletter_for_written_pleading"),
  EOIR_COVERLETTER_FOR_SUPPORTING_DOCUMENTS("eoir_coverletter_for_supporting_documents"),
  EOIR_PROOFOFSERVICE_FOR_I589("eoir_proofofservice_for_i589"),
  EOIR_PROOFOFSERVICE_FOR_PERSONAL_STATEMENT("eoir_proofofservice_for_personal_statement"),
  EOIR_PROOFOFSERVICE_FOR_WRITTEN_PLEADING("eoir_proofofservice_for_written_pleading"),
  EOIR_PROOFOFSERVICE_FOR_SUPPORTING_DOCUMENTS("eoir_proofofservice_for_supporting_documents"),
  ALL("all"),
  MERGE("merge");

  private String name;

  FormType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
