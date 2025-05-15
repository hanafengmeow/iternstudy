package com.quick.immi.ai.entity.asylum.i589;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I589Table {
    //1
    private String applyForWithholdingYesCheckbox;
    private Applicant applicant;
    private Spouse spouse;
    //max 4 children
    private List<Child> children;

    private Background background;
    //Part B. Information About Your Application
    private ApplicationDetails applicationDetails;

    private YourSignature signature;

    private Declaration declaration;
    //NO need for submit application phase
    private AsylumInterview asylumInterview;
    //NO need for submit application phase
    private RemovalHearing removalHearing;

    private List<SupplementA> supplementAs;

    private List<SupplementB> supplementBs;
}

