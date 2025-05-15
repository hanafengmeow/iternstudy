package com.quick.immi.ai.utils;

import com.quick.immi.ai.entity.asylum.business.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalizeUtils {

    public static Applicant formalize(Applicant applicant) {
        if(applicant == null){
            applicant = new Applicant();
        }
        if(applicant.getEntryRecords() == null){
            applicant.setEntryRecords(new ArrayList<>());
        }
        while(applicant.getEntryRecords().size() < 3){
            applicant.getEntryRecords().add(new EntryRecord());
        }

        return applicant;
    }


    public static List<Child> formalize(List<Child> children) {
        if(children == null){
            children = new ArrayList<>();
        }
        while(children.size() < 6){
            children.add(new Child());
        }
        return children;
    }

    public static Background formalize(Background background){
        if(background == null) {
            background = new Background();
        }
        if(background.getAddressHistoriesBeforeUS() == null){
            background.setAddressHistoriesBeforeUS(new ArrayList<>());
        }
        while(background.getAddressHistoriesBeforeUS().size() < 2){
            background.getAddressHistoriesBeforeUS().add(new AddressHistory());
        }

        if(background.getUsAddressHistoriesPast5Years() == null){
            background.setUsAddressHistoriesPast5Years(new ArrayList<>());
        }
        while(background.getUsAddressHistoriesPast5Years().size() < 5){
            background.getUsAddressHistoriesPast5Years().add(new AddressHistory());
        }

        if(background.getEducationHistories() == null){
            background.setEducationHistories(new ArrayList<>());
        }

        while(background.getEducationHistories().size() < 4){
            background.getEducationHistories().add(new EducationHistory());
        }

        if(background.getEmploymentHistories() == null) {
            background.setEmploymentHistories(Arrays.asList(new EmploymentHistory(), new EmploymentHistory(), new EmploymentHistory()));
        }
        while(background.getEmploymentHistories().size() < 3){
            background.getEmploymentHistories().add(new EmploymentHistory());
        }

        return background;
    }

    //Keep it for test
    public static void main(String[] args){
        Background background = null;
        Background formalize = NormalizeUtils.formalize(background);
        System.out.println(formalize);

    }

    public static Family formalize(Family family){
        if (family == null) {
            family = new Family();
        }
        if (family.getFather() == null) {
            family.setFather(new FamilyMember());
        }
        if(family.getMother() == null){
            family.setMother(new FamilyMember());
        }
        if(family.getSpouse() == null){
            family.setSpouse(new Spouse());
        }
        if(family.getSiblings() == null){
            family.setSiblings(new ArrayList<>());
        }
        while (family.getSiblings().size() < 4){
            family.getSiblings().add(new FamilyMember());
        }

        return family;
    }
}
