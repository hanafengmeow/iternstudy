package com.quick.immi.ai.entity.employmentBased.i140;

import com.amazonaws.services.dynamodbv2.xspec.S;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.secretsmanager.endpoints.internal.Value.Str;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailAddress {

  private String inCareOfName;

  private String streetNumberAndName;

  private String aptCheckbox;

  private String steCheckbox;

  private String flrCheckbox;

  private String aptSteFlrNumber;

  private String city;

  private String state;

  private String zipCode;

  private String province;

  private String postalCode;

  private String country;

}
