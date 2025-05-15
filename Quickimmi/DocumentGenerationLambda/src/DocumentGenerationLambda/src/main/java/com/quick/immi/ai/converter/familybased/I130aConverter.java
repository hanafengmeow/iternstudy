
package com.quick.immi.ai.converter.familybased;


import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.LawyerBasicInfo;
import com.quick.immi.ai.entity.LawyerEligibility;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.Address;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.i130a.*;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.InstanceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class I130aConverter {
    private static final Logger log = Logger.getLogger(I131Converter.class.getName());

    private Properties checkboxProperties;
    private List<Supplement.AdditionalRecord> additionalRecords;
    public I130aConverter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I130aCheckbox);
        additionalRecords = new ArrayList<>();
    }

    public I130aTable getI130aTable(LawyerProfile lawyerProfile, FamilyBasedCaseProfile familyBasedCaseProfile){
        try {
            I130aTable i130aTable = new I130aTable();
            i130aTable.setAttorney(convertAttorney(lawyerProfile));
            i130aTable.setBeneficiary(convertBeneficiary(familyBasedCaseProfile));
            i130aTable.setPetitionerStatement(convertPetitionerStatement(familyBasedCaseProfile));
            i130aTable.setInterpreter(convertInterpreter(familyBasedCaseProfile));
            i130aTable.setPreparer(convertPreparer(lawyerProfile));
            i130aTable.setSupplements(convertSupplements(familyBasedCaseProfile));
            return i130aTable;
        } catch (Exception exp){
            log.log(Level.SEVERE, "an exception was thrown whe I130aTable", exp);
            throw new RuntimeException(exp);
        }
    }

    private Attorney convertAttorney(LawyerProfile lawyerProfile) {
        Attorney attorney = new Attorney();
        LawyerEligibility eligibility = lawyerProfile.getEligibility();
        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        if(eligibility == null){
            eligibility = InstanceUtils.createInstanceWithDefaults(LawyerEligibility.class);
        }
        if(basicInfo == null){
            basicInfo = InstanceUtils.createInstanceWithDefaults(LawyerBasicInfo.class);
        }
        attorney.setStateBarNumber(eligibility.getBarNumber());
        attorney.setUSCISOnlineAccountNumber(basicInfo.getUscisOnlineAccountNumber());
        Attorney targetAttorney = new Attorney();

        CopyUtils.copy(attorney, targetAttorney, checkboxProperties, "");
        targetAttorney.setG28AttachedCheckbox("1");
        return targetAttorney;
    }

    private Beneficiary convertBeneficiary(FamilyBasedCaseProfile familyBasedCaseProfile) {
        Beneficiary targetBeneficiary = new Beneficiary();
        com.quick.immi.ai.entity.familyBased.businss.Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
        if(beneficiary == null){
            beneficiary = InstanceUtils.createInstanceWithDefaults(com.quick.immi.ai.entity.familyBased.businss.Beneficiary.class);
        }
        CopyUtils.copy(beneficiary, targetBeneficiary, checkboxProperties, "beneficiary");
        if(targetBeneficiary.getAlienNumber().equals("N/A")){
            targetBeneficiary.setAlienNumber("");
        }
        if(targetBeneficiary.getUSCISOnlineAccountNumber().equals("N/A")){
            targetBeneficiary.setUSCISOnlineAccountNumber("");
        }
        Address physicalAddress = beneficiary.getPhysicalAddress();
        if (physicalAddress == null) {
            physicalAddress = InstanceUtils.createInstanceWithDefaults(Address.class);
        }
        com.quick.immi.ai.entity.familyBased.i130a.Address targetAddress = new com.quick.immi.ai.entity.familyBased.i130a.Address();
        CopyUtils.copy(physicalAddress, targetAddress, checkboxProperties, "beneficiary.physicalAddress");

        List<com.quick.immi.ai.entity.familyBased.i130a.Address> addressHistoryList = new ArrayList<>();
        if(beneficiary.getAddressHistories() != null){
            for(int i = 0; i < 2 && i < beneficiary.getAddressHistories().size(); i++){
                Address addressHistory = beneficiary.getAddressHistories().get(i);
                com.quick.immi.ai.entity.familyBased.i130a.Address targetAddressHistory = new com.quick.immi.ai.entity.familyBased.i130a.Address();
                CopyUtils.copy(addressHistory, targetAddressHistory, checkboxProperties, String.format("beneficiary.addressHistory.%s", i + 1));
                addressHistoryList.add(targetAddressHistory);
            }
        }
        targetBeneficiary.setAddressHistories(addressHistoryList);

        // Convert Mother && Father
        com.quick.immi.ai.entity.familyBased.businss.Parent mother = beneficiary.getFamily()
            .getMother();
        log.info("Debug i130a: parent1: " + mother);

        if (mother == null) mother = InstanceUtils.createInstanceWithDefaults(com.quick.immi.ai.entity.familyBased.businss.Parent.class);
        Parent targetParent1 = new Parent();
        CopyUtils.copy(mother, targetParent1, checkboxProperties, "beneficiary.parent1");
        log.info("Debug i130a: targetParent1: " + targetParent1);

        targetBeneficiary.setParent1(targetParent1);

        com.quick.immi.ai.entity.familyBased.businss.Parent father = beneficiary.getFamily()
            .getFather();
        if (father == null) father = InstanceUtils.createInstanceWithDefaults(com.quick.immi.ai.entity.familyBased.businss.Parent.class);
        Parent targetParent2 = new Parent();
        CopyUtils.copy(father, targetParent2, checkboxProperties, "beneficiary.parent2");
        targetBeneficiary.setParent2(targetParent2);


        // TO DO: Add remaining address histories to the supplement

        // TO DO: Add remaining employment histories to the supplement
        boolean hasOutSideUsEmployment = false;
        int emplyIndex = 0;
        List<com.quick.immi.ai.entity.familyBased.i130a.EmploymentHistory> employmentHistories = new ArrayList<>();

        if(beneficiary.getCurrentEmployment() != null){
            com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory employmentHistory = beneficiary.getCurrentEmployment();
            if(!employmentHistory.isInUSA()){
                hasOutSideUsEmployment = true;
            }

            com.quick.immi.ai.entity.familyBased.i130a.EmploymentHistory targetEmploymentHistory
                    = new com.quick.immi.ai.entity.familyBased.i130a.EmploymentHistory();
            CopyUtils.copy(employmentHistory, targetEmploymentHistory, checkboxProperties,
                    String.format("beneficiary.currentEmployment"));
            employmentHistories.add(targetEmploymentHistory);
        }

        if(beneficiary.getEmploymentHistories() != null){
            int i = 0;
            if(employmentHistories.size() == 1){
                i = 1;
                emplyIndex = 1;
                com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory employmentHistory = beneficiary.getEmploymentHistories().get(0);
                if(!employmentHistory.isInUSA()){
                    hasOutSideUsEmployment = true;
                }
                com.quick.immi.ai.entity.familyBased.i130a.EmploymentHistory targetEmploymentHistory
                        = new com.quick.immi.ai.entity.familyBased.i130a.EmploymentHistory();
                CopyUtils.copy(employmentHistory, targetEmploymentHistory, checkboxProperties,
                        String.format("beneficiary.employmentHistories.%s", 1));
                employmentHistories.add(targetEmploymentHistory);
            }else{
                emplyIndex = 2;
                for(; i < 2 && i < beneficiary.getEmploymentHistories().size(); i++){
                    com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory employmentHistory = beneficiary.getEmploymentHistories().get(i);
                    if(!employmentHistory.isInUSA()){
                        hasOutSideUsEmployment = true;
                    }
                    com.quick.immi.ai.entity.familyBased.i130a.EmploymentHistory targetEmploymentHistory
                            = new com.quick.immi.ai.entity.familyBased.i130a.EmploymentHistory();
                    CopyUtils.copy(employmentHistory, targetEmploymentHistory, checkboxProperties,
                            String.format("beneficiary.employmentHistories.%s", i + 1));
                    employmentHistories.add(targetEmploymentHistory);
                }
            }
        }

        targetBeneficiary.setEmploymentHistories(employmentHistories);

        if(!hasOutSideUsEmployment){
            com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory mostRecentOutSideUsEmploymentHistory5YearsAgo =
                    beneficiary.getMostRecentOutSideUsEmploymentHistory5YearsAgo();
            for(; emplyIndex < beneficiary.getEmploymentHistories().size(); emplyIndex++){
                com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory employmentHistory = beneficiary.getEmploymentHistories().get(emplyIndex);
                if(!employmentHistory.isInUSA()){
                    mostRecentOutSideUsEmploymentHistory5YearsAgo = employmentHistory;
                    hasOutSideUsEmployment = true;
                    break;
                }
            }

            EmploymentHistory employmentHistoryForMostRecentOutSideUsEmploymentHistory5YearsAgo = new EmploymentHistory();
            if(mostRecentOutSideUsEmploymentHistory5YearsAgo == null){
                mostRecentOutSideUsEmploymentHistory5YearsAgo =
                        InstanceUtils.createInstanceWithDefaults(com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory.class);
            }
            CopyUtils.copy(mostRecentOutSideUsEmploymentHistory5YearsAgo, employmentHistoryForMostRecentOutSideUsEmploymentHistory5YearsAgo,
                    checkboxProperties,
                    "beneficiary.mostRecentOutSideUsEmploymentHistory5YearsAgo");
            targetBeneficiary.setMostRecentOutSideUsEmploymentHistory5YearsAgo(employmentHistoryForMostRecentOutSideUsEmploymentHistory5YearsAgo);

        }
        if (!hasOutSideUsEmployment) {
            // I-130a, Page3, Part3, 1-4.b
            // If the beneficiary never worked outside the US, provide this information in Part 7
            Supplement.AdditionalRecord additionalRecord = new Supplement.AdditionalRecord();
            additionalRecord.setAdditionalInfo("I never worked outside the United States");
            additionalRecord.setItemNumber("1-4.b");
            additionalRecord.setPartNumber("3");
            additionalRecord.setPageNumber("3");
            this.additionalRecords.add(additionalRecord);
        }


        Address outsideUsAddress = beneficiary.getOutsideUsAddress();
        if (outsideUsAddress == null) {
            outsideUsAddress = InstanceUtils.createInstanceWithDefaults(Address.class);
        }

        com.quick.immi.ai.entity.familyBased.i130a.Address targetOutsideUsAddress = new com.quick.immi.ai.entity.familyBased.i130a.Address();
        CopyUtils.copy(outsideUsAddress, targetOutsideUsAddress, checkboxProperties, "beneficiary.outsideUsAddress");
        targetBeneficiary.setOutsideUsAddress(targetOutsideUsAddress);

        return targetBeneficiary;
    }

    private PetitionerStatement convertPetitionerStatement(FamilyBasedCaseProfile familyBasedCaseProfile) {
        PetitionerStatement targetPetitionerStatement = new PetitionerStatement();
        com.quick.immi.ai.entity.familyBased.businss.PetitionerStatement petitionerStatement = familyBasedCaseProfile.getPetitionerStatement();
        if(petitionerStatement == null){
            petitionerStatement = InstanceUtils.createInstanceWithDefaults(com.quick.immi.ai.entity.familyBased.businss.PetitionerStatement.class);
        }
        CopyUtils.copy(petitionerStatement, targetPetitionerStatement, checkboxProperties, "petitionerStatement");
        return targetPetitionerStatement;
    }

    private Interpreter convertInterpreter(FamilyBasedCaseProfile familyBasedCaseProfile) {
        Interpreter targetInterpreter = new Interpreter();
        com.quick.immi.ai.entity.familyBased.businss.Interpreter interpreter = familyBasedCaseProfile.getInterpreter();
        if(interpreter == null){
            interpreter =  InstanceUtils.createInstanceWithDefaults(com.quick.immi.ai.entity.familyBased.businss.Interpreter.class);
        }
        CopyUtils.copy(interpreter, targetInterpreter, checkboxProperties, "interpreter");
        return targetInterpreter;
    }

    private Preparer convertPreparer(LawyerProfile lawyerProfile) {
        Preparer preparer = new Preparer();
        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        LawyerEligibility eligibility = lawyerProfile.getEligibility();
        if (basicInfo == null) {
            basicInfo = InstanceUtils.createInstanceWithDefaults(LawyerBasicInfo.class);
        }
        if (eligibility == null) {
            eligibility = InstanceUtils.createInstanceWithDefaults(LawyerEligibility.class);
        }
        preparer.setLastName(basicInfo.getLastName());
        preparer.setFirstName(basicInfo.getFirstName());
        preparer.setBusinessName(eligibility.getNameofLawFirm());

        preparer.setStreetNumberAndName(basicInfo.getStreetNumberAndName());
        if(basicInfo.isAptCheckbox()){
            preparer.setAptCheckbox("APT");
        }

        if(basicInfo.isSteCheckbox()){
            preparer.setAptCheckbox("STE");
        }

        if(basicInfo.isFlrCheckbox()){
            preparer.setAptCheckbox("FLR");
        }
        preparer.setAptSteFlrNumber(basicInfo.getAptSteFlrNumber());
        preparer.setCity(basicInfo.getCity());
        preparer.setState(basicInfo.getStateDropdown());
        preparer.setZipCode(basicInfo.getZipCode());
        preparer.setPostalCode(basicInfo.getPostalCode());
        preparer.setCountry(basicInfo.getCountry());
        preparer.setDaytimeTelephoneNumber(basicInfo.getDaytimeTelephoneNumber());
        preparer.setMobileTelephoneNumber(basicInfo.getMobileTelephoneNumber());
        preparer.setEmailAddress(basicInfo.getEmailAddress());
        return preparer;
    }
    private List<Supplement> convertSupplements(FamilyBasedCaseProfile familyBasedCaseProfile) {
        List<Supplement> supplements = new ArrayList<>();
        if (additionalRecords.isEmpty() || familyBasedCaseProfile.getBeneficiary() == null) {
            return supplements;
        }
        Supplement supplement = null;
        int recordCount = 0;
        for (Supplement.AdditionalRecord record : additionalRecords) {
            if (supplement == null || recordCount == 5) {
                supplement = new Supplement();
                supplement.setFirstName(familyBasedCaseProfile.getBeneficiary().getFirstName());
                supplement.setLastName(familyBasedCaseProfile.getBeneficiary().getLastName());
                supplement.setMiddleName(familyBasedCaseProfile.getBeneficiary().getMiddleName());
                supplement.setAlienNumber(familyBasedCaseProfile.getBeneficiary().getAlienNumber());
                supplement.setAdditionalRecords(new ArrayList<>()); // Initialize the list
                supplements.add(supplement);
                recordCount = 0;
            }
            supplement.getAdditionalRecords().add(record);
            recordCount++;
        }
        return supplements;
    }
}
