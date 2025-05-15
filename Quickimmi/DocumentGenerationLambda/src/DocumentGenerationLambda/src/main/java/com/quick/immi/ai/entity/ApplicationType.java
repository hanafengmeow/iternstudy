package com.quick.immi.ai.entity;

public enum ApplicationType {
    Asylum("asylum");
    private String type;

    ApplicationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
