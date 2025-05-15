/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.exception.Constant.StatusCode.SERVER_SIDE_ERROR;

import com.amazonaws.util.StringUtils;
import com.quick.immi.ai.config.StripeSettings;
import com.quick.immi.ai.dto.common.PaymentDto;
import com.quick.immi.ai.entity.Customer;
import com.quick.immi.ai.exception.Constant;
import com.quick.immi.ai.exception.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.annotations.NotNull;

@Service
@Slf4j
public class StripeService {

  @Autowired private StripeSettings settings;

  @Autowired private CustomerMgtService userService;

  public String getCustomerSecret(@NotNull Customer user) throws StripeException {
    try {
      Stripe.apiKey = settings.getApiKey();
      com.stripe.model.Customer stripCustomer = null;
      if (user.getStripeCustomerId() != null) {
        stripCustomer = com.stripe.model.Customer.retrieve(user.getStripeCustomerId());
      } else {
        CustomerCreateParams params = CustomerCreateParams.builder().build();
        stripCustomer = com.stripe.model.Customer.create(params);
        user.setStripeCustomerId(stripCustomer.getId());
        userService.update(user);
      }

      Map<String, Object> paramsMap = new HashMap<>();
      paramsMap.put("customer", stripCustomer.getId());
      SetupIntent setupIntent = SetupIntent.create(paramsMap);
      return setupIntent.getClientSecret();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new PaymentException(
          SERVER_SIDE_ERROR.getValue(), Constant.SERVER_SIDE_ERROR, e.getMessage());
    }
  }

  public String getStripeCustomerId(@NotNull Customer user) {
    try {
      Stripe.apiKey = settings.getApiKey();
      if (!StringUtils.isNullOrEmpty(user.getStripeCustomerId())) {
        return user.getStripeCustomerId();
      } else {
        CustomerCreateParams params = CustomerCreateParams.builder().build();
        com.stripe.model.Customer customer = com.stripe.model.Customer.create(params);
        user.setStripeCustomerId(customer.getId());
        userService.update(user);
        return customer.getId();
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new PaymentException(
          SERVER_SIDE_ERROR.getValue(), Constant.SERVER_SIDE_ERROR, e.getMessage());
    }
  }

  public List<PaymentDto> findAllPaymentMethods(@NotNull Customer user) throws StripeException {
    Stripe.apiKey = settings.getApiKey();

    String stripeCustomerId = user.getStripeCustomerId();
    List<PaymentDto> cardList = new ArrayList<>();
    Map<String, Object> params = new HashMap<>();
    params.put("customer", stripeCustomerId);
    params.put("type", "card");

    PaymentMethodCollection paymentMethods = PaymentMethod.list(params);

    Iterable<PaymentMethod> it = paymentMethods.autoPagingIterable();

    for (PaymentMethod paymentMethod : it) {
      PaymentMethod.Card card = paymentMethod.getCard();
      PaymentDto dto =
          PaymentDto.builder()
              .card(card.getLast4())
              .expMonth(card.getExpMonth().intValue())
              .expYear(card.getExpYear().intValue())
              .id(paymentMethod.getId())
              .brand(card.getBrand())
              .build();
      cardList.add(dto);
    }
    return cardList;
  }

  public void deletePaymentMethod(String pmid) {
    try {
      Stripe.apiKey = settings.getApiKey();
      PaymentMethod paymentMethod = PaymentMethod.retrieve(pmid);
      paymentMethod.detach();
    } catch (StripeException e) {
      log.error(e.getMessage(), e);
      throw new PaymentException(
          SERVER_SIDE_ERROR.getValue(), Constant.SERVER_SIDE_ERROR, e.getMessage());
    }
  }

  public String addPaymentMethod(Customer user, PaymentDto payment) {
    Stripe.apiKey = settings.getApiKey();
    Map<String, Object> card = new HashMap<>();
    card.put("number", payment.getCard());
    card.put("exp_month", payment.getExpMonth());
    card.put("exp_year", payment.getExpYear());
    if (payment.getCvc() != null) {
      card.put("cvc", payment.getCvc());
    }
    Map<String, Object> params = new HashMap<>();
    params.put("type", "card");
    params.put("card", card);

    try {
      PaymentMethod paymentMethod = PaymentMethod.create(params);
      Map<String, Object> paramsMap = new HashMap<>();
      paramsMap.put("customer", user.getStripeCustomerId());
      PaymentMethod result = paymentMethod.attach(params);

      return result.getId();
    } catch (StripeException e) {
      log.error(e.getMessage(), e);
      throw new PaymentException(
          SERVER_SIDE_ERROR.getValue(), Constant.SERVER_SIDE_ERROR, e.getMessage());
    }
  }
}
