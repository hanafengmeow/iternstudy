package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddressHistory {
    private String part = "Part A.III";
    //can be Q1 or Q2
    private String question;
    private String numberAndStreet;
    private String city;
    private String province;
    private String country;
    private String startDate;
    private String endDate;
}
