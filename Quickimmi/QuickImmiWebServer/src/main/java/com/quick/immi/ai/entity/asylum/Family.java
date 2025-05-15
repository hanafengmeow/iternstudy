/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import java.util.List;
import lombok.Data;

@Data
public class Family {
  private Spouse spouse;
  private List<Child> children;
  private FamilyMember mother;
  private FamilyMember father;
  private List<FamilyMember> siblings;
}
