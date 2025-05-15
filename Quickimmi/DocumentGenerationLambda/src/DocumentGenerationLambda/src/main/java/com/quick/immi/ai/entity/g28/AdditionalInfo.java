package com.quick.immi.ai.entity.g28;

import lombok.Data;

import java.util.List;

@Data
public class AdditionalInfo {
    // Part6 - g28 number - prerun number
    // 1a - 76
    private String lastName;
    // 1b - 77
    private String firstName;
    // 1c - 78
    private String middleName;
    // 2-6 - list of forms
    private List<Information> information;
}
