package com.quick.immi.ai.entity.asylum;

import com.quick.immi.ai.converter.asylum.I589TableConverter;
import com.quick.immi.ai.entity.asylum.business.AddressHistory;
import com.quick.immi.ai.entity.asylum.i589.Applicant;
import com.quick.immi.ai.entity.asylum.i589.Child;
import com.quick.immi.ai.entity.asylum.i589.Spouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class I589TableConverterTest {

    private I589TableConverter i589TableConverter;


    @BeforeEach
    void setUp() throws Exception {
        i589TableConverter = new I589TableConverter();
    }


    @Test
    void testSpouseCovert() throws Exception {
        Spouse spouse = i589TableConverter.covertSpouse(null);
        System.out.println(spouse);
    }

    @Test
    void testGetPhoneNumber() {
        String dayPhone = "6197326858";
        String[] phoneNumber = i589TableConverter.getPhoneNumber(dayPhone);

        System.out.println(phoneNumber[0]);
        System.out.println(phoneNumber[1]);
    }

    @Test
    void testAddressCovert() throws Exception {
        List<AddressHistory> addressHistoriesBefore = new ArrayList<>();
        AddressHistory addressHistory1 = AddressHistory.builder()
                .numberAndStreet("102 Room unit Building 9 Yihan AI xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .city("test City")
                .province("jiangsu")
                .country("country")
                .startDate("2022-01-01")
                .endDate("2022-01-01")
                .build();
        AddressHistory addressHistory2 = AddressHistory.builder()
                .numberAndStreet("XXXXXXXX102 Room unit Building 9 Yihan AI xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .city("test City")
                .province("jiangsu")
                .country("country")
                .startDate("2022-01-01")
                .endDate("2022-01-01")
                .build();

        addressHistoriesBefore.add(addressHistory1);
        addressHistoriesBefore.add(addressHistory2);
//        List<com.quick.immi.ai.entity.asylum.i589.Address> covert = i589TableConverter.covert(addressHistoriesBefore, "", 2);
//        System.out.println(covert);
    }


    @Test
    void testCovertAddressHistory() {
        List<com.quick.immi.ai.entity.asylum.i589.AddressHistory> addressHistories = i589TableConverter.covert(null, "Q1", 2);
        System.out.println(addressHistories);
    }

//    @Test
//    void testTrimContent() {
//        String s = i589TableConverter.trimContent("During the COVID-19 pandemic in early 2020, between January and February, my family resided in a two-bedroom apartment in Yujingyuan, Zhengzhou, Henan. In the apartment lived four individuals including my parents and younger brother. Both of my parents work in the construction industry as subcontractors. With the outbreak of COVID-19 at the end of 2019, and the subsequent lockdown in Hubei province, the restrictions quickly extended to Henan. In January 2020, we were confined to Yujingyuan, where only entry was permitted. On February 10, 2020, around 2 p.m., my father fell seriously ill due to a dizzy spell, necessitating immediate medical attention. When I attempted to take him to the hospital, we were intercepted by security guards stationed at the entrance of the area. These guards, aged around 40 and 50, were wearing green military-style coats. Despite explaining our urgency to seek medical help, they adamantly prohibited our exit, citing concerns about maintaining order within the compound. A heated argument ensued, leading to a physical altercation between myself and the guards. Subsequently, the police were called, and three officers arrived at the scene wearing white masks and drove two police vehicles – a van and a sedan. Misrepresented by the security guards, I was unjustly accused of trespassing and assault. Although I insisted on my innocence, my plea fell on deaf ears. Consequently, I was taken to the Huaihe Road Police Station in Zhengzhou, while my father, feeling unwell, returned home. En route to the police station, I implored the officers to allow me to take my family to the hospital, which was disregarded as I was instructed to remain silent. At the station, I was coerced to provide a statement regarding the alleged assault, despite my prolonged efforts to clarify the misunderstanding. Frustrated by the lack of due process, I raised my voice in protest, criticizing the officers for their biased conduct. A particular officer, approximately 30 years old and towering at around 1.8 meters, responded aggressively to my remarks, physically manhandling me and forcibly placing me in a cell. For over twenty days, I was detained in solitary confinement, enduring mistreatment that included physical assault and threats of legal repercussions if I persisted in demanding justice. Throughout my captivity, my parents attempted to visit me on multiple occasions, only to be consistently denied access.", 12, "", "");
//        System.out.println(s);
//    }

    @Test
    void convertApplicant() {
        Applicant covert = i589TableConverter.covert(null);
        System.out.println(covert);
    }

    @Test
    void covertChildren() {
        List<Child> children = i589TableConverter.covertChildren(null);
        System.out.println(children);
    }

}