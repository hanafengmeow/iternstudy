package com.quick.immi.ai.converter.asylum;

import com.amazonaws.util.StringUtils;
import com.quick.immi.ai.entity.*;
import com.quick.immi.ai.entity.asylum.business.*;
import com.quick.immi.ai.entity.asylum.i589.I589Table;
import com.quick.immi.ai.entity.asylum.i589.SupplementA;
import com.quick.immi.ai.entity.asylum.i589.SupplementB;
import com.quick.immi.ai.entity.common.NameEntity;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.NormalizeUtils;
import crawlercommons.utils.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
public class I589TableConverter {

    private Properties checkboxProperties;
    private List<SupplementB> supplementBS;
    private Applicant applicant;

    public I589TableConverter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.I589Checkbox);
        supplementBS = new ArrayList<>();
    }

    public I589Table getI589Table(String asylumType, LawyerProfile lawyerProfile, AsylumCaseProfile asylumCaseProfile) {
        this.applicant = asylumCaseProfile.getApplicant();

        I589Table i589Table = new I589Table();
        if (asylumCaseProfile.getApplicationDetails().isAsylumBasedOnTortureConventionCheckbox()) {
            i589Table.setApplyForWithholdingYesCheckbox("1");
        }
        i589Table.setApplicant(covert(asylumCaseProfile.getApplicant()));
        i589Table.setSpouse(covertSpouse(asylumCaseProfile.getFamily().getSpouse()));
        i589Table.setChildren(covertChildren(asylumCaseProfile.getFamily().getChildren()));
        i589Table.setBackground(covertBackground(asylumCaseProfile.getBackground(), asylumCaseProfile.getFamily()));
        i589Table.setApplicationDetails(covertApplicationDetails(asylumCaseProfile.getApplicationDetails()));

        i589Table.setSignature(convertSignature(asylumCaseProfile.getSignature()));
        i589Table.setDeclaration(convertDeclaration(asylumType, lawyerProfile));

        i589Table.setSupplementAs(generateSupplementA(asylumCaseProfile.getFamily().getChildren(), asylumCaseProfile.getApplicant()));
        i589Table.setSupplementBs(supplementBS);
        return i589Table;
    }

    private List<SupplementA> generateSupplementA(List<Child> children, Applicant applicant){
        List<SupplementA> ans = new ArrayList<>();
        if(children == null || children.isEmpty() || applicant == null){
            return ans;
        }

        int i = 4;
        while(i < children.size()){
            SupplementA supplementA = new SupplementA();
            supplementA.setAlienNumber(applicant.getAlienNumber());
            supplementA.setApplicantName(applicant.getFirstName() + getMiddleName(applicant.getMiddleName()) + applicant.getLastName());
            List<com.quick.immi.ai.entity.asylum.i589.Child> currentChildren = new ArrayList<>();

            com.quick.immi.ai.entity.asylum.i589.Child targetChild = new com.quick.immi.ai.entity.asylum.i589.Child();
            CopyUtils.copy(children.get(i), targetChild, checkboxProperties, "supplement.a.children.1");
            currentChildren.add(targetChild);
            i++;
            if(i < children.size()){
                com.quick.immi.ai.entity.asylum.i589.Child secondTargetChild = new com.quick.immi.ai.entity.asylum.i589.Child();
                CopyUtils.copy(children.get(i), secondTargetChild, checkboxProperties, "supplement.a.children.2");
                currentChildren.add(targetChild);
                i++;
            }
            ans.add(supplementA);
        }

        return ans;
    }

    private com.quick.immi.ai.entity.asylum.i589.Declaration convertDeclaration(String asylumType, LawyerProfile lawyerProfile) {
        com.quick.immi.ai.entity.asylum.i589.Declaration source =
                new com.quick.immi.ai.entity.asylum.i589.Declaration();
        LawyerBasicInfo basicInfo = lawyerProfile.getBasicInfo();
        LawyerEligibility eligibility = lawyerProfile.getEligibility();

        source.setCompleteName(basicInfo.getFirstName() + getMiddleName(basicInfo.getMiddleName()) + basicInfo.getLastName());
        String[] phoneNumber = getPhoneNumber(lawyerProfile.getBasicInfo().getDaytimeTelephoneNumber());
        source.setTeleNumberAreacode(phoneNumber[0]);
        source.setTeleNumber(phoneNumber[1]);
        source.setStreetNumberAndName(basicInfo.getStreetNumberAndName());
        source.setAptNumber(basicInfo.getAptSteFlrNumber());
        source.setCity(basicInfo.getCity());
        source.setState(basicInfo.getStateDropdown());
        source.setZipCode(basicInfo.getZipCode());
        source.setAttorneyStateBarNumber(eligibility.getBarNumber());
        source.setAttorneyUscisOnlineAccountNumber(basicInfo.getEoirNumber());

        if (AsylumType.AFFIRMATIVE.getValue().equals(asylumType)) {
            source.setG28FormAttachedYesCheckbox("1");
        }

        com.quick.immi.ai.entity.asylum.i589.Declaration target =
                new com.quick.immi.ai.entity.asylum.i589.Declaration();

        CopyUtils.copy(source, target, checkboxProperties, "");
        target.setPreparerSignature("");
        log.info(String.format("part E target=%s", target));
        return target;
    }

    private String getMiddleName(String middleName){
        return (StringUtils.isNullOrEmpty(middleName) || middleName.trim().equalsIgnoreCase("N/A"))
                ? " " : " " + middleName + " ";
    }

    // result[0] is area code
    // result[1] is phone number
    //for test
    public String[] getPhoneNumber(String phoneNumber){
        if(StringUtils.isNullOrEmpty(phoneNumber)){
            return new String[]{"", ""};
        }
        String[] result = new String[2];
        char[] chars = phoneNumber.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] >='0' && chars[i] <='9'){
                sb.append(chars[i]);
            }
            if(sb.length() == 3 && result[0] == null){
                result[0] = sb.toString();
                sb = new StringBuilder();
            }
        }
        result[1] = sb.toString();

        return result;
    }

    private com.quick.immi.ai.entity.asylum.i589.YourSignature convertSignature(com.quick.immi.ai.entity.asylum.business.YourSignature signature) {
        com.quick.immi.ai.entity.asylum.i589.YourSignature targetYourSignature =
                new com.quick.immi.ai.entity.asylum.i589.YourSignature();
        signature.setFamilyMemberAssistNoCheckbox(true);
        signature.setOtherPeopleAssistYesCheckbox(true);
        signature.setProvidePeopleCounselYesCheckbox(true);
        CopyUtils.copy(signature, targetYourSignature, checkboxProperties, "signature");
        targetYourSignature.setName(applicant.getFirstName() + getMiddleName(applicant.getMiddleName()) + applicant.getLastName());
        targetYourSignature.setSignature("");
        targetYourSignature.setDate("");
        targetYourSignature.setNameInNativeAlphabet("");
        targetYourSignature.setMembers(convertRelationship(signature.getMembers()));
        return targetYourSignature;
    }

    private List<com.quick.immi.ai.entity.asylum.i589.Relationship> convertRelationship(List<com.quick.immi.ai.entity.asylum.business.Relationship> relationships){
        if(relationships == null || relationships.isEmpty()) {
            return new ArrayList<>();
        }
        List<com.quick.immi.ai.entity.asylum.i589.Relationship> result = new ArrayList<>();
        for(int i = 0; i < 2 && i < relationships.size(); i++){
            com.quick.immi.ai.entity.asylum.i589.Relationship targetRelationship
                    = new com.quick.immi.ai.entity.asylum.i589.Relationship();
            CopyUtils.copy(relationships.get(i), targetRelationship, checkboxProperties, "");

            result.add(targetRelationship);
        }

        return result;
    }

    public List<com.quick.immi.ai.entity.asylum.i589.Child> covertChildren(List<com.quick.immi.ai.entity.asylum.business.Child> children) {
        children = NormalizeUtils.formalize(children);
        List<com.quick.immi.ai.entity.asylum.i589.Child> result = new ArrayList<>();

        for(int i = 0; i < children.size() && i < 4; i++){
            com.quick.immi.ai.entity.asylum.i589.Child targetChild = new com.quick.immi.ai.entity.asylum.i589.Child();
            Child source = children.get(i);
            if(source.isPersonInUSYesCheckbox()){
                source.setInThisApplicationNoCheckbox(true);
            }
            if(!source.isImmigrationCourtProceedingYesCheckbox()){
                source.setImmigrationCourtProceedingNoCheckbox(true);
            }
            CopyUtils.copy(source, targetChild, checkboxProperties, "children." + (i + 1));
            result.add(targetChild);
        }

        return result;
    }

    private com.quick.immi.ai.entity.asylum.i589.ApplicationDetails covertApplicationDetails
            (com.quick.immi.ai.entity.asylum.business.ApplicationDetails applicationDetails){
        com.quick.immi.ai.entity.asylum.i589.ApplicationDetails targetApplicationDetails =
                new com.quick.immi.ai.entity.asylum.i589.ApplicationDetails();
        CopyUtils.copy(applicationDetails, targetApplicationDetails, checkboxProperties, "applicationDetails");

        //12
        String explainHaveBeenHarmedYesContent= trimContent(targetApplicationDetails.getExplainHaveBeenHarmedYes(), 12,
                targetApplicationDetails.getExplainHaveBeenHarmedYesPart(),
                targetApplicationDetails.getExplainHaveBeenHarmedYesQuestion());

        targetApplicationDetails.setExplainHaveBeenHarmedYes(explainHaveBeenHarmedYesContent);

        //12
        String explainFearReturnYesContent= trimContent(targetApplicationDetails.getExplainFearReturnYes(), 12,
                targetApplicationDetails.getExplainFearReturnYesPart(),
                targetApplicationDetails.getExplainFearReturnYesQuestion());

        targetApplicationDetails.setExplainFearReturnYes(explainFearReturnYesContent);

        //9
        String explainFamilyMembersBeenChargedYesContent= trimContent(targetApplicationDetails.getExplainFamilyMembersBeenChargedYes(), 9,
                targetApplicationDetails.getExplainFamilyMembersBeenChargedYesPart(),
                targetApplicationDetails.getExplainFamilyMembersBeenChargedYesQuestion());
        targetApplicationDetails.setExplainFamilyMembersBeenChargedYes(explainFamilyMembersBeenChargedYesContent);

        //9
        String explainYouOrFamilyBelongAnyOrganizationYesContent= trimContent(targetApplicationDetails.getExplainYouOrFamilyBelongAnyOrganizationYes(), 9,
                targetApplicationDetails.getExplainYouOrFamilyBelongAnyOrganizationYesPart(),
                targetApplicationDetails.getExplainYouOrFamilyBelongAnyOrganizationYesQuestion());
        targetApplicationDetails.setExplainYouOrFamilyBelongAnyOrganizationYes(explainYouOrFamilyBelongAnyOrganizationYesContent);


        //9
        String explainYouOrFamilyContinueBelongAnyOrganizationYesContent= trimContent(targetApplicationDetails.getExplainYouOrFamilyContinueBelongAnyOrganizationYes(), 9,
                targetApplicationDetails.getExplainYouOrFamilyContinueBelongAnyOrganizationYesPart(),
                targetApplicationDetails.getExplainYouOrFamilyContinueBelongAnyOrganizationYesQuestion());
        targetApplicationDetails.setExplainYouOrFamilyBelongAnyOrganizationYes(explainYouOrFamilyContinueBelongAnyOrganizationYesContent);

        //9
        String explainAfraidOfReturnYesContent= trimContent(targetApplicationDetails.getExplainAfraidOfReturnYes(), 9,
                targetApplicationDetails.getExplainAfraidOfReturnYesPart(),
                targetApplicationDetails.getExplainAfraidOfReturnYesQuestion());
        targetApplicationDetails.setExplainAfraidOfReturnYes(explainAfraidOfReturnYesContent);

        //8
        String explainAppliedBeforeYesContent = trimContent(targetApplicationDetails.getExplainAppliedBeforeYes(), 9,
        targetApplicationDetails.getExplainAppliedBeforeYesPart(),
        targetApplicationDetails.getExplainAppliedBeforeYesQuestion());
        targetApplicationDetails.setExplainAppliedBeforeYes(explainAppliedBeforeYesContent);

        //9
        String explainAnyLawfulStatusAnyCountryYesContent = trimContent(targetApplicationDetails.getExplainAnyLawfulStatusAnyCountryYes(), 9,
                targetApplicationDetails.getExplainAnyLawfulStatusAnyCountryYesPart(),
                targetApplicationDetails.getExplainAnyLawfulStatusAnyCountryYesQuestion());
        targetApplicationDetails.setExplainAnyLawfulStatusAnyCountryYes(explainAnyLawfulStatusAnyCountryYesContent);

        //11
        String explainHaveYouHarmOthersYesContent = trimContent(targetApplicationDetails.getExplainHaveYouHarmOthersYes(), 11,
                targetApplicationDetails.getExplainHaveYouHarmOthersYesPart(),
                targetApplicationDetails.getExplainHaveYouHarmOthersYesQuestion());
        targetApplicationDetails.setExplainHaveYouHarmOthersYes(explainHaveYouHarmOthersYesContent);

        String explainReturnCountryYesContent = trimContent(targetApplicationDetails.getExplainReturnCountryYes(), 15,
                targetApplicationDetails.getExplainReturnCountryYesPart(),
                targetApplicationDetails.getExplainReturnCountryYesQuestion());
        targetApplicationDetails.setExplainReturnCountryYes(explainReturnCountryYesContent);

        String explainMoreThanOneYearAfterArrivalYesContent = trimContent(targetApplicationDetails.getExplainMoreThanOneYearAfterArrivalYes(), 15,
                targetApplicationDetails.getExplainMoreThanOneYearAfterArrivalYesPart(),
                targetApplicationDetails.getExplainMoreThanOneYearAfterArrivalYesQuestion());
        targetApplicationDetails.setExplainMoreThanOneYearAfterArrivalYes(explainMoreThanOneYearAfterArrivalYesContent);

        String explainHaveCommittedCrimeYesContent = trimContent(targetApplicationDetails.getExplainHaveCommittedCrimeYes(), 15,
                targetApplicationDetails.getExplainHaveCommittedCrimeYesPart(),
                targetApplicationDetails.getExplainHaveCommittedCrimeYesQuestion());
        targetApplicationDetails.setExplainHaveCommittedCrimeYes(explainHaveCommittedCrimeYesContent);

        return targetApplicationDetails;
    }

    //each line can contain 144 characters
        public String trimContent(String content, int maxLines, String part, String question){
        if(content == null || StringUtils.isNullOrEmpty(content.trim()) || content.equalsIgnoreCase("N/A")){
            return "N/A";
        }
        content = content.replace("\\n", "\n");

        int maxLenPerLine = 144;
        int maxCharacters = maxLines * maxLenPerLine;
        int buffer = 40;

        List<String> paragraphs = Arrays.stream(content.split("\n")).filter(e-> !Strings.isBlank(e)).collect(Collectors.toList());
        StringBuffer remainingContent = new StringBuffer();
        int currentUsedSpace = 0;
        StringBuffer result = new StringBuffer();
        for (String paragraph : paragraphs) {
            if (paragraph.isEmpty()) {
                continue; // Skip empty paragraphs
            }

            if (currentUsedSpace + buffer > maxCharacters) {
                remainingContent.append(paragraph).append("\n");
                continue; // skip updating result
            }

            if (paragraph.length() + currentUsedSpace + buffer < maxCharacters) {
                result.append(paragraph).append("\n\n");
                currentUsedSpace += paragraph.length() + maxLenPerLine + maxLenPerLine;
            } else {
                List<String> words = Arrays.stream(paragraph.split(" ")).filter(e-> !Strings.isBlank(e)).collect(Collectors.toList());
                for(int i = 0; i < words.size(); i++){
                    if(words.get(i).length() + result.length() + buffer > maxCharacters){
                        result.append(" <please see supplement>");
                        currentUsedSpace += buffer;
                        for(int j = i; j < words.size(); j++){
                            remainingContent.append(words.get(j));
                            if(j != words.size() - 1){
                                remainingContent.append(" ");
                            }
                        }
                        remainingContent.append("\n");
                        break;
                    } else {
                        result.append(words.get(i));
                        currentUsedSpace+= words.get(i).length();
                        if(i != words.size() - 1){
                            result.append(" ");
                            currentUsedSpace +=1;
                        }
                    }
                }
            }
        }
        if(remainingContent.length() > 0){
            log.info("Add remaining content to SupplementB ====> ");
            SupplementB supplementB = new SupplementB();
            supplementB.setAlienNumber(applicant.getAlienNumber());
            supplementB.setApplicantName(applicant.getFirstName() + getMiddleName(applicant.getMiddleName()) + applicant.getLastName());
            supplementB.setPart(part);
            supplementB.setQuestion(question);
            supplementB.setAnswer(remainingContent.toString());
            supplementBS.add(supplementB);
        }
        return result.toString();
    }

    private com.quick.immi.ai.entity.asylum.i589.Background covertBackground(Background background, Family family) {
        if(background == null){
            background = new Background();
        }
        if(family == null){
            family = new Family();
        }
        com.quick.immi.ai.entity.asylum.i589.Background targetBackground
                = new com.quick.immi.ai.entity.asylum.i589.Background();

        targetBackground.setAddressHistoriesBeforeUS(covert(background.getAddressHistoriesBeforeUS(), "Q1", 2));
        targetBackground.setUsAddressHistoriesPast5Years(covert(background.getUsAddressHistoriesPast5Years(), "Q2", 5));
        targetBackground.setEducationHistories(covertEducationHistory(background.getEducationHistories()));
        targetBackground.setEmploymentHistories(covertEmploymentHistory(background.getEmploymentHistories()));

        targetBackground.setFather(covertFamilyMember(family.getFather(), "background.father"));
        targetBackground.setMother(covertFamilyMember(family.getMother(), "background.mother"));
        targetBackground.setSiblings(covertFamilyMembers(family.getSiblings()));
        return targetBackground;
    }


    private List<com.quick.immi.ai.entity.asylum.i589.FamilyMember> covertFamilyMembers(List<com.quick.immi.ai.entity.asylum.business.FamilyMember> familyMembers){
        if(familyMembers == null){
            familyMembers = new ArrayList<>();
        }
        while (familyMembers.size() < 4) {
            familyMembers.add(new com.quick.immi.ai.entity.asylum.business.FamilyMember());
        }

        List<com.quick.immi.ai.entity.asylum.i589.FamilyMember> result = new ArrayList<>();

        for(int i = 0; i < familyMembers.size() && i < 4; i++){
            result.add(covertFamilyMember(familyMembers.get(i), "background.siblings." + (i + 1)));
        }

        if(familyMembers.size() > 4){
            StringBuffer sb = new StringBuffer();
            for(int i = 4; i < familyMembers.size(); i++){
                FamilyMember familyMember = familyMembers.get(i);
                sb.append(String.format("Full Name: %s\n", familyMember.getName()));
                sb.append(String.format("City/Town and Country of Birth: %s\n", familyMember.getCityTownAndBirth()));
                sb.append(String.format("deceased: %s\n", familyMember.isDeceasedCheckbox()));
                sb.append(String.format("Current Location: %s\n", familyMember.getLocation()));
                sb.append("\n");
            }

            SupplementB supplementB = new SupplementB();
            supplementB.setAlienNumber(applicant.getAlienNumber());
            supplementB.setApplicantName(applicant.getFirstName() + getMiddleName(applicant.getMiddleName()) + applicant.getLastName());
            supplementB.setPart("Part A.III");
            supplementB.setQuestion("Q5");
            supplementB.setAnswer(sb.toString());

            supplementBS.add(supplementB);
        }

        return result;
    }

    private com.quick.immi.ai.entity.asylum.i589.FamilyMember covertFamilyMember(com.quick.immi.ai.entity.asylum.business.FamilyMember
                                                                                  familyMember,
                                                                                 String prefix){
        if(familyMember == null){
            familyMember = new FamilyMember();
        }

        com.quick.immi.ai.entity.asylum.i589.FamilyMember targetFamilyMember
                = new com.quick.immi.ai.entity.asylum.i589.FamilyMember();
        CopyUtils.copy(familyMember, targetFamilyMember, checkboxProperties, prefix);
        return targetFamilyMember;
    }


    private List<com.quick.immi.ai.entity.asylum.i589.EducationHistory> covertEducationHistory(
            List<com.quick.immi.ai.entity.asylum.business.EducationHistory> educationHistories){
        if(educationHistories == null){
            educationHistories = new ArrayList<>();
        }

        while(educationHistories.size() < 4){
            educationHistories.add(new EducationHistory());
        }

        List<com.quick.immi.ai.entity.asylum.i589.EducationHistory> result =
                new ArrayList<>();

        for(com.quick.immi.ai.entity.asylum.business.EducationHistory educationHistory : educationHistories){
            com.quick.immi.ai.entity.asylum.i589.EducationHistory targetEducationHistory
                    = new com.quick.immi.ai.entity.asylum.i589.EducationHistory();
            CopyUtils.copy(educationHistory, targetEducationHistory, checkboxProperties, "");
            result.add(targetEducationHistory);
        }

        return result;
    }

    private List<com.quick.immi.ai.entity.asylum.i589.EmploymentHistory> covertEmploymentHistory(
            List<com.quick.immi.ai.entity.asylum.business.EmploymentHistory> employmentHistories){
        if(employmentHistories == null){
            employmentHistories = new ArrayList<>();
        }

        while(employmentHistories.size() < 3){
            employmentHistories.add(new EmploymentHistory());
        }

        List<com.quick.immi.ai.entity.asylum.i589.EmploymentHistory> result =
                new ArrayList<>();

        for(int i  = 0; i < employmentHistories.size(); i++){
            List<com.quick.immi.ai.entity.asylum.i589.EmploymentHistory> employmentHistory = getEmploymentHistory(employmentHistories.get(i));

            if (result.size() + employmentHistory.size() <= 4) {
                result.addAll(employmentHistory);
            } else {
                addRemaningEmploymentHistoryToSupplementB(employmentHistories, i);
                break;
            }
        }
        return result;
    }

    private void addRemaningEmploymentHistoryToSupplementB(List<com.quick.immi.ai.entity.asylum.business.EmploymentHistory> employmentHistories, int index){
        if(index >= employmentHistories.size()){
            return;
        }

        StringBuffer sb = new StringBuffer();
        for(int i = index; i < employmentHistories.size(); i++){
            EmploymentHistory employmentHistory = employmentHistories.get(i);
            sb.append(String.format("Name And Address of Employer: %s\n", employmentHistory.getNameAndAddress()));
            sb.append(String.format("Your Occupation: %s\n", employmentHistory.getOccupation()));
            sb.append(String.format("Date From: %s\n", employmentHistory.getStartDate()));
            sb.append(String.format("Date End: %s\n", employmentHistory.getEndDate()));
            sb.append("\n");
        }
        SupplementB supplementB = new SupplementB();
        supplementB.setAlienNumber(applicant.getAlienNumber());
        supplementB.setApplicantName(applicant.getFirstName() + getMiddleName(applicant.getMiddleName()) + applicant.getLastName());
        supplementB.setPart("Part A.III");
        supplementB.setQuestion("Q4");
        supplementB.setAnswer(sb.toString());

        supplementBS.add(supplementB);
    }

    private List<com.quick.immi.ai.entity.asylum.i589.EmploymentHistory> getEmploymentHistory(
            com.quick.immi.ai.entity.asylum.business.EmploymentHistory employmentHistory) {

        List<com.quick.immi.ai.entity.asylum.i589.EmploymentHistory> result = new ArrayList<>();
        int numberAndStreetMaxLen = 55;

        com.quick.immi.ai.entity.asylum.i589.EmploymentHistory targetEmploymentHistory
                = new com.quick.immi.ai.entity.asylum.i589.EmploymentHistory();

        CopyUtils.copy(employmentHistory, targetEmploymentHistory, checkboxProperties, "");
        if (targetEmploymentHistory.getNameAndAddress().length() > numberAndStreetMaxLen) {
            String nameStreet = targetEmploymentHistory.getNameAndAddress();
            String[] s = nameStreet.split(" ");
            StringBuffer sb = new StringBuffer();
            int i = 0;
            while (sb.length() + s[i].length() <= numberAndStreetMaxLen) {
                sb.append(s[i] + " ");
                i++;
            }
            sb.trimToSize();
            targetEmploymentHistory.setNameAndAddress(sb.toString());

            StringBuffer sb1 = new StringBuffer();

            while (i < s.length) {
                sb1.append(s[i] + " ");
                i++;
            }

            sb1.trimToSize();
            com.quick.immi.ai.entity.asylum.i589.EmploymentHistory secondTargetEmploymentHistory
                    = new com.quick.immi.ai.entity.asylum.i589.EmploymentHistory();
            secondTargetEmploymentHistory.setNameAndAddress(sb1.toString());
            secondTargetEmploymentHistory.setOccupation("N/A");
            secondTargetEmploymentHistory.setEndDate("N/A");
            secondTargetEmploymentHistory.setStartDate("N/A");

            result.add(targetEmploymentHistory);
            result.add(secondTargetEmploymentHistory);
            return result;
        } else {
            result.add(targetEmploymentHistory);
            return result;
        }
    }


    public List<com.quick.immi.ai.entity.asylum.i589.AddressHistory> covert(List<com.quick.immi.ai.entity.asylum.business.AddressHistory> addressHistoriesBefore,
                                                                            String question,
                                                                            int rows){
        if(addressHistoriesBefore == null){
            addressHistoriesBefore = new ArrayList<>();
        }
        //for front 8, ---- 25 for front 9
        List<com.quick.immi.ai.entity.asylum.i589.AddressHistory> result = new ArrayList<>();
        for(int i = 0; i < addressHistoriesBefore.size(); i++){
            List<com.quick.immi.ai.entity.asylum.i589.AddressHistory> addressHistories = getAddressHistory(addressHistoriesBefore.get(i));
            if(addressHistories.size() + result.size() <= rows){
                result.addAll(addressHistories);
            } else {
                addRemaningAddressHistoriesBeforeToSupplementB(addressHistoriesBefore, i, question);
                break;
            }
        }
        while (result.size() < rows){
            com.quick.immi.ai.entity.asylum.i589.AddressHistory emptyTarget = new com.quick.immi.ai.entity.asylum.i589.AddressHistory();
            CopyUtils.copy(new com.quick.immi.ai.entity.asylum.business.AddressHistory(), emptyTarget, checkboxProperties, "");
            result.add(emptyTarget);
        }
        return result;
    }

    private List<com.quick.immi.ai.entity.asylum.i589.AddressHistory> getAddressHistory(com.quick.immi.ai.entity.asylum.business.AddressHistory addressHistory) {
        List<com.quick.immi.ai.entity.asylum.i589.AddressHistory> result = new ArrayList<>();
        int numberAndStreetMaxLen = 28;

        com.quick.immi.ai.entity.asylum.i589.AddressHistory targetAddressHistory
                = new com.quick.immi.ai.entity.asylum.i589.AddressHistory();

        CopyUtils.copy(addressHistory, targetAddressHistory, checkboxProperties, "");
        if (targetAddressHistory.getNumberAndStreet().length() > numberAndStreetMaxLen) {
            String numberAndStreet = targetAddressHistory.getNumberAndStreet();
            String[] s = numberAndStreet.split(" ");
            StringBuffer sb = new StringBuffer();
            int i = 0;
            while (sb.length() + s[i].length() <= numberAndStreetMaxLen) {
                sb.append(s[i] + " ");
                i++;
            }
            sb.trimToSize();
            targetAddressHistory.setNumberAndStreet(sb.toString());

            StringBuffer sb1 = new StringBuffer();

            while (i < s.length) {
                sb1.append(s[i] + " ");
                i++;
            }

            sb1.trimToSize();
            com.quick.immi.ai.entity.asylum.i589.AddressHistory secondTargetAddressHistory
                    = new com.quick.immi.ai.entity.asylum.i589.AddressHistory();
            secondTargetAddressHistory.setNumberAndStreet(sb1.toString());
            secondTargetAddressHistory.setCity("N/A");
            secondTargetAddressHistory.setProvince("N/A");
            secondTargetAddressHistory.setCountry("N/A");
            secondTargetAddressHistory.setStartDate("N/A");
            secondTargetAddressHistory.setEndDate("N/A");

            result.add(targetAddressHistory);
            result.add(secondTargetAddressHistory);
            return result;
        } else {
            result.add(targetAddressHistory);
            return result;
        }
    }

    private void addRemaningAddressHistoriesBeforeToSupplementB(List<AddressHistory> addressHistoriesBefore, int index, String question){
        if(index >= addressHistoriesBefore.size()){
            return;
        }

        StringBuffer sb = new StringBuffer();
        for(int i = index; i < addressHistoriesBefore.size(); i++){
            AddressHistory addressHistory = addressHistoriesBefore.get(i);
            sb.append(String.format("NumberAndStreet: %s\n", addressHistory.getNumberAndStreet()));
            sb.append(String.format("City/Town: %s\n", addressHistory.getCity()));
            sb.append(String.format("Department, Province, Or State: %s\n", addressHistory.getProvince()));
            sb.append(String.format("Date From: %s\n", addressHistory.getStartDate()));
            sb.append(String.format("Date To: %s\n", addressHistory.getEndDate()));
            sb.append("\n");
        }
        SupplementB supplementB = new SupplementB();
        supplementB.setAlienNumber(applicant.getAlienNumber());
        supplementB.setApplicantName(applicant.getFirstName() + getMiddleName(applicant.getMiddleName()) + applicant.getLastName());
        supplementB.setPart("Part A.III");
        supplementB.setQuestion(question);
        supplementB.setAnswer(sb.toString());

        supplementBS.add(supplementB);
    }

    public com.quick.immi.ai.entity.asylum.i589.Applicant covert(com.quick.immi.ai.entity.asylum.business.Applicant applicant){
        applicant = NormalizeUtils.formalize(applicant);
        if(!applicant.isFluentEnglishYesCheckbox()){
            applicant.setFluentEnglishNoCheckbox(true);
        }
        com.quick.immi.ai.entity.asylum.i589.Applicant targetApplicant = new com.quick.immi.ai.entity.asylum.i589.Applicant();
        CopyUtils.copy(applicant, targetApplicant, checkboxProperties, "applicant");
        if(applicant.getEntryRecords() != null && !applicant.getEntryRecords().isEmpty()){
            List<com.quick.immi.ai.entity.asylum.i589.EntryRecord> entryRecords = new ArrayList<>();
            int i = 0;
            for(; i < 3 && i < applicant.getEntryRecords().size(); i++){
                com.quick.immi.ai.entity.asylum.i589.EntryRecord entryRecord = new com.quick.immi.ai.entity.asylum.i589.EntryRecord();
                EntryRecord record = applicant.getEntryRecords().get(i);
                String date = record.getDate();
                entryRecord.setDate(StringUtils.isNullOrEmpty(date) ? "N/A" : date);
                entryRecord.setPlace(getPlace(record.getCity(),  record.getState()));
                entryRecord.setStatus(StringUtils.isNullOrEmpty(record.getStatus()) ? "N/A" : record.getStatus());
                entryRecords.add(entryRecord);
            }
            targetApplicant.setEntryRecords(entryRecords);
            addRemaningEntryRecordsToSupplementB(applicant.getEntryRecords(),
                    i, "Part A.I.", "19.c");
        }

        String previousUsedName = getPreviousUsedName(applicant.getNameEntitiesUsedBefore());
        if(previousUsedName != null){
            targetApplicant.setNamesUsedBefore(previousUsedName);
        }
        return targetApplicant;
    }

    private String getPreviousUsedName(List<NameEntity> nameEntities){
        if(nameEntities != null && !nameEntities.isEmpty()){
            StringBuffer sb = new StringBuffer();
            for(NameEntity nameEntity : nameEntities){
                sb.append(nameEntity.getFirstName() + getMiddleName(nameEntity.getMiddleName()) + nameEntity.getLastName());
            }
            return sb.toString();
        }

        return null;
    }

    private String getPlace(String city, String state){
        return StringUtils.isNullOrEmpty(city) ? "N/A" : (city + ", " + state);
    }

    private void addRemaningEntryRecordsToSupplementB(List<EntryRecord> entryRecords, int index, String part, String question){
        if(index >= entryRecords.size()){
            return;
        }

        StringBuffer sb = new StringBuffer();
        for(int i = index; i < entryRecords.size(); i++){
            EntryRecord entryRecord = entryRecords.get(i);
            sb.append(String.format("Date: %s\n", entryRecord.getDate()));
            sb.append(String.format("Place: %s\n", entryRecord.getCity() + ", " + entryRecord.getState()));
            sb.append(String.format("Status: %s\n", entryRecord.getStatus()));
            sb.append("-----------------------------------------------------\n");
        }
        SupplementB supplementB = new SupplementB();
        supplementB.setAlienNumber(applicant.getAlienNumber());
        supplementB.setApplicantName(applicant.getFirstName() + getMiddleName(applicant.getMiddleName()) + applicant.getLastName());
        supplementB.setPart(part);
        supplementB.setQuestion(question);
        supplementB.setAnswer(sb.toString());

        supplementBS.add(supplementB);
    }

    public com.quick.immi.ai.entity.asylum.i589.Spouse covertSpouse(com.quick.immi.ai.entity.asylum.business.Spouse spouse){
        if(spouse == null){
            spouse = new Spouse();
        }
        com.quick.immi.ai.entity.asylum.i589.Spouse targetSpouse = new com.quick.immi.ai.entity.asylum.i589.Spouse();
        CopyUtils.copy(spouse, targetSpouse, checkboxProperties, "spouse");
        return targetSpouse;
    }
}
