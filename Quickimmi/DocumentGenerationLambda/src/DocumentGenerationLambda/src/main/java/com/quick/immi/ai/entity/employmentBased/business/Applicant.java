package com.quick.immi.ai.entity.employmentBased.business;

import com.quick.immi.ai.entity.familyBased.businss.Biographic;
import lombok.Data;

import java.util.List;

@Data
public class Applicant {

   private Attorney attorney;

   //i-485 part1  i765-part2
   private UserBaseInformation userBaseInformation;

   //i-485 part1 4
   private String alienNumber;
   //i-485 part1 5
   private List<String> otherAlienNumber;

   //i-485 part1 9
   private String uscisOnlineAccountNumber;

   //i485 part1 10
   private LastArrivalInformation lastArrivalInformation;



   //i485 part1 19
   private String ssn;

   //i485 part2
   private ApplicationTypeFilingCategory typeFilingCategory;

   //i485 part4
   private ApplicantAdditionalInformation additionalInformation;

   //i485 part5 , part7
   private Family family;

   //i485 part6
   private MaritalInfo maritalInfo;

   //i485 part8
   private Biographic biographicInfo;


}
