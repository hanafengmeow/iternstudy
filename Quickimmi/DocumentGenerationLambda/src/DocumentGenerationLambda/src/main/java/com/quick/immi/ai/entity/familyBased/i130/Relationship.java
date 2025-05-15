package com.quick.immi.ai.entity.familyBased.i130;

import lombok.Data;

@Data
public class Relationship {
    //Part 1-1
    private String petitionForSpouseCheckbox;
    //Part 1-2
    private String petitionForParentCheckbox;
    //Part 1-3
    private String petitionForSiblingCheckbox;
    //Part 1-4
    private String petitionForChildCheckbox;

    //Part 1-Q2-1
    private String petitionForChildBornInWedlockCheckbox;
    //Part 1-Q2-2
    private String petitionForStepchildAndStepparentCheckbox;
    //Part 1-Q2-3
    private String petitionForChildNotBornInWedlockCheckbox;
    //Part 1-Q2-4
    private String petitionForChildAdoptedCheckbox;

    //Part 1-Q3
    private String siblingAdoptionRelationYesCheckbox;
    private String siblingAdoptionRelationNoCheckbox;

    //Part 1-Q4
    private String gainedStatusThroughAdoptionYesCheckbox;
    private String gainedStatusThroughAdoptionNoCheckbox;
}