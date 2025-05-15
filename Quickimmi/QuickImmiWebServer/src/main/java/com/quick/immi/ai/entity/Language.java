/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum Language {
  ENGLISH("english"),
  SIMPLIFIED_CHINESE("simplified_chinese"),
  CHINESE("chinese"),
  SPANISH("spanish"),
  FRENCH("french"),
  ARABIC("arabic"),
  RUSSIAN("russian"),
  PORTUGUESE("portuguese"),
  JAPANESE("japanese"),
  GERMAN("german"),
  KOREAN("korean"),
  ITALIAN("italian"),
  HINDI("hindi"),
  TURKISH("turkish"),
  DUTCH("dutch"),
  POLISH("polish"),
  UKRAINIAN("ukrainian"),
  GREEK("greek"),
  HEBREW("hebrew"),
  CZECH("czech"),
  SWEDISH("swedish"),
  DANISH("danish"),
  FINNISH("finnish"),
  NORWEGIAN("norwegian"),
  ICELANDIC("icelandic"),
  HUNGARIAN("hungarian"),
  ROMANIAN("romanian"),
  BULGARIAN("bulgarian"),
  CROATIAN("croatian"),
  SERBIAN("serbian"),
  SLOVAK("slovak"),
  SLOVENIAN("slovenian"),
  LITHUANIAN("lithuanian"),
  LATVIAN("latvian"),
  ESTONIAN("estonian"),
  MALTESE("maltese"),
  VIETNAMESE("vietnamese"),
  THAI("thai"),
  INDONESIAN("indonesian"),
  MALAY("malay"),
  FILIPINO("filipino"),
  KHMERE("khmere"),
  LAO("lao"),
  BENGALI("bengali"),
  PUNJABI("punjabi"),
  GUJARATI("gujarati"),
  ORIYA("oriya"),
  TAMIL("tamil"),
  TELUGU("telugu"),
  KANNADA("kannada"),
  MALAYALAM("malayalam"),
  SINHALA("sinhala"),
  BURMESE("burmese"),
  KHMER("khmer"),
  TIBETAN("tibetan"),
  NEPALI("nepali"),
  MARATHI("marathi"),
  SANSKRIT("sanskrit");

  private String name;

  Language(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
