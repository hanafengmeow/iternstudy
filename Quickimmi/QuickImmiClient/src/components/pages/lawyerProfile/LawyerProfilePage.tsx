import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Button, message, Select } from "antd";
import { updateLawyerInfoApi } from "../../../api/userAPI";
import { useAppSelector } from "../../../app/hooks";
import { QText } from "../../common/Fonts";
import { Loading } from "../../common/Loading";
import { QTextBox } from "../../form/fields/Controls";
import {
  LawyerBasicInfo,
  LawyerEligibility,
  LawyerInfo,
  LawyerProfile,
  UpdateLawyerRequest,
} from "../../../model/apiModels";
import "./LawyerProfilePage.css";
import { getLawyerInfoApi } from "../../../api/authAPI";
import { QuestionCircleOutlined } from "@ant-design/icons";
import { Tooltip } from "antd";

export function LawyerProfilePage() {
  const { t } = useTranslation();
  const [loading, setLoading] = useState(false);
  const [isEditing, setIsEditing] = useState("");
  const accessToken = useAppSelector(state => state.auth.accessToken);
  const userId = useAppSelector(state => state.auth.userId);
  const email = useAppSelector(state => state.auth.email);
  const role = useAppSelector(state => state.auth.role);

  const defaultBasicInfo: LawyerBasicInfo = {
    uscisOnlineAccountNumber: "",
    lastName: "",
    firstName: "",
    middleName: "",
    streetNumberAndName: "",
    aptCheckbox: false,
    steCheckbox: false,
    flrCheckbox: false,
    aptSteFlrNumber: "",
    city: "",
    stateDropdown: "",
    zipCode: "",
    province: "",
    postalCode: "",
    country: "",
    daytimeTelephoneNumber: "",
    mobileTelephoneNumber: "",
    emailAddress: "",
    faxNumber: "",
    eoirNumber: "",
  };

  const defaultEligibility: LawyerEligibility = {
    eligibleAttorneyCheckbox: true,
    licensingAuthority: "",
    barNumber: "",
    amNotCheckbox: "",
    amCheckbox: false,
    nameofLawFirm: "",
    accreditedRepresentativeCheckbox: false,
    nameofRecognizedOrganization: "",
    dateofAccreditation: "",
    associateCheckbox: "",
    nameofPreviousRepresentative: "",
    lawGraduateCheckbox: "",
    nameofLawStudentOrLawGraduate: "",
  };

  const defaultLawyerProfile: LawyerProfile = {
    basicInfo: defaultBasicInfo,
    eligibility: defaultEligibility,
  };

  const defaultLawyerInfo: LawyerInfo = {
    id: 0,
    username: "",
    cognitoId: "",
    firstName: "",
    lastName: "",
    middleName: "",
    email: "",
    phoneNumber: "",
    specialization: "",
    lawFirm: "",
    profile: defaultLawyerProfile,
    experienceYears: 0,
    createdAt: Date.now(),
    updatedAt: Date.now(),
  };

  const aptSteFlrOptions = [
    { label: t("Apartment"), value: "apt" },
    { label: t("Suite"), value: "ste" },
    { label: t("Floor"), value: "flr" },
  ];

  const getAptSteFlrValue = () => {
    const basicInfo = lawyerInfo?.profile?.basicInfo;
    if (basicInfo?.aptCheckbox) return "apt";
    if (basicInfo?.steCheckbox) return "ste";
    if (basicInfo?.flrCheckbox) return "flr";
    return undefined;
  };

  const handleAptSteFlrChange = value => {
    setLawyerInfo(prevLawyerInfo => {
      const newLawyerInfo = { ...prevLawyerInfo };
      const basicInfo = newLawyerInfo.profile.basicInfo;

      // Reset all checkboxes to false
      basicInfo.aptCheckbox = false;
      basicInfo.steCheckbox = false;
      basicInfo.flrCheckbox = false;

      // Set the selected checkbox to true
      if (value === "apt") basicInfo.aptCheckbox = true;
      else if (value === "ste") basicInfo.steCheckbox = true;
      else if (value === "flr") basicInfo.flrCheckbox = true;

      return newLawyerInfo;
    });
  };

  const [lawyerInfo, setLawyerInfo] = useState(defaultLawyerInfo);

  useEffect(() => {
    fetchLawyerInfo();
  }, [accessToken, userId]);

  const fetchLawyerInfo = async () => {
    if (!accessToken || !userId || !email) {
      console.error(`Access token ${accessToken} or user id ${userId} is missing`);
      message.error("Access token or user id is missing");
      setLoading(false);
      return;
    }
    setLoading(true);
    try {
      const data = await getLawyerInfoApi(email, accessToken, role);
      setLawyerInfo(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
      console.log(lawyerInfo);
    }
  };

  if (loading) {
    return <Loading />;
  }

  const getOnChangeHandler = (keys: string[]) => (value: string) => {
    setLawyerInfo(prevLawyerInfo => {
      const newLawyerInfo = { ...prevLawyerInfo };

      keys.forEach(key => {
        const keyParts = key.split(".");
        let obj = newLawyerInfo;

        // Traverse to the correct nested object
        for (let i = 0; i < keyParts.length - 1; i++) {
          if (!obj[keyParts[i]]) {
            // Initialize the nested object if it's null or undefined
            obj[keyParts[i]] = {};
          }
          obj = obj[keyParts[i]];
        }

        // Update the value at the last part of the key
        if (keyParts[keyParts.length - 1] === "experienceYears") {
          obj[keyParts[keyParts.length - 1]] = parseInt(value);
        } else {
          obj[keyParts[keyParts.length - 1]] = value;
        }
      });

      return newLawyerInfo; // Update state
    });

    console.log(lawyerInfo);

    return value; // Return the value as a string to match the expected type
  };

  const updateLawyerInfo = async () => {
    if (!accessToken || !userId) {
      console.error(`Access token ${accessToken} or user id ${userId} is missing`);
      message.error("Access token or user id is missing");
      setLoading(false);
      return;
    }
    setLoading(true);
    try {
      const updateLawyerRequest: UpdateLawyerRequest = {
        id: lawyerInfo.id,
        firstName: lawyerInfo.firstName,
        lastName: lawyerInfo.lastName,
        middleName: lawyerInfo.middleName,
        phoneNumber: lawyerInfo.phoneNumber,
        specialization: lawyerInfo.specialization,
        lawFirm: lawyerInfo.lawFirm,
        profile: lawyerInfo.profile,
        experienceYears: lawyerInfo.experienceYears,
        status: 0,
        priority: 0,
        maxCapicity: 0,
      };
      const data = await updateLawyerInfoApi(accessToken, role, updateLawyerRequest);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
      message.success("Lawyer profile updated");
      console.log(lawyerInfo);
    }
  };

  const handleSaveClick = async () => {
    try {
      await updateLawyerInfo();
      setIsEditing(""); // Exit edit mode
    } catch (err) {
      console.error(err);
    }
  };

  const handleEditClick = group => {
    if (isEditing === group) {
      setIsEditing(""); // Exit edit mode
    } else {
      setIsEditing(group); // Enter edit mode
    }
  };

  const innerContent = (
    <div className="lawyer-profile-content">
      <div className="lawyer-profile-display-section">
        {/* Name Row */}
        <div className={`lawyer-profile-row ${isEditing && isEditing !== "name" ? "disabled" : ""}`}>
          <div className="lawyer-profile-left">
            <QText level="normal bold">{t("Name")}</QText>
            <Button type="link" className="edit-button" onClick={() => handleEditClick("name")}>
              {isEditing === "name" ? t("Cancel") : t("Edit")}
            </Button>
          </div>
          <div className="lawyer-profile-right">
            {isEditing !== "name" ? (
              <QText level="normal" color="gray">
                {[lawyerInfo.firstName, lawyerInfo.middleName, lawyerInfo.lastName].filter(name => name).join(" ") ||
                  t("LawyerProfile.NotProvided")}
              </QText>
            ) : (
              <div className="lawyer-profile-edit isEditing">
                <div>
                  <QText level="normal" color="gray" margin="margin-bottom-10">
                    {t("LawyerProfile.NameEditMessage")}
                  </QText>
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("FirstName")}
                    value={lawyerInfo.firstName ?? ""}
                    onChange={getOnChangeHandler(["firstName", "profile.basicInfo.firstName"])}
                  />
                  <QTextBox
                    placeholder={t("MiddleName")}
                    value={lawyerInfo.middleName ?? ""}
                    onChange={getOnChangeHandler(["middleName", "profile.basicInfo.middleName"])}
                  />
                  <QTextBox
                    placeholder={t("LastName")}
                    value={lawyerInfo.lastName ?? ""}
                    onChange={getOnChangeHandler(["lastName", "profile.basicInfo.lastName"])}
                  />
                </div>
                <Button type="primary" className="lawyer-profile-edit-save" onClick={handleSaveClick}>
                  {t("Save")}
                </Button>
              </div>
            )}
          </div>
        </div>

        {/* Email Group */}
        <div className={`lawyer-profile-row ${isEditing ? "disabled" : ""}`}>
          <div className="lawyer-profile-left">
            <QText level="normal bold">{t("LawyerProfile.EmailAddress")}</QText>
            <Tooltip title={t("LawyerProfile.EmailChangeTooltip")}>
              <Button type="link" className="edit-button">
                <QuestionCircleOutlined style={{ color: "var(--link-color)" }} />
              </Button>
            </Tooltip>
          </div>
          <div className="lawyer-profile-right">
            <QText level="normal" color="gray">
              {lawyerInfo.email ?? t("LawyerProfile.NotProvided")}
            </QText>
          </div>
        </div>

        {/* Phone Group */}
        <div className={`lawyer-profile-row ${isEditing && isEditing !== "phone" ? "disabled" : ""}`}>
          <div className="lawyer-profile-left">
            <QText level="normal bold">{t("phoneNumber")}</QText>
            <Button type="link" className="edit-button" onClick={() => handleEditClick("phone")}>
              {isEditing === "phone" ? t("Cancel") : t("Edit")}
            </Button>
          </div>
          <div className="lawyer-profile-right">
            {isEditing !== "phone" ? (
              <>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("LawyerProfile.MainNumber")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo.phoneNumber ?? t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("daytimeTelephoneNumber")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.profile?.basicInfo?.daytimeTelephoneNumber ?? t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("mobileTelephoneNumber")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.profile?.basicInfo?.mobileTelephoneNumber ?? t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
              </>
            ) : (
              <div className="lawyer-profile-edit isEditing">
                <div>
                  <QText level="normal" color="gray" margin="margin-bottom-10">
                    {t("LawyerProfile.PhoneEditMessage")}
                  </QText>
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("phoneNumber")}
                    value={lawyerInfo.phoneNumber ?? ""}
                    onChange={getOnChangeHandler(["phoneNumber"])}
                  />
                  <QTextBox
                    placeholder={t("daytimeTelephoneNumber")}
                    value={lawyerInfo?.profile?.basicInfo?.daytimeTelephoneNumber ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.daytimeTelephoneNumber"])}
                  />
                  <QTextBox
                    placeholder={t("mobileTelephoneNumber")}
                    value={lawyerInfo?.profile?.basicInfo?.mobileTelephoneNumber ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.mobileTelephoneNumber"])}
                  />
                </div>
                <Button type="primary" className="lawyer-profile-edit-save" onClick={handleSaveClick}>
                  {t("Save")}
                </Button>
              </div>
            )}
          </div>
        </div>

        {/* Fax Group */}
        <div className={`lawyer-profile-row ${isEditing && isEditing !== "fax" ? "disabled" : ""}`}>
          <div className="lawyer-profile-left">
            <QText level="normal bold">{t("faxNumber")}</QText>
            <Button type="link" className="edit-button" onClick={() => handleEditClick("fax")}>
              {isEditing === "fax" ? t("Cancel") : t("Edit")}
            </Button>
          </div>
          <div className="lawyer-profile-right">
            {isEditing !== "fax" ? (
              <QText level="normal" color="gray">
                {lawyerInfo?.profile?.basicInfo?.faxNumber || t("LawyerProfile.NotProvided")}
              </QText>
            ) : (
              <div className="lawyer-profile-edit isEditing">
                <div>
                  <QText level="normal" color="gray" margin="margin-bottom-10">
                    {t("LawyerProfile.FaxEditMessage")}
                  </QText>
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("faxNumber")}
                    value={lawyerInfo?.profile?.basicInfo?.faxNumber ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.faxNumber"])}
                  />
                </div>
                <Button type="primary" className="lawyer-profile-edit-save" onClick={handleSaveClick}>
                  {t("Save")}
                </Button>
              </div>
            )}
          </div>
        </div>

        {/* Address Group */}
        <div className={`lawyer-profile-row ${isEditing && isEditing !== "address" ? "disabled" : ""}`}>
          <div className="lawyer-profile-left">
            <QText level="normal bold">{t("Address")}</QText>
            <Button type="link" className="edit-button" onClick={() => handleEditClick("address")}>
              {isEditing === "address" ? t("Cancel") : t("Edit")}
            </Button>
          </div>
          <div className="lawyer-profile-right">
            {isEditing !== "address" ? (
              <>
                <QText level="normal" color="gray">
                  {lawyerInfo.lawFirm || t("LawyerProfile.NotProvided")}
                </QText>
                <QText level="normal" color="gray">
                  {`${lawyerInfo?.profile?.basicInfo?.streetNumberAndName || t("LawyerProfile.NotProvided")}, ` +
                    `${
                      (lawyerInfo?.profile?.basicInfo?.aptCheckbox ||
                        lawyerInfo?.profile?.basicInfo?.steCheckbox ||
                        lawyerInfo?.profile?.basicInfo?.flrCheckbox) &&
                      lawyerInfo?.profile?.basicInfo?.aptSteFlrNumber
                        ? ` ${
                            lawyerInfo?.profile?.basicInfo?.aptCheckbox
                              ? t("Apt")
                              : lawyerInfo?.profile?.basicInfo?.steCheckbox
                                ? t("Ste")
                                : lawyerInfo?.profile?.basicInfo?.flrCheckbox
                                  ? t("Flr")
                                  : ""
                          } ${lawyerInfo?.profile?.basicInfo?.aptSteFlrNumber}`
                        : ""
                    }, ` +
                    `${lawyerInfo?.profile?.basicInfo?.city || t("LawyerProfile.NotProvided")}, ` +
                    `${lawyerInfo?.profile?.basicInfo?.stateDropdown || t("LawyerProfile.NotProvided")}, ` +
                    `${lawyerInfo?.profile?.basicInfo?.zipCode || t("LawyerProfile.NotProvided")}`}
                </QText>
              </>
            ) : (
              <div className="lawyer-profile-edit isEditing">
                <div>
                  <QText level="normal" color="gray" margin="margin-bottom-10">
                    {t("LawyerProfile.AddressEditMessage")}
                  </QText>
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("lawFirm")}
                    value={lawyerInfo?.lawFirm ?? ""}
                    onChange={getOnChangeHandler(["lawFirm", "profile.eligibility.nameofLawFirm"])}
                  />
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("Street")}
                    value={lawyerInfo?.profile?.basicInfo?.streetNumberAndName ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.streetNumberAndName"])}
                  />
                  <Select
                    placeholder={t("Select Apt/Ste/Flr")}
                    value={getAptSteFlrValue()}
                    onChange={handleAptSteFlrChange}
                    options={aptSteFlrOptions}
                    style={{ width: 150 }}
                  />
                  <QTextBox
                    placeholder={t("ApartmentNumber")}
                    value={lawyerInfo?.profile?.basicInfo?.aptSteFlrNumber ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.aptSteFlrNumber"])}
                  />
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("City")}
                    value={lawyerInfo?.profile?.basicInfo?.city ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.city"])}
                  />
                  <QTextBox
                    placeholder={t("State")}
                    value={lawyerInfo?.profile?.basicInfo?.stateDropdown ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.stateDropdown"])}
                  />
                  <QTextBox
                    placeholder={t("ZipCode")}
                    value={lawyerInfo?.profile?.basicInfo?.zipCode ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.zipCode", "profile.basicInfo.postalCode"])}
                  />
                  <QTextBox
                    placeholder={t("Country")}
                    value={lawyerInfo?.profile?.basicInfo?.country ?? "U.S.A"}
                    onChange={getOnChangeHandler(["profile.basicInfo.country"])}
                  />
                </div>
                <Button type="primary" className="lawyer-profile-edit-save" onClick={handleSaveClick}>
                  {t("Save")}
                </Button>
              </div>
            )}
          </div>
        </div>

        {/* Account Numbers Group */}
        <div className={`lawyer-profile-row ${isEditing && isEditing !== "account" ? "disabled" : ""}`}>
          <div className="lawyer-profile-left">
            <QText level="normal bold">{t("AccountNumbers")}</QText>
            <Button type="link" className="edit-button" onClick={() => handleEditClick("account")}>
              {isEditing === "account" ? t("Cancel") : t("Edit")}
            </Button>
          </div>
          <div className="lawyer-profile-right">
            {isEditing !== "account" ? (
              <>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("UscisOnlineAccountNumber")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.profile?.basicInfo?.uscisOnlineAccountNumber || t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("eoirNumber")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.profile?.basicInfo?.eoirNumber || t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
              </>
            ) : (
              <div className="lawyer-profile-edit isEditing">
                <div>
                  <QText level="normal" color="gray" margin="margin-bottom-10">
                    {t("LawyerProfile.AccountNumbersEditMessage")}
                  </QText>
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("UscisOnlineAccountNumber")}
                    value={lawyerInfo?.profile?.basicInfo?.uscisOnlineAccountNumber ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.uscisOnlineAccountNumber"])}
                  />
                  <QTextBox
                    placeholder={t("eoirNumber")}
                    value={lawyerInfo?.profile?.basicInfo?.eoirNumber ?? ""}
                    onChange={getOnChangeHandler(["profile.basicInfo.eoirNumber"])}
                  />
                </div>
                <Button type="primary" className="lawyer-profile-edit-save" onClick={handleSaveClick}>
                  {t("Save")}
                </Button>
              </div>
            )}
          </div>
        </div>

        {/* Eligibility Group */}
        <div className={`lawyer-profile-row ${isEditing && isEditing !== "eligibility" ? "disabled" : ""}`}>
          <div className="lawyer-profile-left">
            <QText level="normal bold">{t("Eligibility")}</QText>
            <Button type="link" className="edit-button" onClick={() => handleEditClick("eligibility")}>
              {isEditing === "eligibility" ? t("Cancel") : t("Edit")}
            </Button>
          </div>
          <div className="lawyer-profile-right">
            {isEditing !== "eligibility" ? (
              <>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("experienceYears")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.experienceYears
                      ? `${lawyerInfo.experienceYears} ${lawyerInfo.experienceYears > 1 ? "years" : "year"}`
                      : t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("AttorneyStateBarNumberPlaceholder")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.profile?.eligibility?.barNumber || t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("licensingAuthority")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.profile?.eligibility?.licensingAuthority || t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
                <div className="content-row">
                  <QText level="normal" color="gray" className="content-label">
                    {t("dateofAccreditation")}:
                  </QText>
                  <QText level="normal" color="gray">
                    {lawyerInfo?.profile?.eligibility?.dateofAccreditation || t("LawyerProfile.NotProvided")}
                  </QText>
                </div>
              </>
            ) : (
              <div className="lawyer-profile-edit isEditing">
                <div>
                  <QText level="normal" color="gray" margin="margin-bottom-10">
                    {t("LawyerProfile.EligibilityEditMessage")}
                  </QText>
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("experienceYears")}
                    value={lawyerInfo?.experienceYears?.toString() ?? ""}
                    onChange={getOnChangeHandler(["experienceYears"])}
                  />
                  <QTextBox
                    placeholder={t("AttorneyStateBarNumberPlaceholder")}
                    value={lawyerInfo?.profile?.eligibility?.barNumber ?? ""}
                    onChange={getOnChangeHandler(["profile.eligibility.barNumber"])}
                  />
                </div>
                <div className="lawyer-profile-edit-input">
                  <QTextBox
                    placeholder={t("licensingAuthority")}
                    value={lawyerInfo?.profile?.eligibility?.licensingAuthority ?? ""}
                    onChange={getOnChangeHandler(["profile.eligibility.licensingAuthority"])}
                  />
                  <QTextBox
                    placeholder={t("dateofAccreditation")}
                    value={lawyerInfo?.profile?.eligibility?.dateofAccreditation ?? ""}
                    onChange={getOnChangeHandler(["profile.eligibility.dateofAccreditation"])}
                  />
                </div>
                <Button type="primary" className="lawyer-profile-edit-save" onClick={handleSaveClick}>
                  {t("Save")}
                </Button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );

  return (
    <div className="lawyer-profile-container">
      <div className="lawyer-profile-header">
        <h2>
          <QText level="large">{t("LawyerProfile.LawyerProfile")}</QText>
        </h2>
      </div>

      {innerContent}
    </div>
  );
}
