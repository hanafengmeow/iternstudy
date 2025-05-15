package com.quick.immi.ai.entity.i485;

import java.util.List;
import lombok.Data;

@Data
public class MaritalHistory {
    // Question 1 - Current Marital Status
//    private String isSingleNeverMarried;
//    private String isMarried;
//    private String isDivorced;
//    private String isWidowed;
//    private String isMarriageAnnulled;
//    private String isLegallySeparated;
//    private String isSpouseMemberOfUSArmedForcesNA;
//    private String isSpouseMemberOfUSArmedForcesYes;
//    private String isSpouseMemberOfUSArmedForcesNo;
    private String maritalStatusSingleCheckbox;
    private String maritalStatusMarriedCheckbox;
    private String maritalStatusDivorcedCheckbox;
    private String maritalStatusWidowedCheckbox;
    private String maritalStatusAnnulledCheckbox;
    private String maritalStatusSeparatedCheckbox;

    // Question 2 - Spouse is a member of U.S. Armed Forces or Coast Guard
    private String spouseMilitaryStatusNACheckbox;
    private String spouseMilitaryStatusYesCheckbox;
    private String spouseMilitaryStatusNoCheckbox;

    // Question 3 - Number of Marriages
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
        private String currentSpouseApplyingNoCheckbox;
        private String currentSpouseApplyingYesCheckbox;
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
        private String isMarriageAnnulled;
        private String isDivorced;
        private String isSpouseDeceased;
        private String isSpouseother;
        private String otherMarriageEndReason;
    }

    // Current Spouse Information
    private String currentSpouseFamilyName;
    private String currentSpouseGivenName;
    private String currentSpouseMiddleName;
    private String currentSpouseANumber;
    private String currentSpouseDateOfBirth;
    private String currentSpouseCountryOfBirth;
    private Address currentSpouseAddress;
    private String currentSpouseCityOfMarriage;
    private String currentSpouseStateOfMarriage;
    private String currentSpouseCountryOfMarriage;
    private String currentSpouseDateOfMarriage;

    // Question 10 - Is current spouse applying with you?
    private String isCurrentSpouseApplyingYes;
    private String isCurrentSpouseApplyingNo;

    // Prior Marriages Information
    private String priorSpouseFamilyName;
    private String priorSpouseGivenName;
    private String priorSpouseMiddleName;
    private String priorSpouseDateOfBirth;
    private String priorSpouseCountryOfBirth;
    private String priorSpouseCountryOfCitizenship;
    private String priorSpouseDateOfMarriage;
    private String priorSpousePlaceOfMarriageCity;
    private String priorSpousePlaceOfMarriageState;
    private String priorSpousePlaceOfMarriageCountry;
    private String priorSpousePlaceMarriageEndedCity;
    private String priorSpousePlaceMarriageEndedState;
    private String priorSpousePlaceMarriageEndedCountry;
    private String priorSpouseDateMarriageEnded;
    private String priorSpouseisMarriageAnnulled;
    private String priorSpouseisDivorced;
    private String priorSpouseisSpouseDeceased;
    private String priorSpouseisSpouseother;
    private String priorSpouseotherMarriageEndReason;
}
