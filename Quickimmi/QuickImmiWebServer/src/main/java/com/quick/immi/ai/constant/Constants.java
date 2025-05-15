/* (C) 2024 */
package com.quick.immi.ai.constant;

import static com.quick.immi.ai.entity.DocumentType.*;

import com.quick.immi.ai.entity.DocumentType;
import java.util.List;

public class Constants {
  // DocuSign
  public static String DOCUMENT_TYPE_PDF = "pdf";
  public static String EMAIL_SUBJECT_SIGN_DOCUMENT = "Please sign this document";
  public static String AUTHENTICATION_METHOD_NONE = "none";
  public static String PING_FREQUENCY_600_SECONDS = "600";
  public static String STATE_VALUE_CASE_DETAIL = "?state=caseDetail";
  public static String ANCHOR_TAG_SN1 = "/sn1/";
  public static int TOKEN_EXPIRATION_SECONDS = 3600;
  public static String SIGNATURE_SCOPE = "signature";
  public static String IMPERSONATION_SCOPE = "impersonation";

  public static Long FAILED_FORM_GENERATION_TASK_ID = -1l;

  public static List<DocumentType> SUPPORTED_COVER_LETTER_DOCUMENTS =
      List.of(
          COVER_LETTER_FOR_AFFIRMATIVE_ASYLUM,
          EOIR_COVERLETTER_FOR_I589_FORM,
          EOIR_COVERLETTER_FOR_PERSONAL_STATEMENT,
          EOIR_COVERLETTER_FOR_WRITTEN_PLEADING,
          EOIR_COVERLETTER_FOR_SUPPORTING_DOCUMENTS,
          EOIR_PROOFOFSERVICE_FOR_I589_FORM,
          EOIR_PROOFOFSERVICE_FOR_PERSONAL_STATEMENT,
          EOIR_PROOFOFSERVICE_FOR_WRITTEN_PLEADING,
          EOIR_PROOFOFSERVICE_FOR_SUPPORTING_DOCUMENTS,
          PERSONAL_STATEMENT_ENGLISH,
          PERSONAL_STATEMENT_ORIGINAL,
          CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT);

  public static List<String> SUPPORTED_COVER_LETTER_DOCUMENT_NAMES =
      List.of(
          COVER_LETTER_FOR_AFFIRMATIVE_ASYLUM.getName(),
          EOIR_COVERLETTER_FOR_I589_FORM.getName(),
          EOIR_COVERLETTER_FOR_PERSONAL_STATEMENT.getName(),
          EOIR_COVERLETTER_FOR_WRITTEN_PLEADING.getName(),
          EOIR_COVERLETTER_FOR_SUPPORTING_DOCUMENTS.getName(),
          EOIR_PROOFOFSERVICE_FOR_I589_FORM.getName(),
          EOIR_PROOFOFSERVICE_FOR_PERSONAL_STATEMENT.getName(),
          EOIR_PROOFOFSERVICE_FOR_WRITTEN_PLEADING.getName(),
          EOIR_PROOFOFSERVICE_FOR_SUPPORTING_DOCUMENTS.getName(),
          PERSONAL_STATEMENT_ENGLISH.getName(),
          PERSONAL_STATEMENT_ORIGINAL.getName(),
          CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT.getName());

  public static List<DocumentType> SUPPORTED_AFFIRMATIVE_COVER_LETTER_DOCUMENTS =
      List.of(
          COVER_LETTER_FOR_AFFIRMATIVE_ASYLUM,
          PERSONAL_STATEMENT_ENGLISH,
          PERSONAL_STATEMENT_ORIGINAL,
          CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT);

  public static List<DocumentType> SUPPORTED_DEFENSIVE_COVER_LETTER_DOCUMENTS =
      List.of(
          EOIR_COVERLETTER_FOR_I589_FORM,
          EOIR_COVERLETTER_FOR_PERSONAL_STATEMENT,
          EOIR_COVERLETTER_FOR_WRITTEN_PLEADING,
          EOIR_COVERLETTER_FOR_SUPPORTING_DOCUMENTS,
          EOIR_PROOFOFSERVICE_FOR_I589_FORM,
          EOIR_PROOFOFSERVICE_FOR_PERSONAL_STATEMENT,
          EOIR_PROOFOFSERVICE_FOR_WRITTEN_PLEADING,
          EOIR_PROOFOFSERVICE_FOR_SUPPORTING_DOCUMENTS,
          PERSONAL_STATEMENT_ENGLISH,
          PERSONAL_STATEMENT_ORIGINAL,
          CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT);

  public static List<DocumentType> MAIN_FROM_SET =
      List.of(
          DocumentType.I589,
          DocumentType.EOIR28,
          DocumentType.G28,
          DocumentType.G28_FOR_APPLICANT,
          DocumentType.G28_FOR_SPOUSE,
          DocumentType.G28_FOR_CHILD_1,
          DocumentType.G28_FOR_CHILD_2,
          DocumentType.G28_FOR_CHILD_3,
          DocumentType.G28_FOR_CHILD_4,
          DocumentType.G28_FOR_CHILD_5,
          DocumentType.G28_FOR_CHILD_6,
          DocumentType.G28_FOR_CHILD_7,
          DocumentType.G28_FOR_CHILD_8,
          DocumentType.G28_FOR_CHILD_9,
          DocumentType.G28_FOR_CHILD_10);
}
