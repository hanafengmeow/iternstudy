package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.entity.familyBased.businss.Sponsor;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.secretsmanager.endpoints.internal.Value;

import static org.junit.jupiter.api.Assertions.*;

class I864ConverterTest {

    @Test
    void name() {
        test(null);
    }
    private void test(Sponsor.EmploymentAndIncome employmentAndIncome){
        if(employmentAndIncome == null){
            employmentAndIncome = new Sponsor.EmploymentAndIncome();
        }
        System.out.println(employmentAndIncome);
    }
}