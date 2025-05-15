/* (C) 2024 */
package com.quick.immi.ai.service;

import static com.quick.immi.ai.exception.Constant.SERVER_SIDE_ERROR;
import static com.stripe.net.ApiResource.GSON;
import static com.stripe.param.PaymentLinkCreateParams.PaymentMethodType.CARD;

import com.amazonaws.util.StringUtils;
import com.quick.immi.ai.config.StripeSettings;
import com.quick.immi.ai.dao.OrderMapper;
import com.quick.immi.ai.dto.request.CreateOrderRequestDto;
import com.quick.immi.ai.entity.CreateOrderResult;
import com.quick.immi.ai.entity.Order;
import com.quick.immi.ai.entity.OrderStatus;
import com.quick.immi.ai.exception.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentCreateParams.CaptureMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderService {

  @Autowired private StripeService stripeService;

  @Autowired private StripeSettings settings;

  @Autowired private OrderMapper orderMapper;

  @Transactional(
      propagation = Propagation.REQUIRED,
      readOnly = false,
      rollbackFor = Exception.class)
  public CreateOrderResult create(CreateOrderRequestDto createOrderRequestDto) {
    Order order =
        orderMapper.get(createOrderRequestDto.getCaseId(), createOrderRequestDto.getProduct());

    if (order != null && OrderStatus.Succeeded.getValue().equals(order.getStatus())) {
      // allow retry for other failure case
      throw new PaymentException(
          400,
          "ORDER_ALREADY_PAID_ERROR",
          "Fail to create new order due to order has been paid successfully");
    }

    Stripe.apiKey = settings.getApiKey();
    try {
      // Create the order
      Long orderId = null;
      if (order == null) {
        order =
            Order.builder()
                .caseId(createOrderRequestDto.getCaseId())
                .currency(createOrderRequestDto.getCurrency())
                .totalMoney(createOrderRequestDto.getTotalMoney())
                .productName(createOrderRequestDto.getProduct())
                .status(OrderStatus.Created.getValue())
                .userId(createOrderRequestDto.getUserId())
                .build();
        orderMapper.create(order);
      } else {
        order.setTotalMoney(createOrderRequestDto.getTotalMoney());
      }
      orderId = order.getId();

      // for one-time payment 不需要设置stripeCustomer

      PaymentIntentCreateParams params =
          PaymentIntentCreateParams.builder()
              .setAmount(Long.valueOf(createOrderRequestDto.getTotalMoney()))
              .setCurrency(getCurrency(createOrderRequestDto.getCurrency()))
              .setDescription("Order for product: " + createOrderRequestDto.getProduct())
              .setCaptureMethod(CaptureMethod.MANUAL)
              .addPaymentMethodType(CARD.getValue())
              .setReceiptEmail(createOrderRequestDto.getEmail())
              .putMetadata("order_id", String.valueOf(orderId))
              .addPaymentMethodType(createOrderRequestDto.getPaymentMethod().getValue())
              .build();

      PaymentIntent intent = PaymentIntent.create(params);

      order.setStripeOrderId(intent.getId());
      order.setStatus(OrderStatus.Created.getValue());
      order.setUpdatedAt(System.currentTimeMillis());

      orderMapper.update(order);

      return CreateOrderResult.builder()
          .clientSecret(intent.getClientSecret())
          .orderId(intent.getId())
          .build();
    } catch (Exception e) {
      log.error(String.format("fail to create order for order", createOrderRequestDto), e);
      throw new PaymentException(500, SERVER_SIDE_ERROR, e.getMessage());
    }
  }

  private String getCurrency(String currency) {
    return StringUtils.isNullOrEmpty(currency) ? "usd" : currency;
  }

  public void stripeHook(String signature, String eventJson) {
    log.info(String.format("stripe web hook : %s", eventJson));
    Stripe.apiKey = settings.getApiKey();
    Event event;
    try {
      event =
          Webhook.constructEvent(eventJson, signature, settings.getWebhookSecretEndpointSecret());
    } catch (SignatureVerificationException e) {
      event = GSON.fromJson(eventJson, Event.class);
    }
    // Deserialize the nested object inside the event
    EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
    StripeObject stripeObject;
    if (dataObjectDeserializer.getObject().isPresent()) {
      stripeObject = dataObjectDeserializer.getObject().get();
    } else {
      // Deserialization failed, probably due to an API version mismatch.
      // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
      // instructions on how to handle this case, or return an error here.
      throw new RuntimeException("----- fail to dataObjectDeserializer");
    }
    // Handle the event
    log.info("stripe web hook :" + event.getType());
    switch (event.getType()) {
      case "payment_intent.succeeded":
        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
        String id = paymentIntent.getId();

        log.info(String.format("PaymentIntent was successful for strip order %s!", id));
        // 根据ID查询对应的订单  修改订单状态和支付状态
        Order order = orderMapper.getByStripeOrderId(id);
        order.setStatus(OrderStatus.Succeeded.getValue());
        orderMapper.update(order);
        break;
      case "payment_intent.payment_failed":
        log.info("PaymentIntent was failed!");
        paymentIntent = (PaymentIntent) stripeObject;
        id = paymentIntent.getId();
        log.error("webhook -paymentIntent error:" + id);

        order = orderMapper.getByStripeOrderId(id);
        order.setStatus(OrderStatus.Failed.getValue());
        orderMapper.update(order);
        break;
      default:
        log.info("Unhandled event type: " + event.getType());
    }
  }
}
