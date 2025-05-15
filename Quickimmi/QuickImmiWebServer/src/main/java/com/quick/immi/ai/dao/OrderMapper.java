/* (C) 2024 */
package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Order;

public interface OrderMapper {
  Long create(Order order);

  // fetch order by caseId, and product name
  Order get(Long caseId, String product);

  // fetch order by orderId
  Order getByOrderId(Long id);

  Order getByStripeOrderId(String stripeId);

  void update(Order order);
}
