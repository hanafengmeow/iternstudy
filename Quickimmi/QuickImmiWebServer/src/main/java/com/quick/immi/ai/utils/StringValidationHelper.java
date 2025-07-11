/* (C) 2024 */
package com.quick.immi.ai.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidationHelper {

  public static boolean validateEmailFormat(String email) {
    boolean result = false;

    String regex = "^(.+)@(.+)$";

    Pattern pattern = Pattern.compile(regex);

    Matcher matcher = pattern.matcher(email);
    result = matcher.matches();

    return result;
  }

  public static boolean validatePhoneNumber(String phoneNumber) {
    boolean isValid = false;

    Pattern pattern = Pattern.compile("\\+\\d{3}\\d{8}");
    Matcher matcher = pattern.matcher(phoneNumber);

    if (matcher.matches()) {
      isValid = true;
    }

    return isValid;
  }

  public static boolean hasOnlyAlphanumericCharacters(String text) {
    boolean result = false;
    String regex = "^[a-zA-Z0-9- ]+$";

    Pattern pattern = Pattern.compile(regex);

    Matcher matcher = pattern.matcher(text);
    result = matcher.matches();
    return result;
  }

  public static Date convertStringToDate(String date, String format) throws ParseException {
    java.util.Date utilDate = new SimpleDateFormat(format).parse(date);
    Date sqlDate = new Date(utilDate.getTime());
    return sqlDate;
  }

  public static String convertDateToString(java.util.Date date, String format) {
    String dateString = null;

    if (null != date) {
      DateFormat df = new SimpleDateFormat(format);
      dateString = df.format(date);
    }

    return dateString;
  }

  public static boolean isValidDate(String date, String format) {
    boolean valid = false;
    try {

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
      LocalDate.parse(date, formatter);
      valid = true;

    } catch (Exception e) {
      valid = false;
    }

    return valid;
  }

  public static boolean isStateValid(String state) {
    CharSequence inputStr = state;
    Pattern pattern =
        Pattern.compile(
            "AL|AK|AR|AZ|CA|CO|CT|DC|DE|FL|GA|HI|IA|ID|IL|IN|KS|KY|LA|MA|MD|ME|MI|MN|MO|MS|MT|NC|ND|NE|NH|NJ|NM|NV|NY|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VA|VT|WA|WI|WV|WY|al|ak|ar|az|ca|co|ct|dc|de|fl|ga|hi|ia|id|il|in|ks|ky|la|ma|md|me|mi|mn|mo|ms|mt|nc|nd|ne|nh|nj|nm|nv|ny|oh|ok|or|pa|ri|sc|sd|tn|tx|ut|va|vt|wa|wi|wv|wy");
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean hasSqllInjectionCharacters(String text) {
    CharSequence inputStr = text;
    Pattern pattern = Pattern.compile("^[^\\'\\{\\}\\\\\\;\\$]*$");
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
      return false;
    } else {
      return true;
    }
  }
}
