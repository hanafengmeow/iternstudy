package com.quick.immi.ai.utils;

public enum FieldType {
    Text("Tx"), CheckBox("Btn"), DROP_DOWN("Ch");

    FieldType(String value) {
        this.value = value;
    }

    String value;
    public String getValue(){
        return this.value;
    }
}
