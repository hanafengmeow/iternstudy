package com.quick.immi.ai.converter.common;

import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.i765.I765Table;

import java.util.Properties;

public class I765TableUtils {

    public static I765Table.Preparer convertPreparer(LawyerProfile lawyerProfile, Properties checkboxProperties){
        I765Table.Preparer preparer = new I765Table.Preparer();
        preparer.setLastName(lawyerProfile.getBasicInfo().getLastName());
        preparer.setFirstName(lawyerProfile.getBasicInfo().getFirstName());
        preparer.setOrganizationName(lawyerProfile.getEligibility().getNameofLawFirm());
        I765Table.Address address = I765Table.Address.builder()
                .streetNumberAndName(lawyerProfile.getBasicInfo().getStreetNumberAndName())
                .aptCheckbox(String.valueOf(lawyerProfile.getBasicInfo().isAptCheckbox()))
                .steCheckbox(String.valueOf(lawyerProfile.getBasicInfo().isSteCheckbox()))
                .flrCheckbox(String.valueOf(lawyerProfile.getBasicInfo().isFlrCheckbox()))
                .aptSteFlrNumber(lawyerProfile.getBasicInfo().getAptSteFlrNumber())
                .city(lawyerProfile.getBasicInfo().getCity())
                .state(lawyerProfile.getBasicInfo().getStateDropdown())
                .zipCode(lawyerProfile.getBasicInfo().getZipCode())
                .province(lawyerProfile.getBasicInfo().getProvince())
                .postalCode(lawyerProfile.getBasicInfo().getPostalCode())
                .country(lawyerProfile.getBasicInfo().getCountry())
                .build();
        preparer.setAddress(address);
        I765Table.Contact contact = new I765Table.Contact();
        contact.setDaytimePhone(lawyerProfile.getBasicInfo().getDaytimeTelephoneNumber());
        contact.setMobilePhone(lawyerProfile.getBasicInfo().getMobileTelephoneNumber());
        contact.setEmail(lawyerProfile.getBasicInfo().getEmailAddress());
        preparer.setContact(contact);
        preparer.setNotAttorneyCheckbox(checkboxProperties.getProperty("preparer.notAttorneyCheckbox"));
        preparer.setAttorneyCheckbox(checkboxProperties.getProperty("preparer.attorneyCheckbox"));
        preparer.setExtendCheckbox(checkboxProperties.getProperty("preparer.extendCheckbox"));
        preparer.setNotExtendCheckbox(checkboxProperties.getProperty("preparer.notExtendCheckbox"));
        return preparer;
    }
}
