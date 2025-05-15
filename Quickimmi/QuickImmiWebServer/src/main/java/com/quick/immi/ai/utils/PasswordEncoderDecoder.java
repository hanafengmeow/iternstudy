/* (C) 2024 */
package com.quick.immi.ai.utils;

import java.util.Base64;

public class PasswordEncoderDecoder {

  public static String encodePassword(String password) {
    // Encoding password using Base64
    byte[] encodedBytes = Base64.getEncoder().encode(password.getBytes());
    return new String(encodedBytes);
  }

  public static String decodePassword(String encodedPassword) {
    // Decoding password using Base64
    byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword.getBytes());
    return new String(decodedBytes);
  }
}
