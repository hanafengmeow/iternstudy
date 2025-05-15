package com.quick.immi.ai.entity.asylum.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressHistory {
    private final String part = "Part A.III";
    //can be Q1 or Q2
    private String question;
    private String numberAndStreet;
    private String city;
    private String province;
    private String country;
    private String startDate;
    private String endDate;
}
