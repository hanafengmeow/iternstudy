package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

@Data
public class UsEmbassy {
    private String pageNumber = "4";
    private String partNumber = "3";
    private String itemNumber = "2-4";

    // Page 4, Question 2a - US Embassy City
    private String city;
    // Page 4, Question 2b - US Embassy Country
    private String country;
    // Page 4, Question 3 - Visa Decision
    private String visaDecision;
    // Page 4, Question 4 - Visa Decision Date
    private String visaDecisionDate;
}
