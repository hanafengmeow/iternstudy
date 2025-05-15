package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.i130a.I130aTable;
import org.junit.jupiter.api.Test;

class I130aTableConverterTest {

    @Test
    void name() {
        I130aConverter i130aTableConverter = new I130aConverter();
        I130aTable i130aTable = i130aTableConverter.getI130aTable(new LawyerProfile(), new FamilyBasedCaseProfile());
    }
}