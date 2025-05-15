package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.FamilyBasedCaseProfile;
import com.quick.immi.ai.entity.familyBased.i130.I130Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class I131ConverterTest {


    @Test
    void test() {
        I131Converter i131Converter = new I131Converter();
        i131Converter.getI131Table(new LawyerProfile(), new FamilyBasedCaseProfile());
    }

    @Test
    void test_() {
        I131Converter i131Converter = new I131Converter();
        i131Converter.convertPreparer(new LawyerProfile());
    }
}