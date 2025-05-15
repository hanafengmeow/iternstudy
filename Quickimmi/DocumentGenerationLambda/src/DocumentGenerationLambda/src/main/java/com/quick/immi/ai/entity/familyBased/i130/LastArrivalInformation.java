package com.quick.immi.ai.entity.familyBased.i130;
import lombok.Data;

@Data
public class LastArrivalInformation {
    //Part 4-45
    private String beneficiaryEverInUSYesCheckbox;
    private String beneficiaryEverInUSNoCheckbox;
    //397 ---> H1B etc. Vistor
    private String arrivedAsAdmission;

    //check 485 table question 25
    private String admittedAtPortOfEntryCheckbox;
    //temporary worker
    private String admissionEntryDetail;
    private String paroledAtPortOfEntryCheckbox;
    private String paroleEntranceDetail;
    private String enteredWithoutAdmissionCheckbox;
    private String otherEntryMethodCheckbox;
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
    private String arrivalCity;
    private String arrivalState;

    // Page3-Question27: Current immigration status if has changed since you arrived
    private String currentImmigrationStatus;
}
