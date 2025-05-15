package com.quick.immi.ai.entity.i485;

import lombok.Data;
import java.util.List;

@Data
public class Supplement {
    private String lastName;
    private String firstName;
    private String middleName;

    private List<AdditionalRecord> additionalRecords;
}
