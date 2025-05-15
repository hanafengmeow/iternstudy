package com.quick.immi.ai.entity.familyBased.i130a;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I130aTable {
    // Part 0 - Attorney or Representative Information
    private Attorney attorney;
    // Part 1 - Information About You
    // Part 2 - Information About Your Employment 
    // Part 3 - Information About Your Employment Outside the United States
    private Beneficiary beneficiary;
    // Part 4 - Spouse Beneficiary's Statement, Contact Information, Declaration, Certification, and Signature
    private PetitionerStatement petitionerStatement;
    // Part 5 - Interpreter's Information
    private Interpreter interpreter;
    // Part 6 - Preparer's Information
    private Preparer preparer;
    // Part 7 - Supplement
    private List<Supplement> supplements;
}

