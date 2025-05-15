/* (C) 2024 */
package com.quick.immi.ai.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/payment")
public class SubscriptionController {

  //    @Login
  //    @GetMapping("/listProducts")
  //    public ResponseEntity<ResponseDto<List<Product>>> listProducts(@RequestHeader("role") Role
  // Role) {
  //        try {
  //            return ResponseEntity.ok().body(ResponseDto.newInstance(id));
  //        } catch (Exception exp) {
  //            log.error("Fail to create lawyer", exp);
  //            return ResponseEntity.status(500)
  //                    .body(
  //                            ResponseDto.newInstance(
  //                                    SERVER_SIDE_ERROR, String.format("Failed to create lawyer
  // due to %s", exp.getMessage())));
  //        }
  //    }
  //
  //    @Login
  //    @PostMapping("/createSubscription")
  //    public ResponseEntity<ResponseDto<List<Product>>> createSubscription(@RequestHeader("role")
  // Role Role) {
  //        try {
  //            return ResponseEntity.ok().body(ResponseDto.newInstance(id));
  //        } catch (Exception exp) {
  //            log.error("Fail to create lawyer", exp);
  //            return ResponseEntity.status(500)
  //                    .body(
  //                            ResponseDto.newInstance(
  //                                    SERVER_SIDE_ERROR, String.format("Failed to create lawyer
  // due to %s", exp.getMessage())));
  //        }
  //    }
  //
  //    @Login
  //    @GetMapping("/cancelSubscription")
  //    public ResponseEntity<ResponseDto<List<Product>>> cancelSubscription(@RequestHeader("role")
  // Role Role) {
  //        try {
  //            return ResponseEntity.ok().body(ResponseDto.newInstance(id));
  //        } catch (Exception exp) {
  //            log.error("Fail to create lawyer", exp);
  //            return ResponseEntity.status(500)
  //                    .body(
  //                            ResponseDto.newInstance(
  //                                    SERVER_SIDE_ERROR, String.format("Failed to create lawyer
  // due to %s", exp.getMessage())));
  //        }
  //    }
  //
  //    @Login
  //    @GetMapping("/invoicePreview")
  //    public ResponseEntity<ResponseDto<List<Product>>> invoicePreview(@RequestHeader("role") Role
  // Role) {
  //        try {
  //            return ResponseEntity.ok().body(ResponseDto.newInstance(id));
  //        } catch (Exception exp) {
  //            log.error("Fail to create lawyer", exp);
  //            return ResponseEntity.status(500)
  //                    .body(
  //                            ResponseDto.newInstance(
  //                                    SERVER_SIDE_ERROR, String.format("Failed to create lawyer
  // due to %s", exp.getMessage())));
  //        }
  //    }
  //
  //    @Login
  //    @GetMapping("/invoicePreview")
  //    public ResponseEntity<ResponseDto<List<Product>>> updateSubscription(@RequestHeader("role")
  // Role Role) {
  //        try {
  //            return ResponseEntity.ok().body(ResponseDto.newInstance(id));
  //        } catch (Exception exp) {
  //            log.error("Fail to create lawyer", exp);
  //            return ResponseEntity.status(500)
  //                    .body(
  //                            ResponseDto.newInstance(
  //                                    SERVER_SIDE_ERROR, String.format("Failed to create lawyer
  // due to %s", exp.getMessage())));
  //        }
  //    }
  //
  //    @Login
  //    @GetMapping("/getSubscriptions")
  //    public ResponseEntity<ResponseDto<List<Product>>> getSubscriptions(@RequestHeader("role")
  // Role Role,
  //                                                                       @RequestParam("userId")
  // Integer userId) {
  //        try {
  //            return ResponseEntity.ok().body(ResponseDto.newInstance(id));
  //        } catch (Exception exp) {
  //            log.error("Fail to create lawyer", exp);
  //            return ResponseEntity.status(500)
  //                    .body(
  //                            ResponseDto.newInstance(
  //                                    SERVER_SIDE_ERROR, String.format("Failed to create lawyer
  // due to %s", exp.getMessage())));
  //        }
  //    }
}
