package com.quick.immi.ai.entity.employmentBased.eta9089;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ETA9089Table {
    // Section A - Refiling Instructions
    private RefilingInstructions refilingInstructions;
    
    // Section B - Schedule A or Sheepherder Information
    private ScheduleASheepherder scheduleASheepherder;
    
    // Section C - Employer Information
    private Employer employer;
    
    // Section D - Employer Contact Information
    private EmployerContact employerContact;
    
    // Section E - Agent or Attorney Information
    private AgentAttorney agentAttorney;
    
    // Section F - Prevailing Wage Information
    private PrevailingWage prevailingWage;
    
    // Section G - Wage Offer Information
    private WageOffer wageOffer;
    
    // Section H - Job Opportunity Information
    private JobOpportunity jobOpportunity;
    
    // Section I - Recruitment Information
    private Recruitment recruitment;
    
    // Section J - Alien Information
    private AlienInformation alienInformation;
    
    // Section K - Alien Work Experience
    private AlienWorkExperience alienWorkExperience;
    
    // Section L - Alien Declaration
    private AlienDeclaration alienDeclaration;
    
    // Section M - Declaration of Preparer
    private PreparerDeclaration preparerDeclaration;
    
    // Section N - Employer Declaration
    private EmployerDeclaration employerDeclaration;

    private Addendum addendum;
}
