package com.quick.immi.ai.entity.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NameEntity {
    private String firstName;
    private String middleName;
    private String lastName;
}
