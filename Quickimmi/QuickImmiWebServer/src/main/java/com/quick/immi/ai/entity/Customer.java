/* (C) 2024 */
package com.quick.immi.ai.entity;

import lombok.*;

@Data
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
  private Integer id;
  private String username;
  private String email;
  private String phoneNumber;
  private String cognitoUsername;
  private Integer status;
  //    profile
  private String firstName;
  private String lastName;
  private String middleName;
  // in the format of mm/dd/yy
  private String birthDate;
  private String nationality;
  // Male or Female
  private String gender;
  private String birthPlace;
  // json string of address
  private String address;

  // payment
  private String stripeCustomerId;
  private String stripeAccountId;
  // default payment id
  private String stripePaymentMethodId;

  private Long lastLoginAt;
  private Long createdAt;
  private Long updatedAt;
  // overried the getter/setter method for enum property
}
