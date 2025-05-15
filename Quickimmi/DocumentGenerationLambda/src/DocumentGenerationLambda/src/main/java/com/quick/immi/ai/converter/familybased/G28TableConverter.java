package com.quick.immi.ai.converter.familybased;

import com.quick.immi.ai.entity.FormMapping;
import com.quick.immi.ai.entity.G28FormMetadata;
import com.quick.immi.ai.entity.Identify;
import com.quick.immi.ai.entity.LawyerProfile;
import com.quick.immi.ai.entity.familyBased.businss.*;
import com.quick.immi.ai.entity.g28.*;
import com.quick.immi.ai.utils.CopyUtils;
import com.quick.immi.ai.utils.FormFillUtils;
import com.quick.immi.ai.utils.InstanceUtils;
import lombok.extern.log4j.Log4j;

import static com.quick.immi.ai.constant.Constants.DEFAULT_VALUE;

import java.util.Properties;


@Log4j
public class G28TableConverter {
    private Properties checkboxProperties;

    public G28TableConverter() {
        checkboxProperties = FormFillUtils.getFormMapping(FormMapping.G28Checkbox);
    }

    public G28Table getG28Table(LawyerProfile lawyerProfile, FamilyBasedCaseProfile familyBasedCaseProfile,
                                G28FormMetadata g28FormMetadata) {
        G28Table g28Table = new G28Table();

        g28Table.setRepresentative(convertRepresentative(lawyerProfile));
        g28Table.setRepresentativeEligibility(converRepresentativeEligibility(lawyerProfile));
        g28Table.setAppearance(convertAppearance(familyBasedCaseProfile, g28FormMetadata, g28FormMetadata.getIdentify()));
        g28Table.setClientConsent(convertClientConsent());
        g28Table.setRepresentativeSignature(convertRepresentativeSignature());
        g28Table.setAdditionalInfo(convertAdditionalInfo(familyBasedCaseProfile, g28FormMetadata.getIdentify()));

        return g28Table;
    }

    private Representative convertRepresentative(LawyerProfile lawyerProfile) {
        Representative representative = new Representative();
        CopyUtils.copy(lawyerProfile.getBasicInfo(), representative, checkboxProperties, "representative");

        return representative;
    }

    private RepresentativeEligibility converRepresentativeEligibility(LawyerProfile lawyerProfile) {
        RepresentativeEligibility representativeEligibility = new RepresentativeEligibility();
        CopyUtils.copy(lawyerProfile.getEligibility(), representativeEligibility, checkboxProperties, "representativeEligibility");
        representativeEligibility.setEligibleAttorneyCheckbox(checkboxProperties.getProperty("representativeEligibility.eligibleAttorneyCheckbox"));
        representativeEligibility.setAmNotCheckbox(checkboxProperties.getProperty("representativeEligibility.amNotCheckbox"));

        return representativeEligibility;
    }

