package com.quick.immi.ai.entity.familyBased.i130;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I130Table {
    // PART 0 - Attorney or Representative Information
    private Attorney attorney;
    // Part 1 - Relationship
    private Relationship relationship;
    //Part 2. Information About You
    private Petitioner petitioner;
    // //Part 3 - Biographic Information
    private Biographic biographic;
    // //Part 4 - Information About Beneficiary
    private Beneficiary beneficiary;
    // //Part 5 - Other Petition Information
    private PreviousPetition previousPetition;
    // // Part 6 - Petitioner's Statement
    private PetitionerStatement petitionerStatement;
    // //Part 7 - Interpreter's Contact Information, Certification, and Signature
    private Interpreter interpreter;
    // //Part 8 - Contact Information, Declaration, and Signature
    private Preparer preparer;
    // //Part 9 - Additional Information
    private List<Supplement> supplements;
}
