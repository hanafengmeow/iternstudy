package com.quick.immi.ai.converter.asylum;

import com.google.gson.Gson;

import com.quick.immi.ai.entity.ApplicationCase;
import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.G28FormMetadata;
import com.quick.immi.ai.entity.Identify;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.asylum.business.Applicant;
import com.quick.immi.ai.entity.asylum.business.AsylumCaseProfile;
import com.quick.immi.ai.entity.asylum.business.Child;
import com.quick.immi.ai.entity.asylum.business.Spouse;
import com.quick.immi.ai.entity.g28.*;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j;

import java.util.Properties;

import static com.quick.immi.ai.constant.Constants.DEFAULT_VALUE;

@Log4j
public class G28TableConverter {
    private Properties checkboxProperties;

    public G28TableConverter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.G28Checkbox);
    }

    public G28Table getG28Table(LawyerProfile lawyerProfile, ApplicationCase applicationCase, G28FormMetadata g28FormMetadata, Identify identify) {
        String profile = applicationCase.getProfile();
        AsylumCaseProfile asylumCaseProfile = new Gson().fromJson(profile, AsylumCaseProfile.class);
        Applicant applicant = asylumCaseProfile.getApplicant();

        G28Table g28Table = new G28Table();
        Representative representative = new Representative();
        CopyUtils.copy(lawyerProfile.getBasicInfo(), representative, checkboxProperties, "representative");
        g28Table.setRepresentative(representative);

        RepresentativeEligibility eligibility = new RepresentativeEligibility();
        CopyUtils.copy(lawyerProfile.getEligibility(), eligibility, checkboxProperties, "representativeEligibility");
        eligibility.setEligibleAttorneyCheckbox("Y");
        eligibility.setAmNotCheckbox("Y");

        g28Table.setRepresentativeEligibility(eligibility);

        if (identify == Identify.Applicant) {
            Appearance appearance = new Appearance();
            //默认
            appearance.setUscisCheckbox("Y");
            //TODO: this form name should be passed from webserver. 需要知道主表的form name---I-589, I-765, I-130
            appearance.setFormNumbers(getFormTable(g28FormMetadata));
            //for I-589... 3.b. 589 默认填写“Asylum and withholding of removal”
            //765 默认填写 “EAD Application”
            appearance.setSpecificMatterCBP(getSpecificMatterCBP(g28FormMetadata));

            appearance.setApplicantCheckbox("A");

            appearance.setClientFirstName(applicant.getFirstName());
            appearance.setClientLastName(applicant.getLastName());
            appearance.setClientMiddleName(applicant.getMiddleName());
            String clientDaytimeTelephoneNumber = applicant.getTelePhoneAreaCode() + applicant.getTelePhoneNumber();
            appearance.setClientDaytimeTelephoneNumber(getDigits(clientDaytimeTelephoneNumber));
            appearance.setClientEMail(applicationCase.getEmail());
            appearance.setClientStreetNumberName(applicant.getStreetNumberAndName());
            if (applicant.getAptNumber() != null && !"N/A".equals(applicant.getAptNumber())) {
                appearance.setAptCheckbox(" APT ");
                appearance.setClientAptNumber(applicant.getAptNumber());
            }
            appearance.setClientCity(applicant.getCity());
            appearance.setStateDropdown(applicant.getState());
            appearance.setClientZipCode(applicant.getZipCode());
            appearance.setClientCountry("USA");
            appearance.setClientProvince(DEFAULT_VALUE);
            appearance.setClientPostalCode(DEFAULT_VALUE);
            appearance.setClientUSCISOnlineAcctNumber(applicant.getUscisOnlineAccountNumber());
            g28Table.setAppearance(appearance);

            ClientConsent clientConsent = new ClientConsent();
            clientConsent.setNoticetoRepresentativeCheckbox("Y");
            clientConsent.setSecureIDtoRepresentativeCheckbox("Y");
            g28Table.setClientConsent(clientConsent);
            AdditionalInfo additionalInfo = new AdditionalInfo();
            additionalInfo.setFirstName(applicant.getFirstName());
            additionalInfo.setLastName(applicant.getLastName());
            additionalInfo.setMiddleName(applicant.getMiddleName());
            g28Table.setAdditionalInfo(additionalInfo);
        } else if (identify == Identify.Spouse) {
            Spouse spouse = asylumCaseProfile.getFamily().getSpouse();

            Appearance appearance = new Appearance();
            //默认
            appearance.setUscisCheckbox("Y");
            //TODO: this form name should be passed from webserver. 需要知道主表的form name---I-589, I-765, I-130
            appearance.setFormNumbers(getFormTable(g28FormMetadata));
            //for I-589... 3.b. 589 默认填写“Asylum and withholding of removal”
            //765 默认填写 “EAD Application”
            appearance.setSpecificMatterCBP(getSpecificMatterCBP(g28FormMetadata));

            appearance.setApplicantCheckbox("A");

            appearance.setClientFirstName(spouse.getFirstName());
            appearance.setClientLastName(spouse.getLastName());
            appearance.setClientMiddleName(spouse.getMiddleName());
//            String clientDaytimeTelephoneNumber = applicant.getTelePhoneAreaCode() + applicant.getTelePhoneNumber();
//            appearance.setClientDaytimeTelephoneNumber(getDigits(clientDaytimeTelephoneNumber));
//            appearance.setClientEMail(applicationCase.getEmail());
            appearance.setClientStreetNumberName(applicant.getStreetNumberAndName());
            if (applicant.getAptNumber() != null && !"N/A".equals(applicant.getAptNumber())) {
                appearance.setAptCheckbox(" APT ");
                appearance.setClientAptNumber(applicant.getAptNumber());
            }
            appearance.setClientCity(applicant.getCity());
            appearance.setStateDropdown(applicant.getState());
            appearance.setClientZipCode(applicant.getZipCode());
            appearance.setClientCountry("USA");
            appearance.setClientProvince(DEFAULT_VALUE);
            appearance.setClientPostalCode(DEFAULT_VALUE);
//            appearance.setClientUSCISOnlineAcctNumber(applicant.getUscisOnlineAccountNumber());
            g28Table.setAppearance(appearance);

            ClientConsent clientConsent = new ClientConsent();
            clientConsent.setNoticetoRepresentativeCheckbox("Y");
            clientConsent.setSecureIDtoRepresentativeCheckbox("Y");
            g28Table.setClientConsent(clientConsent);
            AdditionalInfo additionalInfo = new AdditionalInfo();
            additionalInfo.setFirstName(spouse.getFirstName());
            additionalInfo.setLastName(spouse.getLastName());
            additionalInfo.setMiddleName(spouse.getMiddleName());
            g28Table.setAdditionalInfo(additionalInfo);
        } else if (identify.getValue().startsWith("child")) {
            List<Child> children = asylumCaseProfile.getFamily().getChildren();
            int index = Integer.parseInt(identify.getValue().substring(6)) - 1;
            Child child = children.get(index);
            Appearance appearance = new Appearance();
            //默认
            appearance.setUscisCheckbox("Y");
            //TODO: this form name should be passed from webserver. 需要知道主表的form name---I-589, I-765, I-130
            appearance.setFormNumbers(getFormTable(g28FormMetadata));
            //for I-589... 3.b. 589 默认填写“Asylum and withholding of removal”
            //765 默认填写 “EAD Application”
            appearance.setSpecificMatterCBP(getSpecificMatterCBP(g28FormMetadata));

            appearance.setApplicantCheckbox("A");

            appearance.setClientFirstName(child.getFirstName());
            appearance.setClientLastName(child.getLastName());
            appearance.setClientMiddleName(child.getMiddleName());
//            String clientDaytimeTelephoneNumber = applicant.getTelePhoneAreaCode() + applicant.getTelePhoneNumber();
//            appearance.setClientDaytimeTelephoneNumber(getDigits(clientDaytimeTelephoneNumber));
//            appearance.setClientEMail(applicationCase.getEmail());
            appearance.setClientStreetNumberName(applicant.getStreetNumberAndName());
            if (applicant.getAptNumber() != null && !"N/A".equals(applicant.getAptNumber())) {
                appearance.setAptCheckbox(" APT ");
                appearance.setClientAptNumber(applicant.getAptNumber());
            }
            appearance.setClientCity(applicant.getCity());
            appearance.setStateDropdown(applicant.getState());
            appearance.setClientZipCode(applicant.getZipCode());
            appearance.setClientCountry("USA");
            appearance.setClientProvince(DEFAULT_VALUE);
            appearance.setClientPostalCode(DEFAULT_VALUE);
//            appearance.setClientUSCISOnlineAcctNumber(applicant.getUscisOnlineAccountNumber());
            g28Table.setAppearance(appearance);

            ClientConsent clientConsent = new ClientConsent();
            clientConsent.setNoticetoRepresentativeCheckbox("Y");
            clientConsent.setSecureIDtoRepresentativeCheckbox("Y");
            g28Table.setClientConsent(clientConsent);
            AdditionalInfo additionalInfo = new AdditionalInfo();
            additionalInfo.setFirstName(child.getFirstName());
            additionalInfo.setLastName(child.getLastName());
            additionalInfo.setMiddleName(child.getMiddleName());
            g28Table.setAdditionalInfo(additionalInfo);
        }

        return g28Table;
    }

    private String getFormTable(G28FormMetadata g28FormMetadata){
        if(g28FormMetadata != null){
            return g28FormMetadata.getMainForm();
        } else {
            return FormMapping.I589.getName();
        }
    }

    //TODO: remove g28FormMetadata null condition
    private String getSpecificMatterCBP(G28FormMetadata g28FormMetadata){
        if(g28FormMetadata == null || FormMapping.I589.getName().equalsIgnoreCase(g28FormMetadata.getMainForm())){
            return "Asylum and withholding of removal";
        } else if(g28FormMetadata != null && FormMapping.I765.getName().equalsIgnoreCase(g28FormMetadata.getMainForm())){
            return "EAD Application";
        } else {
            return DEFAULT_VALUE;
        }
    }

    private String getDigits(String phoneNumer){
        if(phoneNumer == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(char ch : phoneNumer.toCharArray()){
            if(Character.isDigit(ch)){
                sb.append(ch);
            }
        }

        return sb.toString();
    }
 }
