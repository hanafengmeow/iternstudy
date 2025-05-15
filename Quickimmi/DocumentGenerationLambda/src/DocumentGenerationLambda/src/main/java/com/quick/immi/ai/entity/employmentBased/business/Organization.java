/* (C) 2024 */
package com.quick.immi.ai.entity.employmentBased.business;

import lombok.Data;

@Data
public class Organization{

  private String natureOfGroup;
  private String orgName;
  private String natureOfInvolvement;
  private String sourceOfFinancialSupport;

  private String employerIdentificationNumber;

  //  a nonprofit organized as tax exempt?
  private String nonprofitCheckboxYes;
  private String nonprofitCheckboxNo;

 // employ 25 or fewer full-time equivalent employees
  private String employFewerThan25CheckboxYes;
  private String employFewerThan25CheckboxNo;


    private Address address;

}
