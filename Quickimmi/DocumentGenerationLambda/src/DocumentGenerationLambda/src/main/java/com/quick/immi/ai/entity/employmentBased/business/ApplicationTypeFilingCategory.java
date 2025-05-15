/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import com.quick.immi.ai.entity.common.NameEntity;
import lombok.Data;

@Data
public class ApplicationTypeFilingCategory {
    //i485 part2 1
    private boolean isFilingWithEOIRYes;
    private boolean isFilingWithEOIRNo;

    //i485 part2 2
    private String receiptNumber;
    private String priorityDate;

    private boolean isPrincipalApplicant;
    private boolean isDerivativeApplicant;


    private NameEntity principalApplicantName;
    private String principalApplicantANumber;
    private String principalApplicantDateOfBirth;

}
