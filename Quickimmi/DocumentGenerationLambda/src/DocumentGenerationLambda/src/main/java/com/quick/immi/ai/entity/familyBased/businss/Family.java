package com.quick.immi.ai.entity.familyBased.businss;

import lombok.Data;

import java.util.List;

@Data
public class Family {
    // Part 4 - Information About Your Parents
    private Parent mother;
    private Parent father;
    // Part 6 - Information About Your Children
    private String totalNumberOfChildren;
    private List<Child> children;
}
