package com.quick.immi.ai.entity.familyBased.i864;

import lombok.Data;

@Data
public class FamilyMember {

    //
    private String pageNumber = "2";
    //
    private String partNumber = "3";
    //
    private String itemNumber = "4-28";

    // Page 6, Questions 4.a, 9.a, 14.a, 19.a, 24a - Family Name (Last Name)
    private String lastName;

    // Page 6, Questions 4.b, 9.b, 14.b, 19.b, 24b - Given Name (First Name)
    private String firstName;

    // Page 6, Questions 4.c, 9.c, 14.c, 19.c, 24c - Middle Name
    private String middleName;

    // Page 6, Questions 5, 10, 15, 20, 25 - Relationship to Principal Immigrant
    private String relationshipToPrincipalImmigrant;

    // Page 6, Questions 6, 11, 16, 21, 26 - Date of Birth (mm/dd/yyyy)
    private String dateOfBirth;

    // Page 6, Questions 7, 12, 17, 22, 27 - Alien Registration Number (A-Number) (if any)
    private String alienNumber;

    // Page 6, Questions 8, 13, 18, 23, 28 - USCIS Online Account Number (if any)
    private String uSCISOnlineAccountNumber;
}