    private Appearance convertAppearance(FamilyBasedCaseProfile familyBasedCaseProfile, G28FormMetadata g28FormMetadata, Identify identify) {
        Appearance appearance = new Appearance();
        appearance.setUscisCheckbox(checkboxProperties.getProperty("appearance.uscisCheckbox"));
        appearance.setFormNumbers(getFormTable(g28FormMetadata));
        appearance.setSpecificMatterCBP(getSpecificMatterCBP(g28FormMetadata));
        appearance.setApplicantCheckbox(checkboxProperties.getProperty("appearance.applicantCheckbox"));

        if (identify == Identify.Beneficiary) {
            Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();

            appearance.setClientFirstName(beneficiary.getFirstName());
            appearance.setClientLastName(beneficiary.getLastName());
            appearance.setClientMiddleName(beneficiary.getMiddleName());
            appearance.setClientDaytimeTelephoneNumber(beneficiary.getDaytimePhoneNumber());
            appearance.setClientEMail(beneficiary.getEmailAddress());
            appearance.setClientStreetNumberName(beneficiary.getMailingAddress().getStreetNumberAndName());
            if (beneficiary.getMailingAddress().isAptCheckbox()) {
                appearance.setAptCheckbox(checkboxProperties.getProperty("appearance.aptCheckbox"));
            }
            if (beneficiary.getMailingAddress().isFlrCheckbox()) {
                appearance.setAptCheckbox(checkboxProperties.getProperty("appearance.flrCheckbox"));
            }
            if (beneficiary.getMailingAddress().isSteCheckbox()) {
                appearance.setSteCheckbox(checkboxProperties.getProperty("appearance.steCheckbox"));
            }
            appearance.setClientCity(beneficiary.getMailingAddress().getCityOrTown());
            appearance.setStateDropdown(beneficiary.getMailingAddress().getState());
            appearance.setClientZipCode(beneficiary.getMailingAddress().getZipCode());
            appearance.setClientCountry("USA");
            appearance.setClientUSCISOnlineAcctNumber(beneficiary.getUSCISOnlineAccountNumber());
        } else if (identify == Identify.Petitioner) {
            Petitioner petitioner = familyBasedCaseProfile.getPetitioner();

            appearance.setClientFirstName(petitioner.getFirstName());
            appearance.setClientLastName(petitioner.getLastName());
            appearance.setClientMiddleName(petitioner.getMiddleName());
            appearance.setClientDaytimeTelephoneNumber(petitioner.getDaytimeTelephoneNumber());
            appearance.setClientEMail(petitioner.getEmailAddress());
            Address mailingAddress = petitioner.getMailingAddress();
            if(mailingAddress == null){
                mailingAddress = InstanceUtils.createInstanceWithDefaults(Address.class);
            }
            appearance.setClientStreetNumberName(mailingAddress.getStreetNumberAndName());
            if (mailingAddress.isAptCheckbox()) {
                appearance.setAptCheckbox(checkboxProperties.getProperty("appearance.aptCheckbox"));
            }
            if (mailingAddress.isFlrCheckbox()) {
                appearance.setAptCheckbox(checkboxProperties.getProperty("appearance.flrCheckbox"));
            }
            if (mailingAddress.isSteCheckbox()) {
                appearance.setSteCheckbox(checkboxProperties.getProperty("appearance.steCheckbox"));
            }
            appearance.setClientCity(mailingAddress.getCityOrTown());
            appearance.setStateDropdown(mailingAddress.getState());
            appearance.setClientZipCode(mailingAddress.getZipCode());
            appearance.setClientCountry("USA");
            appearance.setClientUSCISOnlineAcctNumber(petitioner.getUSCISOnlineAccountNumber());
        } else if (g28FormMetadata.getSponsorIndex() != null) {
            Sponsor sponsor = familyBasedCaseProfile.getSponsorList().get(g28FormMetadata.getSponsorIndex());

            appearance.setClientFirstName(sponsor.getSponsorDetails().getFirstName());
            appearance.setClientLastName(sponsor.getSponsorDetails().getLastName());
            appearance.setClientMiddleName(sponsor.getSponsorDetails().getMiddleName());
            appearance.setClientDaytimeTelephoneNumber(sponsor.getSponsorContract().getDaytimeTelephoneNumber());
            appearance.setClientEMail(sponsor.getSponsorContract().getEmailAddress());
            appearance.setClientStreetNumberName(sponsor.getSponsorDetails().getMailAddress().getStreetNumberAndName());
            if (sponsor.getSponsorDetails().getMailAddress().isAptCheckbox()) {
                appearance.setAptCheckbox(checkboxProperties.getProperty("appearance.aptCheckbox"));
            }
            if (sponsor.getSponsorDetails().getMailAddress().isFlrCheckbox()) {
                appearance.setAptCheckbox(checkboxProperties.getProperty("appearance.flrCheckbox"));
            }
            if (sponsor.getSponsorDetails().getMailAddress().isSteCheckbox()) {
                appearance.setSteCheckbox(checkboxProperties.getProperty("appearance.steCheckbox"));
            }
            appearance.setClientCity(sponsor.getSponsorDetails().getMailAddress().getCityOrTown());
            appearance.setStateDropdown(sponsor.getSponsorDetails().getMailAddress().getState());
            appearance.setClientZipCode(sponsor.getSponsorDetails().getMailAddress().getZipCode());
            appearance.setClientCountry("USA");
            appearance.setClientUSCISOnlineAcctNumber(sponsor.getSponsorDetails().getUSCISOnlineAccountNumber());
        } 

        return appearance;
    }

    private ClientConsent convertClientConsent() {
        ClientConsent clientConsent = new ClientConsent();
        clientConsent.setNoticetoRepresentativeCheckbox(checkboxProperties.getProperty("clientConsent.noticetoRepresentativeCheckbox"));
        clientConsent.setSecureIDtoRepresentativeCheckbox(checkboxProperties.getProperty("clientConsent.secureIDtoRepresentativeCheckbox"));

        return clientConsent;
    }

    private RepresentativeSignature convertRepresentativeSignature() {
        RepresentativeSignature representativeSignature = new RepresentativeSignature();

        return representativeSignature;
    }

    private AdditionalInfo convertAdditionalInfo(FamilyBasedCaseProfile familyBasedCaseProfile, Identify identify) {
        AdditionalInfo additionalInfo = new AdditionalInfo();

        if (identify == Identify.Spouse) {
            Beneficiary beneficiary = familyBasedCaseProfile.getBeneficiary();
            additionalInfo.setFirstName(beneficiary.getFirstName());
            additionalInfo.setLastName(beneficiary.getLastName());
            additionalInfo.setMiddleName(beneficiary.getMiddleName());
        }
        return additionalInfo;
    }

    private String getFormTable(G28FormMetadata g28FormMetadata){
        if(g28FormMetadata != null){
            return g28FormMetadata.getMainForm();
        } else {
            return FormMapping.I589.getName();
        }
    }

    private String getSpecificMatterCBP(G28FormMetadata g28FormMetadata){
        if(g28FormMetadata == null || FormMapping.I864.getName().equalsIgnoreCase(g28FormMetadata.getMainForm())){
            return "Application Sponsor";
        } else if(g28FormMetadata != null && FormMapping.I765.getName().equalsIgnoreCase(g28FormMetadata.getMainForm())){
            return "EAD Application";
        } else {
            return DEFAULT_VALUE;
        }
    }
 }