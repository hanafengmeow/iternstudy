/* (C) 2024 */
package com.quick.immi.ai.dao;

import com.quick.immi.ai.entity.Customer;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper {
  Customer get(@Param("id") Integer id);

  Customer getByCName(@Param("cognitoUsername") String cognitoUsername);

  Customer getByUserName(@Param("username") String username);

  Integer create(Customer entity);

  void updateByCName(Customer entity);

  void updateById(Customer customer);
}
