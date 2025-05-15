package com.quick.immi.ai.entity.asylum.business;

import lombok.Data;

import java.util.List;

@Data
public class Family {
    private Spouse spouse;
    private List<Child> children;
    private FamilyMember mother;
    private FamilyMember father;
    private List<FamilyMember> siblings;
}
