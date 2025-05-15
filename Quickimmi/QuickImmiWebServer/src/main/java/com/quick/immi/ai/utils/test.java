/* (C) 2024 */
package com.quick.immi.ai.utils;

import com.google.gson.Gson;
import com.quick.immi.ai.entity.asylum.AsylumCaseProfile;

public class test {
  public static void main(String[] args) {
    String a = null;
    AsylumCaseProfile profile = new Gson().fromJson(a, AsylumCaseProfile.class);
    System.out.println(profile);
  }
}
