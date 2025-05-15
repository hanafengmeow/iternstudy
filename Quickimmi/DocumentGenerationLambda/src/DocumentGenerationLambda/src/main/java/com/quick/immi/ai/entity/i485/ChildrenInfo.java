package com.quick.immi.ai.entity.i485;

import lombok.Data;

import java.util.List;

@Data
public class ChildrenInfo {
    
    private String totalNumberOfChildren;
    private List<Child> children;
}
