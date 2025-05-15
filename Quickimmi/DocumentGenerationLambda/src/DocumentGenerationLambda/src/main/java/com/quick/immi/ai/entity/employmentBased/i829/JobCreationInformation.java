package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCreationInformation {

    // Direct Job Creation Information
    private String fullTimeEmployeesAtInitialInvestment;
    private String fullTimeEmployeesAtFiling;
    private String differenceInFullTimeEmployees;
    private String capitalInvestedNotFromEB5;

    // Indirect Job Creation Information
    private String economicallyDirectIndirectInducedJobsCreated;
    private String capitalFromEB5InvestorsTransferredToJCE;
    private String capitalInvestedInJCENotFromAlienInvestors;

    /**
     * Indicates if the investment was made into a troubled business.
     * Expected values: "Yes" or "No"
     */
    private String investingInTroubledBusinessYes;
    private String investingInTroubledBusinessNo;

    // Information if invested in a troubled business
    private String fullTimePositionsMaintainedInTroubledBusiness;
    private String fullTimePositionsCreatedInTroubledBusiness;

    /**
     * Number of full-time jobs expected to be created if ten full-time jobs have not yet been created.
     */
    private String expectedNumberOfJobs;

    /**
     * Indicates if there have been changes to the business plan.
     * Expected values: "Yes" or "No"
     */
    private String changesToBusinessPlanYes;
    private String changesToBusinessPlanNo;
}
