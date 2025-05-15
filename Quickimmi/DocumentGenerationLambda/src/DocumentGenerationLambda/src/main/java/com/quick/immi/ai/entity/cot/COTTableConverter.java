package com.quick.immi.ai.entity.cot;

import com.google.gson.Gson;

import com.quick.immi.ai.entity.ApplicationCase;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.LawyerBasicInfo;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.asylum.business.Applicant;
import com.quick.immi.ai.entity.asylum.business.AsylumCaseProfile;
import com.quick.immi.ai.entity.cot.COTTable;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import lombok.extern.log4j.Log4j;

import java.util.Properties;

@Log4j
public class COTTableConverter {

  public COTTable getCOTTable(LawyerProfile lawyerProfile, String documentName){

    LawyerBasicInfo lawyerBasicInfo = lawyerProfile.getBasicInfo();

    String fullName = lawyerBasicInfo.getFirstName() + " " + lawyerBasicInfo.getLastName();
    String email = lawyerBasicInfo.getEmailAddress();
    String phone = lawyerBasicInfo.getDaytimeTelephoneNumber();
    String areaCode = phone.substring(0, 3);
    String centralOfficeCode = phone.substring(3, 6);
    String lineNumber = phone.substring(6, 10);
    phone = String.format("(%s) %s-%s", areaCode, centralOfficeCode, lineNumber);
    String address = lawyerBasicInfo.getAptSteFlrNumber() + ", " + lawyerBasicInfo.getStreetNumberAndName()
        + ", " + lawyerBasicInfo.getCity() + ", " + lawyerBasicInfo.getStateDropdown() + ", "
        + lawyerBasicInfo.getCountry() + " " + lawyerBasicInfo.getZipCode();

    COTTable cotTable = new COTTable();
    cotTable.setFullName(fullName);
    cotTable.setEmail(email);
    cotTable.setPhone(phone);
    cotTable.setAddress(address);
    cotTable.setDocumentName(documentName);

    return cotTable;
  }
}
