package com.quick.immi.ai.entity;

public enum Identify {
    Applicant("applicant"),
    Spouse("spouse"),
    Child_1("child_1"),
    Child_2("child_2"),
    Child_3("child_3"),
    Child_4("child_4"),
    Child_5("child_5"),
    Child_6("child_6"),
    Child_7("child_7"),
    Child_8("child_8"),
    Child_9("child_9"),
    Child_10("child_10"),
    Child_11("child_11"),
    Child_12("child_12"),
    Child_13("child_13"),
    Beneficiary("beneficiary"),
    Petitioner("petitioner"),
    JointSponsor("joint_sponsor");
    
    private String value;

    Identify(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Identify fromValue(String value) {
        if(value == null){
            return Applicant;
        }

        for (Identify identify : Identify.values()) {
            if (identify.getValue().equals(value)) {
                return identify;
            }
        }
        throw new IllegalArgumentException("Invalid Identify value: " + value);
    }
}
