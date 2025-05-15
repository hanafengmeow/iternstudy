package com.quick.immi.ai.entity.common;

import lombok.Data;

@Data
public class LastArrival {
    private String i94;
    private String lastArrivalDate;
    private String lastArrivalCity;
    private String lastArrivalState;
    private String authorizedStayExpiredDate;
    //only fill one
    private String passportNumber;
    private String travelDocumentNumber;

    private String issueCountry;
    private String expireDate;
    private String lastArrivalImmigrationStatus;
    private String currentNonImmigrationStatus;
    private String nonImmigrationStatusDate;
}
