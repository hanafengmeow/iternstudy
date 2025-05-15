/* (C) 2024 */
package com.quick.immi.ai.entity.asylum;

import java.util.List;
import lombok.Data;

@Data
public class YourSignature {
  private String name;
  private String nameInNativeAlphabet;

  // 2
  private boolean familyMemberAssistYesCheckbox;
  // 1
  private boolean familyMemberAssistNoCheckbox;

  private List<Relationship> members;
  // Y
  private boolean otherPeopleAssistYesCheckbox;
  // N
  private boolean otherPeopleAssistNoCheckbox;

  // Y
  private boolean providePeopleCounselYesCheckbox;
  // N
  private boolean providePeopleCounselNoCheckbox;

  private String signature;
  private String date;
}
