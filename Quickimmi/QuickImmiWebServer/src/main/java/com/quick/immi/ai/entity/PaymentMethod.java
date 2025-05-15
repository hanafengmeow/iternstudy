/* (C) 2024 */
package com.quick.immi.ai.entity;

public enum PaymentMethod {
  Alipay("alipay"),
  WechatPay("wechat_pay"),
  Paypal("paypal"),
  Card("card");

  private String value;

  PaymentMethod(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
