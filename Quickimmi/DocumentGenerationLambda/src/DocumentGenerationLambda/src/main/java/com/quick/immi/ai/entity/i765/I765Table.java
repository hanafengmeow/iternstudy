package com.quick.immi.ai.entity.i765;

import com.quick.immi.ai.entity.LastArrival;
import com.quick.immi.ai.entity.common.NameEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class I765Table {
    private String attorneyUSCISOnlineAccountNumber;
    private String g28AttachedCheckbox;
    private ApplyingReason applyingReason;
    private Applicant applicant;
    private EligibilityCategory eligibilityCategory;
    private ApplicantStatement applicantStatement;
    private ApplicantContact applicantContact;
    private Interpreter interpreter;
    private Preparer preparer;
    private AdditionalInfo additionalInfo;

    @Data
    public static class ApplyingReason {
        private String initialPermissionToAcceptEmploymentCheckbox;
        private String replacementEmploymentDocumentCheckbox;
        private String renewPermissionCheckbox;
    }

    @Data
    @Builder
    public static class Address {
        private String inCareOfName;
        private String streetNumberAndName;
        private String aptCheckbox;
        private String steCheckbox;
        private String flrCheckbox;
        private String aptSteFlrNumber;
        private String city;
        private String state;
        private String zipCode;
        private String province;
        private String postalCode;
        private String country;
    }

    @Data
    public static class Applicant {
        private NameEntity currentName;
        private List<NameEntity> otherUsedNames;
        private Address mailAddress;
        private String sameAsPhysicalAddressYesCheckbox;
        private String sameAsPhysicalAddressNoCheckbox;
        private Address physicalAddress;

        private String alienNumber;
        private String uscisOnlineAccountNumber;
        private String genderMaleCheckbox;
        private String genderFemaleCheckbox;
        private String singleMaritalStatusCheckbox;
        private String marriedMaritalStatusCheckbox;
        private String divorcedMaritalStatusCheckbox;
        private String widowedMaritalStatusCheckbox;

        private String previousFilledFormYesCheckbox;
        private String previousFilledFormNoCheckbox;

        private String issuedSSNYesCheckbox;
        private String issuedSSNNoCheckbox;
        private String ssn;
        private String wantSSNIssueSSNYesCheckbox;
        private String wantSSNIssueSSNNoCheckbox;

        private String consentDisclosureYesCheckbox;
        private String consentDisclosureNoCheckbox;

        private String fatherLastName;
        private String fatherFirstName;


        private String MotherLastName;
        private String MotherFirstName;

        private String firstCountry;
        private String secondCountry;
        private String birthCity;
        private String birthState;
        private String birthCountry;
        private String birthDate;

        private LastArrival lastArrival;
    }

    @Data
    public static class EligibilityCategory {
        private String eligibilityCategoryFirst = "C";
        private String eligibilityCategorySecond = "8";
        private String eligibilityCategoryThird;

        private String degree;
        private String employerNameInEVerify;
        private String employerEVerifyIdNumber;
        private String receiptNumber;

        private String hasBeenArrestedYesFor30Checkbox;
        private String hasBeenArrestedNoFor30Checkbox;
        private String receiptNumberOfSpouseOrParent;

        private String hasBeenArrestedYesFor31bCheckbox;
        private String hasBeenArrestedNoFor31bCheckbox;
    }

    @Data
    public static class ApplicantStatement {
        private String readAndUnderstandEnglishCheckbox;
        private String readByInterpreterCheckbox;
        private String readLanguage;
        private String preparedByOtherCheckbox;
        private String prepareName;
    }

    @Data
    public static class ApplicantContact {
        private Contact contact;
        private String salvadoranOrGuatermalanCheckbox;
    }


    @Data
    public static class Contact {
        private String daytimePhone;
        private String mobilePhone;
        private String email;
    }

    @Data
    public static class Interpreter {
        private String lastName;
        private String firstName;
        //or business name
        private String organizationName;
        private Address address;
        private Contact contact;
        private String fluentLanguage;
    }

    @Data
    public static class Preparer {
        private String lastName;
        private String firstName;
        private String organizationName;
        private Address address;
        private Contact contact;
        private String notAttorneyCheckbox;
        private String attorneyCheckbox;
        private String extendCheckbox;
        private String notExtendCheckbox;
    }

    @Data
    public static class AdditionalInfo {
        private NameEntity applicantName;
        private String alienNumber;

        private String a3PageNumber;
        private String a3PartNumber;
        private String a3ItemNumber;
        private String d3Content;

        private String a4PageNumber;
        private String a4PartNumber;
        private String a4ItemNumber;
        private String d4Content;

        private String a5PageNumber;
        private String a5PartNumber;
        private String a5ItemNumber;
        private String d5Content;

        private String a6PageNumber;
        private String a6PartNumber;
        private String a6ItemNumber;
        private String d6Content;

        private String a7PageNumber;
        private String a7PartNumber;
        private String a7ItemNumber;
        private String d7Content;
    }
}
