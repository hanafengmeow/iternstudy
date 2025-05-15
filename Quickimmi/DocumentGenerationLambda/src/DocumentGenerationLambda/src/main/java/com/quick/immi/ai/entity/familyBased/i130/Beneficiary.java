package com.quick.immi.ai.entity.familyBased.i130;
import java.util.List;

import lombok.Data;

@Data
public class Beneficiary {
    //287
    private String alienNumber;
    //288
    private String uSCISOnlineAccountNumber;
    //341
    private String ssn;
    //289
    private String lastName;
    //290
    private String firstName;
    //291
    private String middleName;

    //292 - 294
    private List<BeneficiaryOtherName> beneficiaryOtherNames;
    
    //295
    private String cityOfBirth;
    //296
    private String countryOfBirth;
    //325
    private String dateOfBirth;
    //Part 4-9-M
    private String sexMaleCheckbox;
    //Part 4-9-F
    private String sexFemaleCheckbox;
    //Part 4-10

    private String previousPetitionFiledYesCheckbox;
    private String previousPetitionFiledNoCheckbox;
    private String previousPetitionFiledUnknownCheckbox;
    private Address physicalAddress;
//
//    //317
//    private String streetNumberAndName;
//    //321
//    private String aptCheckbox;
//    private String steCheckbox;
//    private String flrCheckbox;
//    private String aptSteFlrNumber;
//    //322
//    private String cityOrTown;
//    //324
//    private String state;
//    //323
//    private String zipCode;
//    //297
//    private String province;
//    //298
//    private String postalCode;
//    //299
//    private String country;

    //300

    //if different from physical address
    private Address intentToLiveAddress;
//    private String streetNumberAndNameOtherAddress;
//    //304
//    private String aptOtherAddressCheckbox;
//    private String steOtherAddressCheckbox;
//    private String flrOtherAddressCheckbox;
//    private String aptSteFlrNumberOtherAddress;
//    //305
//    private String cityOrTownOtherAddress;
//    //307
//    private String stateOtherAddress;
//    //306
//    private String zipCodeOtherAddress;
    //313
    //Provide the beneficiary's address outside the United States, if
    //    different from Item Numbers 11.a. - 11.h. If the address is the
    //    same, type or print "SAME" in Item Number 13.a.
    private Address outsideUsAddress;
//    private String streetNumberAndNameOutsideUs;
//    //309
//    private String aptOutsideUsCheckbox;
//    private String steOutsideUsCheckbox;
//    private String flrOutsideUsCheckbox;
//    private String aptSteFlrNumberOutsideUs;
//    //314
//    private String cityOrTownOutsideUs;
//    //316
//    private String provinceOutsideUs;
//    //308
//    private String postalCodeOutsideUs;
//    //315
//    private String countryOtherAddress;
    //340
    private String daytimePhoneNumber;
    //350
    private String mobilePhoneNumber;
    //351
    private String emailAddress;
    //343
    private String timesBeneficiaryMarried;
    //Part 4-18
    private String currentMaritalStatusSingleCheckbox;
    private String currentMaritalStatusMarriedCheckbox;  
    private String currentMaritalStatusDivorcedCheckbox;  
    private String currentMaritalStatusWidowedCheckbox;  
    private String currentMaritalStatusSeparatedCheckbox;  
    private String currentMaritalStatusAnnulledCheckbox;  
    //352
    private String currentMarriageDate; 
    //379
    private String currentMarriageCity;
    //380
    private String currentMarriageState;
    //342
    private String currentMarriageProvince;
    //381
    private String currentMarriageCountry;

    private List<BeneficiarySpouses> beneficiarySpouses;
    private List<BeneficiaryFamily> beneficiaryFamilies;

    private LastArrivalInformation lastArrivalInformation;

    //404
    private String nameOfCurrentEmployer;
    //405
    private String streetNumberAndNameOfCurrentEmployer;
    //409
    private String aptCurrentEmployerCheckbox;
    private String steCurrentEmployerCheckbox;
    private String flrCurrentEmployerCheckbox;
    private String aptSteFlrNumberEmployer;
    //410
    private String cityOrTownOfCurrentEmployer;
    //411
    private String stateOfCurrentEmployer;
    //412
    private String zipCodeOfCurrentEmployer;
    //413
    private String provinceOfCurrentEmployer;
    //422
    private String postalCodeOfCurrentEmployer; 
    //423
    private String countryOfCurrentEmployer;
    //414
    private String dateEmploymentBegan;

    //Part 4-53
    private String beneficiaryInImmigrationProceedingsYesCheckbox;
    private String beneficiaryInImmigrationProceedingsNoCheckbox;

    //Part 4-54
    private String removalCheckbox;
    private String exclusionCheckbox;
    private String rescissionCheckbox;
    private String otherJudicialProceedingsCheckbox;

    //421
    private String cityOfProceedings;
    //424
    private String stateOfProceedings;
    //425
    private String dateOfProceedings;

    //427
    private String lastNameUsedNativeLanguage;
    //428
    private String firstNameUsedNativeLanguage;
    //426
    private String middleNameUsedNativeLanguage;
    private Address addressNativeLanguage;
    //442 ===== if filling for spouse
    private Address lastAddressLivedTogether;
    //463
    private String adjustmentOfStatusCity;
    //464
    private String adjustmentOfStatusState;

    //451
    private String immigrantVisaCity;
    //452
    private String immigrantVisaProvince;
    //453
    private String immigrantVisaCountry;
}
