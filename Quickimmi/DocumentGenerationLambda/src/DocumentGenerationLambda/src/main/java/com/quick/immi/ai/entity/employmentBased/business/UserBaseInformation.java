/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import com.quick.immi.ai.entity.common.NameEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserBaseInformation {

  //i-485 part1 1
  private NameEntity currentLegalName;

  //i-485 part1 2
  private List<NameEntity> nameEntitiesUsedBefore;
  //i-485 part1 3
  private String birthDate;
  private List<String> otherBirthDate;

  //i-485 part1 6
  private boolean genderMaleCheckbox;
  //i-485 part1 6
  private boolean genderFemaleCheckbox;
  //i-485 part1 6
  private boolean genderAnotherCheckbox;

  //i-485 part1 7
  private String birthCity;
  private String birthState;
  private String birthCountry;

  //i-485 part1 8
  private String nationality;
  private List<String> OtherNationalities;


  //i485 part1 18
  private Address physicalAddress;

  private Address mailingAddress;

  private boolean mailingAddressSameAsPhysicalAddressYesCheckbox;
  private boolean mailingAddressSameAsPhysicalAddressNoCheckbox;

  private List<Address> addressHistory;

  //i485 part10
  private String daytimeTelephoneNumber;

  //i485 part10
  private String mobileTelephoneNumber;

  //i485 part10
  private String emailAddress;

}
