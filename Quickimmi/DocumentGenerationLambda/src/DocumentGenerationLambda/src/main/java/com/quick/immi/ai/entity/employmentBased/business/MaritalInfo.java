package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

import java.util.List;

@Data
public class MaritalInfo {
    //i485-part6-1 Marital Status
    private boolean maritalStatusSingleCheckbox;
    private boolean maritalStatusMarriedCheckbox;
    private boolean maritalStatusDivorcedCheckbox;
    private boolean maritalStatusWidowedCheckbox;
    private boolean maritalStatusAnnulledCheckbox;
    private boolean maritalStatusSeparatedCheckbox;

    //i485-part6-2 - Spouse Military Status
    private boolean spouseMilitaryStatusNACheckbox;
    private boolean spouseMilitaryStatusYesCheckbox;
    private boolean spouseMilitaryStatusNoCheckbox;

    // Page 7, Question 3 - Number of Marriages
    private String numberOfMarriages;
    private CurrentSpouseInfo currentSpouse;
    private List<PreviousSpouseInfo> previousSpouseInfos;

    @Data
    // Page 7, Questions 4a, 4b, 4c - Current Spouse Names
    public static class CurrentSpouseInfo {
        private String lastName;
        private String firstName;
        private String middleName;
        // todo new added
        private Address address;

        // Page 7, Question 5 - Current Spouse A-Number
        private String alienNumber;

        // Page 7, Question 6 - Current Spouse Date of Birth
        private String dateOfBirth;

        // Page 7, Question 7 - Date of Marriage to Current Spouse
        private String dateOfMarriage;

        // Page 7, Questions 8a, 8b, 8c - Current Spouse Place of Birth
        private String cityOfBirth;
        private String stateOfBirth;
        private String countryOfBirth;

        // Page 7, Questions 9a, 9b, 9c - Place of Marriage
        private String placeOfMarriageCity;
        private String placeOfMarriageState;
        private String placeOfMarriageProvince;
        private String placeOfMarriageCountry;

        // Page 7, Question 10 - Is Current Spouse Applying
        private boolean currentSpouseApplyingNoCheckbox;
        private boolean currentSpouseApplyingYesCheckbox;
    }

    @Data
    public static class PreviousSpouseInfo {
        // Page 8, Questions 11a, 11b, 11c - Prior Spouse Name
        private String lastName;
        private String firstName;
        private String middleName;

        // Page 8, Question 12 - Prior Spouse Date of Birth
        private String dateOfBirth;

        // Page 8, Question 13 - Date of Marriage to Prior Spouse
        private String dateOfMarriage;

        // Page 8, Questions 14a, 14b, 14c - Place of Marriage to Prior Spouse
        private String placeOfMarriageCity;
        private String placeOfMarriageState;
        private String placeOfMarriageCountry;

        // Page 8, Question 15 - Date Marriage Legally Ended
        private String dateMarriageLegallyEnded;

        // Page 8, Questions 16a, 16b, 16c - Place Marriage Legally Ended
        private String placeMarriageLegallyEndedCity;
        private String placeMarriageLegallyEndedState;
        private String placeMarriageLegallyEndedCountry;

        // todo new added
        private String countryOfBirth;
        private String countryOfCitizenship;
        private boolean isMarriageAnnulled;
        private boolean isDivorced;
        private boolean isSpouseDeceased;
        private boolean isSpouseother;
        private String otherMarriageEndReason;
    }
}
