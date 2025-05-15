package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.i130.I130Table;
import com.quick.immi.ai.entity.familyBased.i130.LastArrivalInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


import java.lang.reflect.Method;

class I130ConverterTest {

    I130Converter i130Converter = new I130Converter();
    I130Table i130Table = i130Converter.getI130Table(new LawyerProfile(), new FamilyBasedCaseProfile());

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    public void testConvertLastArrivalInfo() throws Exception {
        com.quick.immi.ai.entity.familyBased.businss.LastArrivalInformation source = new com.quick.immi.ai.entity.familyBased.businss.LastArrivalInformation();
        source.setDateOfArrival("2024-01-01");
        source.setBeneficiaryEverInUSYesCheckbox(true);
        source.setBeneficiaryEverInUSNoCheckbox(false);
        source.setArrivedAsAdmission("H1B");

        Method privateMethod = I130Converter.class.getDeclaredMethod("convertLastArrivalInfo", com.quick.immi.ai.entity.familyBased.businss.LastArrivalInformation.class);
        privateMethod.setAccessible(true);

        LastArrivalInformation result = (LastArrivalInformation) privateMethod.invoke(i130Converter, source);

        Assertions.assertNotNull(result, "Result should not be null");
        Assertions.assertEquals("2024-01-01", result.getDateOfArrival(), "Arrival Date should be copied correctly");
        System.out.println(result.getBeneficiaryEverInUSYesCheckbox());
        Assertions.assertEquals("H1B", result.getArrivedAsAdmission(), "Visa Type should be copied correctly");
    }

}