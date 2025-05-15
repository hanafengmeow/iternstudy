package com.quick.immi.ai.entity.familyBased.i130a;
import lombok.Data;
import java.util.List;

@Data
public class Beneficiary {
    //106
    private String alienNumber;
    //137
    private String uSCISOnlineAccountNumber;
    //134
    private String lastName;
    //135
    private String firstName;
    //136
    private String middleName;
    private List<Address> addressHistories;
    //148
    private Address outsideUsAddress;

    private Parent parent1;
    private Parent parent2;
    private List<EmploymentHistory> employmentHistories;
    //201
    private EmploymentHistory mostRecentOutSideUsEmploymentHistory5YearsAgo;
}
