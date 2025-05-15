package com.quick.immi.ai.entity.familyBased.i864;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation {
  // Daytime Telephone Number
  private String daytimeTelephoneNumber;

  // Mobile Telephone Number (if any)
  private String mobileTelephoneNumber;

  //  Email Address (if any)
  private String emailAddress;

}
