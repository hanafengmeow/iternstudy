package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefilingInstructions {
    // Section A1 - Are you seeking to utilize the filing date from a previously submitted Application for Alien Employment Certification (ETA 750)?
    private String seekingPreviousFilingDate;
    
    // Section A1-A - If Yes, enter the previous filing date
    private String previousFilingDate;
    
    // Section A1-B - Indicate the previous SWA or local office case number OR if not available, specify the state where the case was originally filed
    private String previousSWAOrCaseNumber;
}
