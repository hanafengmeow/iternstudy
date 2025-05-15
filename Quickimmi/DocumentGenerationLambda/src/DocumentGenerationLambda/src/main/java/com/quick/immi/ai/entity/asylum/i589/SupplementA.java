package com.quick.immi.ai.entity.asylum.i589;

import lombok.Data;

import java.util.List;

@Data
public class SupplementA {
    //398
    private String alienNumber;
    //399
    private String date;
    //400
    private String applicantName;
    //401
    private String applicantSignature;

    private List<Child> children;
}
