package com.quick.immi.ai.entity.employmentBased.business;

import com.quick.immi.ai.entity.common.NameEntity;
import com.quick.immi.ai.entity.familyBased.businss.Biographic;
import lombok.Data;

import java.util.List;

@Data
public class Petitioner {

   private String isPersonOrOrganization;

   //i-140 part1 1
   private NameEntity name;

   //i-140 part1
   private Organization organization;


   private Address mailingAddress;

   //i-485 part1 9
   private String uscisOnlineAccountNumber;

   //i485 part1 19
   private String ssn;

}
