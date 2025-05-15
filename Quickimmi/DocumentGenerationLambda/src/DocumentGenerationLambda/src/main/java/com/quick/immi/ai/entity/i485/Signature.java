package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class Signature {

    private String numbersOfChanges;
    private String changesNumberedFrom; // The starting number of changes made to the application
    private String changesNumberedThrough; // The ending number of changes made to the application
    private String additionalPagesNumberedFrom; // The starting number of additional pages submitted
    private String additionalPagesNumberedThrough; // The ending number of additional pages submitted
    
    private String uscisOfficerPrintedName; // USCIS Officer's Printed Name or Stamp
    private String uscisOfficerSignature; // USCIS Officer's Signature (sign in ink)
    private String applicantSignature; // Applicant's Signature (sign in ink)
    private String dateOfSignature; // Date of Signature (mm/dd/yyyy)
}
