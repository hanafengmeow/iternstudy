package com.quick.immi.ai.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LawyerProfile {
    private LawyerBasicInfo basicInfo;
    private LawyerEligibility eligibility;
}