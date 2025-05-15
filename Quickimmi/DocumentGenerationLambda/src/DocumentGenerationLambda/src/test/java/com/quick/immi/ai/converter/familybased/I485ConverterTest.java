package com.quick.immi.ai.converter.familybased;

import com.google.gson.Gson;
import com.quick.immi.ai.converter.familybased.I485Converter;
import com.quick.immi.ai.entity.FormGenerationTask;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.Biographic;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.businss.Attorney;

import com.quick.immi.ai.entity.i485.I485Table;
import com.quick.immi.ai.fillform.I485.I485FormFiller;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class I485TableConverterTest {
  private static final Logger logger = Logger.getLogger(I485TableConverterTest.class.getName());

  private I485Converter i485Converter;
  private I485FormFiller formFiller;
  private LawyerProfile lawyerProfile;
  private FamilyBasedCaseProfile familyBasedCaseProfile;
  private Attorney attorney;

  @BeforeEach
  void setUp() throws Exception{

    // Instantiate the I485Converter with the mocked FormFillUtils
    i485Converter = new I485Converter();
    // Set up mock data for LawyerProfile, I485CaseProfile, and Attorney
    lawyerProfile = new LawyerProfile();
    familyBasedCaseProfile = new FamilyBasedCaseProfile();

    Gson gson = new Gson();
    try (Reader reader = new InputStreamReader(
            getClass().getClassLoader().getResourceAsStream("familyBasedCase-test.json"), StandardCharsets.UTF_8)) {
      familyBasedCaseProfile = gson.fromJson(reader, FamilyBasedCaseProfile.class);
    }
    System.out.println(familyBasedCaseProfile);

    try (Reader reader = new InputStreamReader(
            getClass().getClassLoader().getResourceAsStream("lawyerProfile-test.json"), StandardCharsets.UTF_8)) {
      lawyerProfile = gson.fromJson(reader, LawyerProfile.class);
    }
//    System.out.println(lawyerProfile);

    I485Table i485Table = i485Converter.getI485Table(familyBasedCaseProfile, lawyerProfile);
    System.out.println("i485: " + i485Table.getBackground());
    FormGenerationTask task = FormGenerationTask.builder()
            .id(12l)
            .caseId(31l)
            .userId(1)
            .status("0")
            .build();
//    familyBasedCaseProfile.setBiographic(new Biographic());
//    System.out.println(familyBasedCaseProfile.getBeneficiaryEligibility());

    formFiller = new I485FormFiller(i485Table, task);
    formFiller.init();

//    attorney = new Attorney();
  }


//  @Test
//  void testGetI485Table_convertAttorney_true() throws IOException {
//    attorney = mock(Attorney.class);
//    // Set up mock attorney data
  ////    when(attorney.getG28AttachedCheckbox()).thenReturn(true);
//    when(attorney.getStateBarNumber()).thenReturn("111");
//    when(attorney.getVolagNumber()).thenReturn("222");
//    when(attorney.getUSCISOnlineAccountNumber()).thenReturn("333");
//
//    // Act: Call the converter with mocked attorney
//    I485Table i485Table = i485Converter.getI485Table(null, null, attorney);
//
//    // Assert: Verify that attorney information is correctly set in I485Table
//    assertEquals("1", i485Table.getAttorney().getG28AttachedCheckbox());
//    assertEquals("111", i485Table.getAttorney().getAttorneyStateBarNumber());
//    assertEquals("222", i485Table.getAttorney().getVolagNumber());
//    assertEquals("333", i485Table.getAttorney().getUSCISOnlineAcctNumber());
//  }

//  @Test
//  void testGetI485Table_withValidAttorneyInfo_false() {
//    // Set up mock attorney data
//    attorney = mock(Attorney.class);
//    // Set up mock attorney data
//    when(attorney.getG28AttachedCheckbox()).thenReturn(false);
//    when(attorney.getStateBarNumber()).thenReturn("111");
//    when(attorney.getVolagNumber()).thenReturn("222");
//    when(attorney.getUSCISOnlineAccountNumber()).thenReturn("333");
//
//    // Act: Call the converter with mocked attorney
//    I485Table i485TestTable = i485Converter.getI485Table(null, null, attorney);
//
//    // Assert: Verify that attorney information is correctly set in I485Table
//    assertNull(i485TestTable.getAttorney().getG28AttachedCheckbox());
//    assertEquals("111", i485TestTable.getAttorney().getAttorneyStateBarNumber());
//    assertEquals("222", i485TestTable.getAttorney().getVolagNumber());
//    assertEquals("333", i485TestTable.getAttorney().getUSCISOnlineAcctNumber());
//  }

//  @Test
//  void testGetI485Table_convertBiographic_true() throws Exception {
//    Biographic biographic = familyBasedCaseProfile.getBiographic();
//    I485Table i485TestTable = i485Converter.getI485Table(null, familyBasedCaseProfile);
//    testFillDocument();
//
//
//  }
  @Test
  void testFillDocument() throws Exception {
    System.out.println("Starting testFillDocument...");
    logger.info("Starting testFillDocument...");
    formFiller.fillDocument();
    formFiller.saveLocal();
    // formFiller.saveLocalSupplementResult();
  }
}