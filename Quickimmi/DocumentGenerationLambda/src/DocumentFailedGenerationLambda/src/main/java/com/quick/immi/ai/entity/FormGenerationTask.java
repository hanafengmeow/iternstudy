package com.quick.immi.ai.entity;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormGenerationTask {
    private Long id;
    private Long documentId;
    private String type;
    private Long caseId;
    private Integer userId;
    private Integer lawyerId;
    private String formName;
    private String documentName;
    private String metadata;
    private String status;
    private String s3Location;
    private Long createdAt;
    private Long updatedAt;
}
