package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class LastArrivalInformation {
    //Part i485 10
    private boolean everInUSYesCheckbox;
    private boolean everInUSNoCheckbox;
    //397 ---> H1B etc. Vistor
    private String arrivedAsAdmission;

    //i485 11
    private boolean admittedAtPortOfEntryCheckbox;
    //temporary worker
    private String admissionEntryDetail;
    private boolean paroledAtPortOfEntryCheckbox;
    private String paroleEntranceDetail;
    private boolean enteredWithoutAdmissionCheckbox;
    private boolean otherEntryMethodCheckbox;
    private String otherEntryDetail;

    //398
    private String i94Number;

    private String i94Status;

    //399
    private String dateOfArrival;
    //396
    private String authorizedStayExpirationDate;
    //400
    private String passportNumber;
    //401
    private String travelDocumentNumber;
    //402
    private String passportIssueCountry;
    //403
    private String expirationDateForPassport;

    private String visaNumber;
    private String visaIssueDate;
    private String arrivalCity;
    private String arrivalState;

    // Page3-Question27: Current immigration status if has changed since you arrived
    private String currentImmigrationStatus;

    // todo new added
    private boolean lastArrivalFirstTimeYes;          // Was last arrival the first time in the U.S.? (Yes/No)
    private boolean lastArrivalFirstTimeNo;

}
