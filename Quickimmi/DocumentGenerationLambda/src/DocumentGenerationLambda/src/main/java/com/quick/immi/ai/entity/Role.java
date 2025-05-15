package com.quick.immi.ai.entity;

public enum Role {
    APPLICANT("Applicant"), LAWYER("Lawyer"), SYSTEM("System");

    private String value;

    public String getValue() {
        return value;
    }

    Role(String role) {
        this.value = role;
    }
}
