package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;

import java.util.List;

@Data
public class YourSignature {
    private String name;
    private String nameInNativeAlphabet;

    //2
    private String familyMemberAssistYesCheckbox;
    //1
    private String familyMemberAssistNoCheckbox;

    private List<Relationship> members;
    //Y
    private String otherPeopleAssistYesCheckbox;
    //N
    private String otherPeopleAssistNoCheckbox;

    //Y
    private String providePeopleCounselYesCheckbox;
    //N
    private String providePeopleCounselNoCheckbox;

    private String signature;
    private String date;
}
