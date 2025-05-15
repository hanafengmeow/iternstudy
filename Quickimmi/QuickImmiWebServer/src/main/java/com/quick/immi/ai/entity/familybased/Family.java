/* (C) 2024 */
package com.quick.immi.ai.entity.familybased;

import java.util.List;
import lombok.Data;

@Data
public class Family {
  // Part 4 - Information About Your Parents
  private Parent father;
  private Parent mother;
  // Part 6 - Information About Your Children
  private String totalNumberOfChildren;
  private List<Child> children;
}
