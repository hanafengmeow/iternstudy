/* (C) 2024 */
package com.quick.immi.ai.api;

import static com.quick.immi.ai.exception.Constant.CLIENT_SIDE_ERROR;

import com.amazonaws.util.StringUtils;
import com.quick.immi.ai.annotation.Login;
import com.quick.immi.ai.dto.request.CreateOrderRequestDto;
import com.quick.immi.ai.dto.response.ResponseDto;
import com.quick.immi.ai.entity.CreateOrderResult;
import com.quick.immi.ai.exception.PaymentException;
import com.quick.immi.ai.service.CustomerMgtService;
import com.quick.immi.ai.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@Slf4j
public class BookingController {

  @Autowired private CustomerMgtService userService;

  @Autowired private OrderService orderService;

  @PostMapping("/create")
  @Login
  public ResponseEntity<ResponseDto> createOrder(
      @RequestBody CreateOrderRequestDto createOrderRequestDto) {
    try {
      CreateOrderResult result = orderService.create(createOrderRequestDto);
      return ResponseEntity.ok().body(ResponseDto.newInstance(result));
    } catch (PaymentException e) {
      log.error(String.format("fail to create order %s", createOrderRequestDto), e);
      return ResponseEntity.status(e.getStatusCode())
          .body(ResponseDto.newInstance(e.getErrorCode(), e.getMessage()));
    }
  }

  @PostMapping("/quickimmi-stripehook")
  public ResponseEntity<ResponseDto<Boolean>> stripehook(
      @RequestHeader(value = "Stripe-Signature") String signature, @RequestBody String eventJson) {
    log.info(
        String.format("stripehook input signature: %s --- eventJson: %s", signature, eventJson));
    if (StringUtils.isNullOrEmpty(signature) || StringUtils.isNullOrEmpty(eventJson)) {
      return ResponseEntity.badRequest()
          .body(
              ResponseDto.newInstance(
                  CLIENT_SIDE_ERROR, "signature or eventJson can't be null or empty!", false));
    }
    try {
      orderService.stripeHook(signature, eventJson);
      return ResponseEntity.ok().body(ResponseDto.newInstance(true));
    } catch (PaymentException e) {
      log.error(String.format("fail to process stripehook with given event %s", eventJson), e);
      return ResponseEntity.status(500)
          .body(ResponseDto.newInstance(e.getErrorCode(), e.getMessage()));
    }
  }
}
