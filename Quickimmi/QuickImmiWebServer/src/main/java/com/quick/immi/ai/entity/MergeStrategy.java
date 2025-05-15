/* (C) 2024 */
package com.quick.immi.ai.entity;

import java.util.List;

public enum MergeStrategy {
  MERGED_DOCUMENT_FOR_AFFIRMATIVE_ASYLUM(
      "merged_document_for_affirmative_asylum",
      List.of(
          "cover_letter_for_affirmative_asylum",
          "g-28",
          "i-589",
          "i-589_supplement",
          "personal_statement_english",
          "personal_statement_original",
          "i94",
          "supporting_document",
          "certificate_of_translation_for_personal_statement")),
  MERGED_I589_FOR_DEFENSIVE_ASYLUM(
      "merged_i589_for_defensive_asylum",
      List.of(
          "eoir_coverletter_for_i589_form",
          "i-589",
          "i-589_supplement",
          "eoir-28",
          "eoir_proofofservice_for_i589_form")),
  MERGED_PERSONAL_STATEMENT_FOR_DEFENSIVE_ASYLUM(
      "merged_personal_statement_for_defensive_asylum",
      List.of(
          "eoir_coverletter_for_personal_statement",
          "personal_statement_original",
          "personal_statement_english",
          "certificate_of_translation_for_personal_statement",
          "eoir_proofofservice_for_personal_statement")),
  MERGED_WRITTEN_PLEADING_FOR_DEFENSIVE_ASYLUM(
      "merged_written_pleading_for_defensive_asylum",
      List.of(
          "eoir_coverletter_for_written_pleading",
          "asylum_pleading",
          "eoir_proofofservice_for_written_pleading")),
  MERGED_SUPPORTING_DOCUMENTS_FOR_DEFENSIVE_ASYLUM(
      "merged_supporting_documents_for_defensive_asylum",
      List.of(
          "eoir_coverletter_for_supporting_documents",
          "supporting_document",
          "eoir_proofofservice_for_supporting_documents")),
  MERGED_PASSPORT_FOR_APPLICANT("merged_passport_for_applicant", List.of("applicant")),
  MERGED_PASSPORT_FOR_SPOUSE("merged_passport_for_spouse", List.of("spouse")),
  MERGED_PASSPORT_FOR_CHILD_1("merged_passport_for_child_1", List.of("child_1")),
  MERGED_PASSPORT_FOR_CHILD_2("merged_passport_for_child_2", List.of("child_2")),
  MERGED_PASSPORT_FOR_CHILD_3("merged_passport_for_child_3", List.of("child_3")),
  MERGED_PASSPORT_FOR_CHILD_4("merged_passport_for_child_4", List.of("child_4")),
  MERGED_PASSPORT_FOR_CHILD_5("merged_passport_for_child_5", List.of("child_5")),
  MERGED_PASSPORT_FOR_CHILD_6("merged_passport_for_child_6", List.of("child_6")),
  MERGED_PASSPORT_FOR_CHILD_7("merged_passport_for_child_7", List.of("child_7")),
  MERGED_PASSPORT_FOR_CHILD_8("merged_passport_for_child_8", List.of("child_8")),
  MERGED_PASSPORT_FOR_CHILD_9("merged_passport_for_child_9", List.of("child_9")),
  MERGED_PASSPORT_FOR_CHILD_10("merged_passport_for_child_10", List.of("child_10")),
  MERGED_PASSPORT_FOR_CHILD_11("merged_passport_for_child_11", List.of("child_11")),
  MERGED_PASSPORT_FOR_CHILD_12("merged_passport_for_child_12", List.of("child_12")),
  MERGED_PASSPORT_FOR_CHILD_13("merged_passport_for_child_13", List.of("child_13"));

  private String name;
  private List<String> documentTypes;

  MergeStrategy(String name, List<String> documentTypes) {
    this.name = name;
    this.documentTypes = documentTypes;
  }

  public String getName() {
    return name;
  }

  public List<String> getDocumentTypes() {
    return documentTypes;
  }

  @Override
  public String toString() {
    return String.format("MergeStrategy{name='%s', documentTypes='%s'}", name, documentTypes);
  }
}
