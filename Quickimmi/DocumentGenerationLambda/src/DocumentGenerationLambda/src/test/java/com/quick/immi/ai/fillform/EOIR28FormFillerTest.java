package com.quick.immi.ai.fillform;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.eoir28.*;
import com.quick.immi.ai.entity.g28.G28Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class EOIR28FormFillerTest {
    private EOIR28FormFiller formFiller;
    private EOIR28Table g28Table;

    @TempDir
    private Path tempDir; // JUnit Jupiter provides this temporary directory
//
//    @Test
//    void testFillDocument() throws Exception {
//        EOIR28Table eoir28Table = new EOIR28Table();
//
//        PartyInformation partyInformation = PartyInformation.builder()
//                .firstName("Dandan")
//                .middleName("He")
//                .lastName("Test LastName")
//                .address1("test address")
//                .address2("address2")
//                .city("Bothell")
//                .state("WA")
//                .zip("98011")
//                .alienNumber("1131231")
//                .allProceedingsCheckbox("All")
//                .build();
//
//        eoir28Table.setPartyInformation(partyInformation);
//
//
//        Representation representation = Representation.builder()
//                .attorneyCheckbox("Attorney")
//                .courtName("Supreme Court of California")
//                .barNumber("Tst Bar Number")
//                .appearanceAttorneyCheckbox("0")
//                .build();
//
//        AttorneyInfo attorneyInfo = AttorneyInfo.builder()
//                .firstName("Jun")
//                .lastName("Ma")
//                .address1("test address")
//                .city("Bothell")
//                .state("WA")
//                .zip("98011")
//                .telephone("test phone")
//                .lawFirm("test lawer firm")
//                .email("junma@gmail.com")
//                .build();
//        TypeOfAppearance typeOfAppearance = new TypeOfAppearance();
//        typeOfAppearance.setPrimaryAttorneyCheckbox("Primary");
//        typeOfAppearance.setProBonoRepresentationDropdown("No");
//
//
//        ProofOfService proofOfService = new ProofOfService();
//        proofOfService.setAttorneyName("Xinxin Dan");
//        proofOfService.setElectronicServiceCheckbox("Yes");
//
//        eoir28Table.setProofOfService(proofOfService);
//        eoir28Table.setAttorneyInfo(attorneyInfo);
//        eoir28Table.setRepresentation(representation);
//        eoir28Table.setTypeOfAppearance(typeOfAppearance);
//
//        FormGenerationTask task = FormGenerationTask.builder()
//                .id(12l)
//                .caseId(31l)
//                .userId(1)
//                .status("0")
//                .build();
//
//
//        formFiller = new EOIR28FormFiller(eoir28Table, task);
//        formFiller.init();
//        formFiller.fillDocument();
//        formFiller.saveLocal();
//    }
}