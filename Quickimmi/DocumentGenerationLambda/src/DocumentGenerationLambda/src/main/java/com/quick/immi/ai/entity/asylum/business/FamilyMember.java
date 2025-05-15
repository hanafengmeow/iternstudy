package com.quick.immi.ai.entity.asylum.business;

import lombok.Data;

@Data
public class FamilyMember {
    private String part = "Part A.III";
    private String question = "Q5";
    private String name;
    private String cityTownAndBirth;
    private String location;
    private boolean deceasedCheckbox;
}
