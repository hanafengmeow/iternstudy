package com.quick.immi.ai.entity.asylum.business;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String streetNumberAndName;
    private String aptNumber;
    private String city;
    private String state;
    private String zipCode;
}
