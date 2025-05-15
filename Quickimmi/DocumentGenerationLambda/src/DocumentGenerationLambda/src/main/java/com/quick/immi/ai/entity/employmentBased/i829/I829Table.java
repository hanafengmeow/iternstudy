package com.quick.immi.ai.entity.employmentBased.i829;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I829Table {
    // To be To be completed by an Attorney or Accredited Representative (if any)
    private AttorneyOrRepresentativeInformation attorneyOrRepresentativeInformation;

    // Part 1 - Basis for Petition
    private BasisForPetition basisForPetition;
    
    // Part 2 - Information About You
    private PersonalInformation personalInformation;
    
    // Part 3 - Information About Your Current or Former Conditional Permanent Resident Spouse
    private SpouseInformation spouseInformation;
    
    // Part 4 - Information About Your Children
    private List<ChildInformation> childrenInformation;
    
    // Part 5 - Biographic Information
    private BiographicInformation biographicInformation;
    
    // Part 6 - Additional Information About the Regional Center and the New Commercial Enterprise (NCE)
    private RegionalCenterNCEInformation regionalCenterNCEInformation;
    
    // Part 7 - Information About the Job Creating Entity (JCE)
    private JobCreatingEntityInformation jobCreatingEntityInformation;
    
    // Part 8 - Information About Job Creation
    private JobCreationInformation jobCreationInformation;
    
    // Part 9 - Petitioner's Contact Information, Certification, and Signature
    private PetitionerContactCertification petitionerContactCertification;
    
    // Part 10 - Interpreter's Contact Information, Certification, and Signature
    private InterpreterContactCertification interpreterContactCertification;
    
    // Part 11 - Contact Information, Declaration, and Signature of the Person Preparing this Petition, if Other Than the Petitioner
    private PreparerContactCertification preparerContactCertification;
    
    // Part 12 - Additional Information
    private AdditionalInformation additionalInformation;
}