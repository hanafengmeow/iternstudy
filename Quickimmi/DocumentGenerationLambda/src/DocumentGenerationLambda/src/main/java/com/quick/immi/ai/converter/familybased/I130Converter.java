package com.quick.immi.ai.converter.familybased;

import com.amazonaws.util.StringUtils;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.LawyerBasicInfo;
import com.quick.immi.ai.entity.LawyerEligibility;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.Address;
import com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory;
import com.quick.immi.ai.entity.familyBased.businss.*;
import com.quick.immi.ai.entity.familyBased.i130.Attorney;
import com.quick.immi.ai.entity.familyBased.i130.Beneficiary;
import com.quick.immi.ai.entity.familyBased.i130.BeneficiaryOtherName;
import com.quick.immi.ai.entity.familyBased.i130.Biographic;
import com.quick.immi.ai.entity.familyBased.i130.Interpreter;
import com.quick.immi.ai.entity.familyBased.i130.LastArrivalInformation;
import com.quick.immi.ai.entity.familyBased.i130.Petitioner;
import com.quick.immi.ai.entity.familyBased.i130.PetitionerOtherName;
import com.quick.immi.ai.entity.familyBased.i130.PetitionerStatement;
import com.quick.immi.ai.entity.familyBased.i130.Preparer;
import com.quick.immi.ai.entity.familyBased.i130.PreviousPetition;
import com.quick.immi.ai.entity.familyBased.i130.Relationship;
import com.quick.immi.ai.entity.familyBased.i130.*;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.InstanceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class I130Converter {
    private static final String BLANK = "N/A";
    private static final String NO_OPTION = "";
    private static final Logger logger = Logger.getLogger(I130Converter.class.getName());

    private Properties checkboxProperties;
    private List<Supplement.AdditionalRecord> additionalRecords;

    public I130Converter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I130Checkbox);
        this.additionalRecords = new ArrayList<>();
    }

    public I130Table getI130Table(LawyerProfile lawyerProfile, FamilyBasedCaseProfile familyBasedCaseProfile) {
        I130Table i130Table = new I130Table();
        i130Table.setAttorney(convertAttorney(lawyerProfile));
        i130Table.setRelationship(convertRelationship(familyBasedCaseProfile));
        i130Table.setBeneficiary(convertBeneficiary(familyBasedCaseProfile));
        i130Table.setBiographic(convertBiographic(familyBasedCaseProfile));
        i130Table.setPetitioner(convertPetitioner(familyBasedCaseProfile));
        i130Table.setPreviousPetition(convertPreviousPetition(familyBasedCaseProfile));
        i130Table.setInterpreter(convertInterpreter(familyBasedCaseProfile.getInterpreter()));
        i130Table.setPetitionerStatement(convertPetitionerStatement(familyBasedCaseProfile.getPetitionerStatement()));
        i130Table.setPreparer(convertPrepare(lawyerProfile));
        i130Table.setSupplements(convertSupplements(familyBasedCaseProfile));
        return i130Table;
    }

    private List<Supplement> convertSupplements(FamilyBasedCaseProfile familyBasedCaseProfile) {
        List<Supplement> supplements = new ArrayList<>();
        if (additionalRecords.isEmpty() || familyBasedCaseProfile.getPetitioner() == null) {
            return supplements;
        }
        Supplement supplement = null;
        int recordCount = 0;

        for (Supplement.AdditionalRecord record : additionalRecords) {
            if (supplement == null || recordCount == 5) {
                supplement = new Supplement();
                supplement.setFirstName(familyBasedCaseProfile.getPetitioner().getFirstName());
                supplement.setLastName(familyBasedCaseProfile.getPetitioner().getLastName());
                supplement.setMiddleName(familyBasedCaseProfile.getPetitioner().getMiddleName());
                supplement.setAlienNumber(familyBasedCaseProfile.getPetitioner().getAlienNumber());
                supplement.setAdditionalRecords(new ArrayList<>()); // Initialize the list
                supplements.add(supplement);
                recordCount = 0;
            }
            supplement.getAdditionalRecords().add(record);
            recordCount++;
        }

        return supplements;
    }

    private Petitioner convertPetitioner(FamilyBasedCaseProfile familyBasedCaseProfile) {
        Petitioner targetPetitioner = new Petitioner();

        com.quick.immi.ai.entity.familyBased.businss.Petitioner petitioner = familyBasedCaseProfile.getPetitioner();
        if (petitioner == null) {
            petitioner = new com.quick.immi.ai.entity.familyBased.businss.Petitioner();
        }
        CopyUtils.copy(petitioner, targetPetitioner, checkboxProperties, "petitioner");

        List<PetitionerOtherName> petitionerOtherNames = new ArrayList<>();
        if (petitioner.getPetitionerOtherNames() != null
                && !petitioner.getPetitionerOtherNames().isEmpty()) {
            com.quick.immi.ai.entity.familyBased.businss.PetitionerOtherName petitionerOtherName = familyBasedCaseProfile.getPetitioner().getPetitionerOtherNames().get(0);
            PetitionerOtherName targetPetitionerOtherName = new PetitionerOtherName();
            CopyUtils.copy(petitionerOtherName, targetPetitionerOtherName, checkboxProperties, "");
            petitionerOtherNames.add(targetPetitionerOtherName);
        } else {
            petitionerOtherNames.add(InstanceUtils.createInstanceWithDefaults(PetitionerOtherName.class));
        }
        targetPetitioner.setPetitionerOtherNames(petitionerOtherNames);
        if (petitioner.getPetitionerOtherNames() != null
                && petitioner.getPetitionerOtherNames().size() > 1) {
            StringBuilder sb = new StringBuilder();
            for(int i = 1; i < petitioner.getPetitionerOtherNames().size(); i++){
                com.quick.immi.ai.entity.familyBased.businss.PetitionerOtherName petitionerOtherName = petitioner.getPetitionerOtherNames().get(i);
                sb.append("FirstName:").append(petitionerOtherName.getFirstName()).append(" ");
                if(petitionerOtherName.getMiddleName() != null){
                    sb.append("MiddleName:").append(petitionerOtherName.getMiddleName())
                        .append(" ");
                }
                sb.append("LastName:").append(petitionerOtherName.getLastName());
                sb.append("\n");
            }
            Supplement.AdditionalRecord additionalRecord = new Supplement.AdditionalRecord();
            additionalRecord.setAdditionalInfo(sb.toString());
            additionalRecord.setItemNumber("5");
            additionalRecord.setPartNumber("2");
            additionalRecord.setPageNumber("2");

            this.additionalRecords.add(additionalRecord);
        }

        List<com.quick.immi.ai.entity.familyBased.i130.Address> addressHistoryList = new ArrayList<>();

        // set mailing address
        // TODO: fail to set Apt. Ste. Flr checkbox
        setPetitionerCurrentAddress(targetPetitioner, petitioner.getMailingAddress());

        // add the current physical address to the address list first
        Address physicalAddress = petitioner.getPhysicalAddress();
        if (physicalAddress != null) {
            physicalAddress.setDateTo("PRESENT");
            // if same with the mailing address, fill forms with "N/A"
            if (petitioner.isMailingAddressSameAsPhysicalAddressYesCheckbox()) {
                Address defaultAddress = InstanceUtils.createInstanceWithDefaults(Address.class);
                defaultAddress.setDateFrom(physicalAddress.getDateFrom());
                defaultAddress.setDateTo("PRESENT");
                physicalAddress = defaultAddress;
            }
            com.quick.immi.ai.entity.familyBased.i130.Address targetAddressHistory = new com.quick.immi.ai.entity.familyBased.i130.Address();
            CopyUtils.copy(physicalAddress, targetAddressHistory, checkboxProperties, String.format("petitioner.addressHistory.%s", 1));
            addressHistoryList.add(targetAddressHistory);
        }
        if (petitioner.getAddressHistory() != null && !petitioner.getAddressHistory().isEmpty()) {
            Address addressHistory = petitioner.getAddressHistory().get(0);
            com.quick.immi.ai.entity.familyBased.i130.Address targetAddressHistory = new com.quick.immi.ai.entity.familyBased.i130.Address();
            CopyUtils.copy(addressHistory, targetAddressHistory, checkboxProperties, String.format("petitioner.addressHistory.%s", 2));
            addressHistoryList.add(targetAddressHistory);

            int i = 1;
            if (i < petitioner.getAddressHistory().size()) {
                StringBuilder sb = new StringBuilder();
                for(; i < petitioner.getAddressHistory().size(); i++){
                    Address additionalAddress = petitioner.getAddressHistory().get(i);
                    sb.append("StreetNumber:").append(additionalAddress.getStreetNumberAndName())
                        .append(" ");
                    sb.append(getAptSteFlr(additionalAddress)).append(":")
                        .append(additionalAddress.getAptSteFlrNumber()).append(" \n");
                    sb.append("CityOrTown:").append(additionalAddress.getCityOrTown()).append(" ");
                    if (!StringUtils.isNullOrEmpty(additionalAddress.getState())) {
                        sb.append("State: ").append(additionalAddress.getState()).append("\n");
                    }
                    if (!StringUtils.isNullOrEmpty(additionalAddress.getZipCode())) {
                        sb.append("ZipCode: ").append(additionalAddress.getZipCode()).append("\n");
                    }
                    if (!StringUtils.isNullOrEmpty(additionalAddress.getProvince())) {
                        sb.append("Province: ").append(additionalAddress.getProvince())
                            .append("\n");
                    }
                    if (!StringUtils.isNullOrEmpty(additionalAddress.getPostalCode())) {
                        sb.append("PostCode: ").append(additionalAddress.getPostalCode())
                            .append("\n");
                    }
                    if (!StringUtils.isNullOrEmpty(additionalAddress.getCountry())) {
                        sb.append("Country: ").append(additionalAddress.getCountry()).append("\n");
                    }
                    sb.append("Data From: ").append(additionalAddress.getDateFrom())
                        .append(" Date to: ").append(additionalAddress.getDateTo()).append("\n");
                }
                Supplement.AdditionalRecord additionalRecord = new Supplement.AdditionalRecord();
                additionalRecord.setAdditionalInfo(sb.toString());
                additionalRecord.setItemNumber("12.a-15.b");
                additionalRecord.setPartNumber("2");
                additionalRecord.setPageNumber("2");
            }
        }
        targetPetitioner.setAddressHistory(addressHistoryList);

        List<com.quick.immi.ai.entity.familyBased.i130.Spouses> spousesList = new ArrayList<>();

        if (petitioner.getSpouses() != null) {
            for (int i = 0; i < 2 && i < petitioner.getSpouses().size(); i++) {
                com.quick.immi.ai.entity.familyBased.businss.Spouses spouses = petitioner.getSpouses().get(i);
                com.quick.immi.ai.entity.familyBased.i130.Spouses targetSpouses = new com.quick.immi.ai.entity.familyBased.i130.Spouses();
                CopyUtils.copy(spouses, targetSpouses, checkboxProperties, String.format("petitioner.spouses.%s", i + 1));
                spousesList.add(targetSpouses);
            }
        }
        targetPetitioner.setSpouses(spousesList);
        targetPetitioner.setParent1(convertParents(petitioner.getParent1()));
        targetPetitioner.setParent2(convertParents(petitioner.getParent2()));

        List<com.quick.immi.ai.entity.familyBased.i130.EmploymentHistory> employmentHistories = new ArrayList<>();

        if (petitioner.getEmploymentHistory() != null) {
            for (int i = 0; i < 2 && i < petitioner.getEmploymentHistory().size(); i++) {
                com.quick.immi.ai.entity.familyBased.businss.EmploymentHistory employmentHistory = petitioner.getEmploymentHistory().get(i);
                com.quick.immi.ai.entity.familyBased.i130.EmploymentHistory targetEmploymentHistory
                        = new com.quick.immi.ai.entity.familyBased.i130.EmploymentHistory();
                CopyUtils.copy(employmentHistory, targetEmploymentHistory, checkboxProperties,
                        String.format("petitioner.employmentHistory.%s", i + 1));
                employmentHistories.add(targetEmploymentHistory);
            }
        }
        targetPetitioner.setEmploymentHistory(employmentHistories);

        return targetPetitioner;
    }

    private Parents convertParents(Parent parent){
        if(parent == null){
            return InstanceUtils.createInstanceWithDefaults(Parents.class);
        }

        Parents parents = new Parents();

        parents.setFirstName(parent.getFirstName());
        parents.setMiddleName(parent.getMiddleName());
        parents.setLastName(parent.getLastName());

        parents.setDateOfBirth(parent.getDateOfBirth());
        if(parent.isSexOfMaleCheckbox()){
            parents.setSexOfMaleCheckbox("Y");
        } else if(parent.isSexOfFemaleCheckbox()){
            parents.setSexOfFemaleCheckbox("Y");
        }
        parents.setCountryOfBirth(parent.getCountryOfBirth());
        parents.setCityOfResidence(parent.getCurrentCityOfResidence());
        parents.setCountryOfResidence(parent.getCurrentCountryOfResidence());
        return parents;
    }

    private String getAptSteFlr(Address address){
        if(address.isSteCheckbox()){
            return "STE";
        }
        if(address.isAptCheckbox()){
            return "APT";
        }
        if(address.isFlrCheckbox()){
            return "FLR";
        }
        return "";
    }

    private void setPetitionerCurrentAddress(Petitioner petitioner, Address address){
        if(address == null){
            address = InstanceUtils.createInstanceWithDefaults(Address.class);
        }
        //set the current physical address
        petitioner.setInCareOf(address.getInCareOf());
        petitioner.setStreetNumberAndName(address.getStreetNumberAndName());
        if(address.isAptCheckbox()){
            petitioner.setAptCheckbox("APT");
            petitioner.setSteCheckbox("");
            petitioner.setFlrCheckbox("");
        }
        else if(address.isFlrCheckbox()){
            petitioner.setAptCheckbox("");
            petitioner.setSteCheckbox("");
            petitioner.setFlrCheckbox("FLR");
        } else if(address.isSteCheckbox()){
            petitioner.setSteCheckbox("STE");
            petitioner.setAptCheckbox("");
            petitioner.setFlrCheckbox("");
        }
        petitioner.setAptSteFlrNumber(address.getAptSteFlrNumber());
        petitioner.setCityOrTown(address.getCityOrTown());
        petitioner.setState(StringUtils.isNullOrEmpty(address.getState())?"N/A": address.getState());
        petitioner.setZipCode(StringUtils.isNullOrEmpty(address.getZipCode()) ? "N/A": address.getZipCode());
        petitioner.setProvince(StringUtils.isNullOrEmpty(address.getProvince()) ? "N/A": address.getProvince());
        petitioner.setPostalCode(StringUtils.isNullOrEmpty(address.getPostalCode()) ? "N/A": address.getPostalCode());
        petitioner.setCountry(StringUtils.isNullOrEmpty(address.getCountry()) ? "N/A": address.getCountry());
    }

    private Relationship convertRelationship(FamilyBasedCaseProfile familyBasedCaseProfile) {
        Relationship targetRelationship = new Relationship();
        com.quick.immi.ai.entity.familyBased.businss.Relationship relationship = familyBasedCaseProfile.getRelationship();

        if (relationship == null) {
            relationship = new com.quick.immi.ai.entity.familyBased.businss.Relationship();
        }
        CopyUtils.copy(relationship, targetRelationship, checkboxProperties, "relationship");

        return targetRelationship;
    }

    private Beneficiary convertBeneficiary(FamilyBasedCaseProfile familyBasedCaseProfile) {
        Beneficiary targetBeneficiary = new Beneficiary();

        com.quick.immi.ai.entity.familyBased.businss.Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
        if (beneficiary == null) {
            beneficiary = new com.quick.immi.ai.entity.familyBased.businss.Beneficiary();
        }
        CopyUtils.copy(beneficiary, targetBeneficiary, checkboxProperties, "beneficiary");
        logger.info("convert beneficiary successfully");
        targetBeneficiary.setBeneficiaryOtherNames(convertBeneficiaryOtherName(beneficiary.getBeneficiaryOtherNames()));
        logger.info("convertBeneficiaryOtherName successfully");
        targetBeneficiary.setPhysicalAddress(convertAddress(beneficiary.getPhysicalAddress(), "beneficiary.physicalAddress"));
        logger.info("convert physical address successfully");
        if(beneficiary.isIntendToLiveUsAddressSameAsPhysicalAddressYesCheckbox()){
            com.quick.immi.ai.entity.familyBased.i130.Address intendAddress = new com.quick.immi.ai.entity.familyBased.i130.Address();
            intendAddress.setStreetNumberAndName("SAME");
            intendAddress.setAptSteFlrNumber("N/A");
            intendAddress.setCityOrTown("N/A");
            intendAddress.setState("N/A");
            intendAddress.setZipCode("N/A");
            targetBeneficiary.setIntentToLiveAddress(intendAddress);
        }else{
            targetBeneficiary.setIntentToLiveAddress(convertAddress(beneficiary.getIntendToLiveUsAddress(), "beneficiary.intendToLiveUsAddress"));
            logger.info("convert intendToLiveUsAddress successfully");
        }

        if(beneficiary.isOutsideUsAddressSameAsPhysicalAddressYesCheckbox()){
            com.quick.immi.ai.entity.familyBased.i130.Address outsideusAddress = new com.quick.immi.ai.entity.familyBased.i130.Address();
            outsideusAddress.setStreetNumberAndName("SAME");
            outsideusAddress.setAptSteFlrNumber("N/A");
            outsideusAddress.setCityOrTown("N/A");
            outsideusAddress.setProvince("N/A");
            outsideusAddress.setPostalCode("N/A");
            outsideusAddress.setCountry("N/A");
            targetBeneficiary.setOutsideUsAddress(outsideusAddress);
        }else{
            targetBeneficiary.setOutsideUsAddress(convertAddress(beneficiary.getOutsideUsAddress(), "beneficiary.outsideUsAddress"));
            logger.info("convert outsideUsAddress successfully");
        }

        targetBeneficiary.setAddressNativeLanguage(convertAddress(beneficiary.getAddressNativeLanguage(), "beneficiary.addressNativeLanguage"));
        logger.info("convert addressNativeLanguage successfully");
        Address lastAddressLivedTogether;
        if(beneficiary.isNeverLiveTogetherCheckbox()){
            lastAddressLivedTogether = new Address();
            lastAddressLivedTogether.setStreetNumberAndName("Never lived together");
        } else {
            lastAddressLivedTogether = beneficiary.getLastAddressLivedTogether();
        }
        targetBeneficiary.setLastAddressLivedTogether(convertAddress(lastAddressLivedTogether, "beneficiary.lastAddressLivedTogether"));
        logger.info("convert lastAddressLivedTogether successfully");

        MaritalInfo maritalInfo = getMaritalInfo(beneficiary);
        if (maritalInfo.getCurrentSpouse() != null) {
            setMaritalStatus(targetBeneficiary, maritalInfo);
            logger.info("setMaritalStatus successfully");
            setCurrentMaritalInfo(targetBeneficiary, maritalInfo);
            logger.info("setCurrentMaritalInfo successfully");
            if (!maritalInfo.isMaritalStatusSingleCheckbox()) {
                List<BeneficiarySpouses> beneficiarySpouses = new ArrayList<>();
                if(maritalInfo.isMaritalStatusMarriedCheckbox()){
                    BeneficiarySpouses ele = convertBeneficiarySpousesFromCurrentSpouse(maritalInfo.getCurrentSpouse());
                    beneficiarySpouses.add(ele);
                }

                List<MaritalInfo.PreviousSpouseInfo> previousSpouseInfos = maritalInfo.getPreviousSpouseInfos();
                if (previousSpouseInfos != null) {
                    for (int i = 0; i < previousSpouseInfos.size() && i < 1; i++) {
                        MaritalInfo.PreviousSpouseInfo previousSpouseInfo = previousSpouseInfos.get(i);
                        BeneficiarySpouses ele = convertBeneficiarySpouses(previousSpouseInfo);
                        beneficiarySpouses.add(ele);
                    }
                    Supplement.AdditionalRecord additionalRecord = convertAdditionalRecord(previousSpouseInfos, maritalInfo.isMaritalStatusMarriedCheckbox());

                    additionalRecords.add(additionalRecord);

                    targetBeneficiary.setBeneficiarySpouses(beneficiarySpouses);
                }
            }
            logger.info("set previousSpouseInfos successfully");
        }
        logger.info("convert BeneficiarySpouses successfully");

        List<BeneficiaryFamily> beneficiaryFamilies = new ArrayList<>();
        Parent father = getFamily(beneficiary).getFather();
        addParents(beneficiaryFamilies, father);

        Parent mother = getFamily(beneficiary).getMother();
        addParents(beneficiaryFamilies, mother);

        if (maritalInfo.getCurrentSpouse() != null) {
            MaritalInfo.CurrentSpouseInfo currentSpouse = maritalInfo.getCurrentSpouse();
            BeneficiaryFamily beneficiaryFamily = new BeneficiaryFamily();
            beneficiaryFamily.setFirstName(currentSpouse.getFirstName());
            beneficiaryFamily.setLastName(currentSpouse.getLastName());
            beneficiaryFamily.setMiddleName(currentSpouse.getMiddleName());
            beneficiaryFamily.setRelationship("Spouse");
            beneficiaryFamily.setDateOfBirth(currentSpouse.getDateOfBirth());
            beneficiaryFamily.setCountryOfBirth(currentSpouse.getCountryOfBirth());
            beneficiaryFamilies.add(beneficiaryFamily);
        }
        logger.info("convert beneficiaryFamilies--currentSpouse successfully");

        Family family = getFamily(beneficiary);
        if (family.getChildren() != null) {
            List<Child> children = family.getChildren();
            for (int i = 0; i < children.size() && i < 5; i++) {
                Child child = children.get(i);
                BeneficiaryFamily beneficiaryFamily = new BeneficiaryFamily();
                beneficiaryFamily.setFirstName(child.getFirstName());
                beneficiaryFamily.setLastName(child.getLastName());
                beneficiaryFamily.setMiddleName(child.getMiddleName());
                beneficiaryFamily.setRelationship("Child");
                beneficiaryFamily.setDateOfBirth(child.getDateOfBirth());
                beneficiaryFamily.setCountryOfBirth(child.getCountryOfBirth());
                beneficiaryFamilies.add(beneficiaryFamily);
            }
        }
        List<BeneficiaryFamily> firstFive = new ArrayList<>();
        StringBuilder beneficiaryFamilySupplementStringBuilder = new StringBuilder();
        for (int i = 0; i < beneficiaryFamilies.size(); i++) {
            if (i < 5) {
                firstFive.add(beneficiaryFamilies.get(i));
            } else {
                BeneficiaryFamily fam = beneficiaryFamilies.get(i);
                beneficiaryFamilySupplementStringBuilder.append("Last Name: ").append(fam.getLastName()).append("  ");
                beneficiaryFamilySupplementStringBuilder.append("First Name: ").append(fam.getFirstName()).append(" ");
                beneficiaryFamilySupplementStringBuilder.append("Middle Name: ").append(fam.getMiddleName()).append("  ");
                beneficiaryFamilySupplementStringBuilder.append("Birth Date: ").append(fam.getDateOfBirth()).append(" ");
                beneficiaryFamilySupplementStringBuilder.append("Relationship: ").append(fam.getRelationship()).append(" ");
                beneficiaryFamilySupplementStringBuilder.append("Birth Country: ").append(fam.getCountryOfBirth()).append(" ");
                beneficiaryFamilySupplementStringBuilder.append("\n");
            }
        }
        if (beneficiaryFamilySupplementStringBuilder.length() > 0) {
            Supplement.AdditionalRecord additionalRecord = new Supplement.AdditionalRecord();
            additionalRecord.setPageNumber("6");
            additionalRecord.setPartNumber("4");
            additionalRecord.setItemNumber("25-44");
            additionalRecord.setAdditionalInfo(beneficiaryFamilySupplementStringBuilder.toString());
            additionalRecords.add(additionalRecord);
        }
        targetBeneficiary.setBeneficiaryFamilies(firstFive);
        logger.info("convert beneficiaryFamilies--children successfully");


        EmploymentHistory currentEmployment = beneficiary.getCurrentEmployment();
        if(currentEmployment == null){
            currentEmployment = InstanceUtils.createInstanceWithDefaults(EmploymentHistory.class);
            currentEmployment.setNameOfEmployer("Unemployed");
        }
        setBeneficiaryCurrentEmployment(targetBeneficiary, currentEmployment);

        logger.info("convert beneficiary's current employment successfully");

        targetBeneficiary.setLastArrivalInformation(convertLastArrivalInfo(beneficiary.getLastArrivalInformation()));
        logger.info("convert convertLastArrivalInfo successfully");
        return targetBeneficiary;
    }

    private void addParents(List<BeneficiaryFamily> beneficiaryFamilies, Parent parent) {
        if (parent != null) {
            BeneficiaryFamily familyMemberParent = new BeneficiaryFamily();
            familyMemberParent.setFirstName(parent.getFirstName());
            familyMemberParent.setLastName(parent.getLastName());
            familyMemberParent.setMiddleName(parent.getMiddleName());
            familyMemberParent.setDateOfBirth(parent.getDateOfBirth());
            familyMemberParent.setCountryOfBirth(parent.getCountryOfBirth());
            familyMemberParent.setRelationship("Parent");
            beneficiaryFamilies.add(familyMemberParent);
        }
    }


    private void setBeneficiaryCurrentEmployment(Beneficiary beneficiary, EmploymentHistory currentEmployment){
        //set the beneficiary's current employment
        if(currentEmployment == null){
            currentEmployment= InstanceUtils.createInstanceWithDefaults(EmploymentHistory.class);
        }
        beneficiary.setNameOfCurrentEmployer(currentEmployment.getNameOfEmployer());
        beneficiary.setStreetNumberAndNameOfCurrentEmployer(currentEmployment.getStreetNumberAndName());
        if(currentEmployment.isAptCheckbox()){
            beneficiary.setAptCurrentEmployerCheckbox("APT");
        }
        else if(currentEmployment.isFlrCheckbox()){
            beneficiary.setFlrCurrentEmployerCheckbox("FLR");
        } else if(currentEmployment.isSteCheckbox()){
            beneficiary.setSteCurrentEmployerCheckbox("STE");
        }
        beneficiary.setAptSteFlrNumberEmployer(currentEmployment.getAptSteFlrNumber());
        beneficiary.setCityOrTownOfCurrentEmployer(currentEmployment.getCity());
        beneficiary.setStateOfCurrentEmployer(currentEmployment.getState());
        beneficiary.setZipCodeOfCurrentEmployer(currentEmployment.getZipCode());
        beneficiary.setProvinceOfCurrentEmployer(currentEmployment.getProvince());
        beneficiary.setPostalCodeOfCurrentEmployer(currentEmployment.getPostalCode());
        beneficiary.setCountryOfCurrentEmployer(currentEmployment.getCountry());
        beneficiary.setDateEmploymentBegan(currentEmployment.getDateFrom());
    }

    private LastArrivalInformation convertLastArrivalInfo(com.quick.immi.ai.entity.familyBased.businss.LastArrivalInformation sourceLastArrivalInformation) {
        if (sourceLastArrivalInformation == null) {
            sourceLastArrivalInformation = new com.quick.immi.ai.entity.familyBased.businss.LastArrivalInformation();
        }
        LastArrivalInformation lastArrivalInformation = new LastArrivalInformation();
        CopyUtils.copy(sourceLastArrivalInformation, lastArrivalInformation, checkboxProperties, "beneficiary.lastArrivalInformation");
        return lastArrivalInformation;
    }

    private Family getFamily(com.quick.immi.ai.entity.familyBased.businss.Beneficiary beneficiary) {
        Family family = beneficiary.getFamily();
        if (family == null) {
            family = new Family();
        }
        return family;
    }

    private Supplement.AdditionalRecord convertAdditionalRecord(List<MaritalInfo.PreviousSpouseInfo> previousSpouseInfos, Boolean isMaritalStatusMarried) {
        StringBuilder sb = new StringBuilder();
        int index = 2;
        if(isMaritalStatusMarried){
            index = 1;
        }

        for (int i = index; i < previousSpouseInfos.size(); i++) {
            MaritalInfo.PreviousSpouseInfo previousSpouseInfo = previousSpouseInfos.get(i);
            sb.append("Last Name: ").append(previousSpouseInfo.getLastName()).append("  ");
            sb.append("First Name: ").append(previousSpouseInfo.getFirstName()).append(" ");
            sb.append("Middle Name: ").append(previousSpouseInfo.getMiddleName()).append("  ");
            sb.append("DateMarriage End: ")
                .append(previousSpouseInfo.getDateMarriageLegallyEnded());
            sb.append("\n");
        }
        Supplement.AdditionalRecord additionalRecord = new Supplement.AdditionalRecord();
        additionalRecord.setPageNumber("6");
        additionalRecord.setPartNumber("4");
        additionalRecord.setItemNumber("21-24");
        additionalRecord.setAdditionalInfo(sb.toString());
        return additionalRecord;
    }

    private BeneficiarySpouses convertBeneficiarySpouses(MaritalInfo.PreviousSpouseInfo previousSpouseInfo) {
        BeneficiarySpouses ele = new BeneficiarySpouses();
        ele.setLastName(previousSpouseInfo.getLastName());
        ele.setFirstName(previousSpouseInfo.getFirstName());
        ele.setMiddleName(previousSpouseInfo.getMiddleName());
        ele.setDateMarriageEnded(previousSpouseInfo.getDateMarriageLegallyEnded());
        return ele;
    }

    private BeneficiarySpouses convertBeneficiarySpousesFromCurrentSpouse(MaritalInfo.CurrentSpouseInfo currentSpouse) {
        BeneficiarySpouses ele = new BeneficiarySpouses();
        ele.setLastName(currentSpouse.getLastName());
        ele.setFirstName(currentSpouse.getFirstName());
        ele.setMiddleName(currentSpouse.getMiddleName());
        ele.setDateMarriageEnded("N/A");
        return ele;
    }

    private void setCurrentMaritalInfo(Beneficiary targetBeneficiary, MaritalInfo maritalInfo) {
        String numberOfMarriages = maritalInfo.getNumberOfMarriages();
        targetBeneficiary.setTimesBeneficiaryMarried(!StringUtils.isNullOrEmpty(numberOfMarriages) ? numberOfMarriages: "N/A");

        if (maritalInfo.isMaritalStatusMarriedCheckbox()) {
            MaritalInfo.CurrentSpouseInfo currentSpouse = maritalInfo.getCurrentSpouse();

            targetBeneficiary.setCurrentMarriageCity(currentSpouse.getPlaceOfMarriageCity());
            if ("United States".equals(currentSpouse.getPlaceOfMarriageCountry())) {
                targetBeneficiary.setCurrentMarriageState(currentSpouse.getPlaceOfMarriageState());
                targetBeneficiary.setCurrentMarriageProvince(BLANK);
            } else {
                targetBeneficiary.setCurrentMarriageState(BLANK);
                targetBeneficiary.setCurrentMarriageProvince(currentSpouse.getPlaceOfMarriageState());
            }
            targetBeneficiary.setCurrentMarriageCountry(currentSpouse.getPlaceOfMarriageCountry());
            targetBeneficiary.setCurrentMarriageDate(currentSpouse.getDateOfMarriage());
        }
    }

    private void setMaritalStatus(Beneficiary targetBeneficiary, MaritalInfo maritalInfo) {
        if (maritalInfo.isMaritalStatusSingleCheckbox()) {
            targetBeneficiary.setCurrentMaritalStatusSingleCheckbox(
                    checkboxProperties.getProperty("beneficiary.currentMaritalStatusSingleCheckbox"));
        } else if (maritalInfo.isMaritalStatusMarriedCheckbox()) {
            targetBeneficiary.setCurrentMaritalStatusMarriedCheckbox(
                    checkboxProperties.getProperty("beneficiary.currentMaritalStatusMarriedCheckbox"));
        } else if (maritalInfo.isMaritalStatusDivorcedCheckbox()) {
            targetBeneficiary.setCurrentMaritalStatusDivorcedCheckbox(
                    checkboxProperties.getProperty("beneficiary.currentMaritalStatusDivorcedCheckbox"));
        } else if (maritalInfo.isMaritalStatusWidowedCheckbox()) {
            targetBeneficiary.setCurrentMaritalStatusWidowedCheckbox(
                    checkboxProperties.getProperty("beneficiary.currentMaritalStatusWidowedCheckbox"));
        } else if (maritalInfo.isMaritalStatusSeparatedCheckbox()) {
            targetBeneficiary.setCurrentMaritalStatusSeparatedCheckbox(
                    checkboxProperties.getProperty("beneficiary.currentMaritalStatusSeparatedCheckbox"));
        } else if (maritalInfo.isMaritalStatusAnnulledCheckbox()) {
            targetBeneficiary.setCurrentMaritalStatusAnnulledCheckbox(
                    checkboxProperties.getProperty("beneficiary.currentMaritalStatusAnnulledCheckbox"));
        }
    }

    private MaritalInfo getMaritalInfo(com.quick.immi.ai.entity.familyBased.businss.Beneficiary beneficiary) {
        MaritalInfo maritalInfo = beneficiary.getMaritalInfo();
        if (maritalInfo == null) {
            maritalInfo = new MaritalInfo();
        }
        return maritalInfo;
    }

    private List<BeneficiaryOtherName> convertBeneficiaryOtherName(List<com.quick.immi.ai.entity.familyBased.businss.BeneficiaryOtherName> beneficiaryOtherNames) {
        List<BeneficiaryOtherName> beneficiaryOtherNamesInTable = new ArrayList<>();
        if(beneficiaryOtherNames == null || beneficiaryOtherNames.isEmpty()){
            beneficiaryOtherNamesInTable.add(InstanceUtils.createInstanceWithDefaults(BeneficiaryOtherName.class));
        }
        if (beneficiaryOtherNames != null) {
            for (int i = 0; i < 1 && i < beneficiaryOtherNames.size(); i++) {
                com.quick.immi.ai.entity.familyBased.businss.BeneficiaryOtherName beneficiaryOtherName = beneficiaryOtherNames.get(i);
                if(beneficiaryOtherName == null){
                    continue;
                }
                BeneficiaryOtherName targetBeneficiaryOtherName
                        = new BeneficiaryOtherName();

                CopyUtils.copy(beneficiaryOtherName, targetBeneficiaryOtherName, checkboxProperties,
                        String.format("beneficiary.beneficiaryOtherNames.%s", i + 1));
                beneficiaryOtherNamesInTable.add(targetBeneficiaryOtherName);
            }
        }
        return beneficiaryOtherNamesInTable;
    }

    private com.quick.immi.ai.entity.familyBased.i130.Address convertAddress(Address address,
                                                                             String prefix) {
        if (address == null) {
            address = new Address();
        }
        com.quick.immi.ai.entity.familyBased.i130.Address targetAddress = new com.quick.immi.ai.entity.familyBased.i130.Address();
        CopyUtils.copy(address, targetAddress, checkboxProperties, prefix);
        return targetAddress;
    }

    private Biographic convertBiographic(FamilyBasedCaseProfile familyBasedCaseProfile) {
        Biographic targetBiographic = new Biographic();

        com.quick.immi.ai.entity.familyBased.businss.Petitioner petitioner = familyBasedCaseProfile.getPetitioner();
        if (petitioner == null) {
            petitioner = new com.quick.immi.ai.entity.familyBased.businss.Petitioner();
        }
        com.quick.immi.ai.entity.familyBased.businss.Biographic biographic = petitioner.getBiographic();
        if (petitioner.getBiographic() == null) {
            biographic = new com.quick.immi.ai.entity.familyBased.businss.Biographic();
        }
        CopyUtils.copy(biographic, targetBiographic, checkboxProperties, "biographic");

        if(biographic.isEthnicityHispanicCheckbox()){
            targetBiographic.setEthnicityNotHispanicCheckbox("H");
            targetBiographic.setEthnicityHispanicCheckbox("");
        }else if(biographic.isEthnicityNotHispanicCheckbox()){
            targetBiographic.setEthnicityNotHispanicCheckbox("");
            targetBiographic.setEthnicityHispanicCheckbox("NH");
        }
        return targetBiographic;
    }

    // // Part 6 - Petitioner's Statement
    private PetitionerStatement petitionerStatement;

    private PreviousPetition convertPreviousPetition(FamilyBasedCaseProfile familyBasedCaseProfile) {
        PreviousPetition targetPreviousPetition = new PreviousPetition();
        com.quick.immi.ai.entity.familyBased.businss.PreviousPetition previousPetition = familyBasedCaseProfile.getPreviousPetition();
        if (previousPetition == null) {
            previousPetition = new com.quick.immi.ai.entity.familyBased.businss.PreviousPetition();
        }
        CopyUtils.copy(previousPetition, targetPreviousPetition, checkboxProperties, "previousPetition");

        List<com.quick.immi.ai.entity.familyBased.i130.AdditionalRelative> additionalRelativeList = new ArrayList<>();

        if (previousPetition.getAdditionalRelatives() != null) {
            for (int i = 0; i < 2 && i < previousPetition.getAdditionalRelatives().size(); i++) {
                com.quick.immi.ai.entity.familyBased.businss.AdditionalRelative additionalRelative =
                        familyBasedCaseProfile.getPreviousPetition().getAdditionalRelatives().get(i);
                com.quick.immi.ai.entity.familyBased.i130.AdditionalRelative targetAdditionalRelative =
                        new com.quick.immi.ai.entity.familyBased.i130.AdditionalRelative();
                CopyUtils.copy(additionalRelative, targetAdditionalRelative, checkboxProperties, String.format("previousPetition.additionalRelatives.%s", i + 1));
                additionalRelativeList.add(targetAdditionalRelative);
            }
        }
        targetPreviousPetition.setAdditionalRelatives(additionalRelativeList);

        return targetPreviousPetition;
    }

    private PetitionerStatement convertPetitionerStatement(com.quick.immi.ai.entity.familyBased.businss.PetitionerStatement petitionerStatement) {
        PetitionerStatement targetPetitionerStatement = new PetitionerStatement();
        if (petitionerStatement == null) {
            petitionerStatement = new com.quick.immi.ai.entity.familyBased.businss.PetitionerStatement();
        }
        CopyUtils.copy(petitionerStatement, targetPetitionerStatement, checkboxProperties, "petitionerStatement");

        return targetPetitionerStatement;
    }

    private Interpreter convertInterpreter(com.quick.immi.ai.entity.familyBased.businss.Interpreter interpreter) {
        Interpreter targetInterpreter = new Interpreter();
        if (interpreter == null) {
            interpreter = new com.quick.immi.ai.entity.familyBased.businss.Interpreter();
        }
        CopyUtils.copy(interpreter, targetInterpreter, checkboxProperties, "interpreter");

        return targetInterpreter;
    }

    private Preparer convertPrepare(LawyerProfile lawyerProfile) {
        Preparer preparer = new Preparer();

        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        LawyerEligibility eligibility = lawyerProfile.getEligibility();

        if (basicInfo != null) {
            preparer.setStreetNumberAndName(basicInfo.getStreetNumberAndName());
            if (basicInfo.isAptCheckbox()) {
                preparer.setAptCheckbox("APT");
            }

            if (basicInfo.isSteCheckbox()) {
                preparer.setAptCheckbox("STE");
            }

            if (basicInfo.isFlrCheckbox()) {
                preparer.setAptCheckbox("FLR ");
            }
            preparer.setAptSteFlrNumber(basicInfo.getAptSteFlrNumber());
            preparer.setCity(basicInfo.getCity());
            preparer.setState(basicInfo.getStateDropdown());
            preparer.setZipCode(basicInfo.getZipCode());
            preparer.setPostalCode(basicInfo.getPostalCode());
            preparer.setCountry(basicInfo.getCountry());
            preparer.setDaytimeTelephoneNumber(basicInfo.getDaytimeTelephoneNumber());
            preparer.setMobileTelephoneNumber(basicInfo.getMobileTelephoneNumber());
            preparer.setLastName(basicInfo.getLastName());
            preparer.setFirstName(basicInfo.getFirstName());
        }

        if (eligibility != null) {
            preparer.setBusinessName(eligibility.getNameofLawFirm());
        }

        return preparer;
    }

    private Attorney convertAttorney(LawyerProfile lawyerProfile) {
        Attorney attorney = new Attorney();

        LawyerEligibility eligibility = lawyerProfile.getEligibility();
        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        if (eligibility != null) {
            attorney.setStateBarNumber(eligibility.getBarNumber());
        }
        if (basicInfo != null) {
            attorney.setUSCISOnlineAccountNumber(basicInfo.getUscisOnlineAccountNumber());
        }
        Attorney targetAttorney = new Attorney();

        CopyUtils.copy(attorney, targetAttorney, checkboxProperties, "");
        targetAttorney.setG28AttachedCheckbox("1");
        return targetAttorney;
    }
}