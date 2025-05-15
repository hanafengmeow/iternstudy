package com.quick.immi.ai.entity;

public enum FormMapping {
    I829("i-829"),
    I829Checkbox("i-829-checkbox"),
    ETA9089("eta9089"),
    ETA9089Checkbox("eta9089-checkbox"),
    I140("i-140"), // New I-140 form mapping
    I140Checkbox("i-140-checkbox"), // New I-864 form mapping

    I864("i-864"), // New I-864 form mapping
    I864Checkbox("i-864-checkbox"), // New I-864 form mapping
    G28("g-28"),
    EOIR28("eoir-28"),
    EOIR28Checkbox("eoir-28-checkbox"),
    I589_SUPPLEMENT("i-589_supplement"),
    I589("i-589"),
    I765("i-765"),
    I130("i-130"),
    I130Checkbox("i-130-checkbox"),
    I130_SUPPLEMENT("i-130-supplement"),
    I485("i-485"),
    I485_SUPPLEMENT("i-485-supplement"),
    I485Checkbox("i-485-checkbox"),
    I130a("i-130a"),
    I130aCheckbox("i-130a-checkbox"),
    I130a_SUPPLEMENT("i-130a-supplement"),
    I131("i-131"),
    I131Checkbox("i-131-checkbox"),
    I589Checkbox("i-589-checkbox"),
    I765Checkbox("i-765-checkbox"),
    G28Checkbox("g-28-checkbox"),
    COT("cot"),
    CERTIFICATE_OF_TRANSLATION_FOR_PERSONAL_STATEMENT("certificate_of_translation_for_personal_statement"),
    CERTIFICATE_OF_TRANSLATION_FOR_MARRIAGE_CERTIFICATE("certificate_of_translation_for_marriage_certificate"),
    MERGE("merge");
    private String name;

    FormMapping(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FormMapping fromName(String name) {
        for (FormMapping type : FormMapping.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}
