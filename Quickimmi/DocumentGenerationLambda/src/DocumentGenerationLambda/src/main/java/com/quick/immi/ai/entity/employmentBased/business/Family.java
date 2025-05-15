package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

import java.util.List;

@Data
public class Family {
    // i485-Part5 - Information About Your Parents
    private Parent parent1;
    private Parent parent2;
    // i485-Part7 - Information About Your Children
    private String totalNumberOfChildren;
    private List<Child> children;
}
