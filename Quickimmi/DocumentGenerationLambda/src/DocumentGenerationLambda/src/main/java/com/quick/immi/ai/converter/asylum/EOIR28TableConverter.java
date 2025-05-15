package com.quick.immi.ai.converter.asylum;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.ApplicationCase;
import com.quick.immi.ai.entity.LawyerBasicInfo;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.asylum.business.Applicant;
import com.quick.immi.ai.entity.asylum.business.AsylumCaseProfile;
import com.quick.immi.ai.entity.eoir28.*;
import crawlercommons.utils.Strings;

public class EOIR28TableConverter {

    public EOIR28TableConverter() {
    }

    public EOIR28Table getEOIR28Table(LawyerProfile lawyerProfile, ApplicationCase applicationCase){
        AsylumCaseProfile asylumCaseProfile = new Gson().fromJson(applicationCase.getProfile(), AsylumCaseProfile.class);
        Applicant applicant = asylumCaseProfile.getApplicant();

        EOIR28Table eoir28Table = new EOIR28Table();

        PartyInformation partyInformation = PartyInformation.builder()
                .firstName(applicant.getFirstName())
                .middleName(applicant.getMiddleName())
                .lastName(applicant.getLastName())
                .address1(applicant.getStreetNumberAndName())
                .address2(applicant.getAptNumber())
                .city(applicant.getCity())
                .state(applicant.getState())
                .zip(applicant.getZipCode())
                .allProceedingsCheckbox("All")
                .alienNumber(applicant.getAlienNumber()).build();

        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();

        Representation representation = Representation.builder()
                .attorneyCheckbox("Attorney")
                .courtName("Supreme Court of California")
                .barNumber(lawyerProfile.getEligibility().getBarNumber())
                .appearanceAttorneyCheckbox("0")
                .eoirIdNumber(lawyerProfile.getBasicInfo().getEoirNumber())
                .build();

        AttorneyInfo attorneyInfo = AttorneyInfo.builder()
                .firstName(basicInfo.getFirstName())
                .middleName(basicInfo.getMiddleName())
                .lastName(basicInfo.getLastName())
                .address1(basicInfo.getAptSteFlrNumber())
                .city(basicInfo.getCity())
                .state(basicInfo.getStateDropdown())
                .zip(basicInfo.getZipCode())
                .telephone(basicInfo.getDaytimeTelephoneNumber())
                .email(basicInfo.getEmailAddress())
                .build();
        TypeOfAppearance typeOfAppearance = new TypeOfAppearance();
        typeOfAppearance.setPrimaryAttorneyCheckbox("Primary");
        typeOfAppearance.setProBonoRepresentationDropdown("No");


        ProofOfService proofOfService = new ProofOfService();
        proofOfService.setAttorneyName(basicInfo.getFirstName() + getMiddleName(basicInfo.getMiddleName()) + attorneyInfo.getLastName());
        proofOfService.setElectronicServiceCheckbox("Yes");

        eoir28Table.setPartyInformation(partyInformation);
        eoir28Table.setRepresentation(representation);
        eoir28Table.setAttorneyInfo(attorneyInfo);
        eoir28Table.setTypeOfAppearance(typeOfAppearance);
        eoir28Table.setProofOfService(proofOfService);

        return eoir28Table;
    }

    private String getMiddleName(String middleName) {
        if(Strings.isBlank(middleName)){
            return " ";
        } else {
            return " " + middleName + " ";
        }
    }
}
