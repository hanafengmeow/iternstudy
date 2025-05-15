/* (C) 2024 */
package com.quick.immi.ai.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.quick.immi.ai.dao.CustomerMapper;
import com.quick.immi.ai.entity.Customer;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerMgtService {
  @Autowired private CustomerMapper userMapper;

  @Value("${com.quickimmi.jwt.aws.userPoolId}")
  private String userPoolId;

  @Autowired private AWSCognitoIdentityProvider cognitoClient;

  /**
   * Creates a new customer using the provided email address.
   *
   * <p>This method performs the following operations:
   *
   * <ol>
   *   <li>Creates a new user in the AWS Cognito User Pool using the provided email address.
   *   <li>Sets the email as the username in the Cognito User Pool.
   *   <li>Marks the email as verified in the user's attributes.
   *   <li>Sends a welcome email to the newly created user (via Cognito).
   *   <li>Stores the customer details, including the Cognito username, in the local database.
   * </ol>
   *
   * @param email The email address to be used as the username for the new customer.
   * @return The created {@link Customer} object containing the user's information.
   * @throws UsernameExistsException if a user with the given email already exists in the Cognito
   *     User Pool.
   * @throws RuntimeException if an error occurs during the user creation process.
   */
  public Customer createCustomerByEmail(String email) {
    AdminCreateUserRequest request = new AdminCreateUserRequest();
    request.setUserPoolId(userPoolId);
    request.setUsername(email);
    request.setUserAttributes(
        Arrays.asList(
            new AttributeType().withName("email").withValue(email),
            new AttributeType().withName("email_verified").withValue("true")));
    request.setDesiredDeliveryMediums(Collections.singleton("EMAIL"));
    request.setForceAliasCreation(Boolean.FALSE);
    try {
      AdminCreateUserResult result = cognitoClient.adminCreateUser(request);
      String cognitoUsername = result.getUser().getUsername();
      Customer customer = new Customer();
      customer.setUsername(email);
      customer.setEmail(email);
      customer.setCognitoUsername(cognitoUsername);
      customer.setCreatedAt(System.currentTimeMillis());
      this.create(customer);
      log.info("User created: " + result.getUser().getUsername());
      return customer;
    } catch (UsernameExistsException e) {
      log.error("User already exists: " + email);
      throw e;
    } catch (Exception e) {
      log.error("Error creating user: " + email, e);
      throw new RuntimeException(e);
    }
  }

  public Customer get(Integer id) {
    // TODO --- add cache
    Customer entity = userMapper.get(id);
    return entity;
  }

  public Customer getByUsername(String username) {
    // TODO --- add cache
    Customer entity = userMapper.getByUserName(username);
    return entity;
  }

  public Customer createCustomerByEmailIfNotExist(String username) {
    Customer entity = userMapper.getByCName(username);
    if (entity == null) {
      return this.createCustomerByEmail(username);
    }
    return entity;
  }

  public Customer findByCName(String username) {
    Customer entity = userMapper.getByCName(username);
    return entity;
  }

  public Integer create(Customer customer) {
    return userMapper.create(customer);
  }

  public boolean updateByCName(Customer user) {
    userMapper.updateByCName(user);
    return true;
  }

  public boolean update(Customer customer) {
    userMapper.updateById(customer);
    return true;
  }
}
