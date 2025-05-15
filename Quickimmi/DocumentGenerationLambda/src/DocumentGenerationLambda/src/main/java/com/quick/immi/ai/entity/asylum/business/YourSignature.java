package com.quick.immi.ai.entity.asylum.business;

import lombok.Data;

import java.util.List;

@Data
public class YourSignature {
    private String name;
    private String nameInNativeAlphabet;

    //2
    private boolean familyMemberAssistYesCheckbox;
    //1
    private boolean familyMemberAssistNoCheckbox;

    private List<Relationship> members;
    //Y
    private boolean otherPeopleAssistYesCheckbox;
    //N
    private boolean otherPeopleAssistNoCheckbox;

    //Y
    private boolean providePeopleCounselYesCheckbox;
    //N
    private boolean providePeopleCounselNoCheckbox;

    private String signature;
    private String date;

}
