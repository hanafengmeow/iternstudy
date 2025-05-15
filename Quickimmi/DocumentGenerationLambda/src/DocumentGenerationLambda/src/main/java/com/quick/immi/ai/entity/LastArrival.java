package com.quick.immi.ai.entity;

import lombok.Data;

@Data
public class LastArrival {
    private String i94;
    //only fill one
    private String passportNumber;
    private String travelDocumentNumber;

    private String issueCountry;
    private String expireDate;
    private String lastArrivalDate;
    private String lastArrivalPlace;
    private String lastArrivalImmigrationStatus;
    private String currentImmigrationStatus;
    private String sevisNumber;
}
