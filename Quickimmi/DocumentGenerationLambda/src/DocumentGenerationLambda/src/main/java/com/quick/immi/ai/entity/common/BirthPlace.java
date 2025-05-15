package com.quick.immi.ai.entity.common;

import lombok.Data;

@Data
public class BirthPlace {
    private String cityOfTown;
    private String stateOrProvince;
    private String countryOfBirth;
    private String currentCountryOfCitizenship;
    private String relinquishedCountryOfCitizenship;
    private String countryOfLastForeignResidence;
}
