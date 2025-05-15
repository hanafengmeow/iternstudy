/* (C) 2024 */
package com.quick.immi.ai.service.helper;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendEmailService {

  @Autowired private AmazonSimpleEmailService amazonSimpleEmailService;

  /**
   * @param subject subject of the email
   * @param sender sender email
   * @param recipients list of recipient
   * @param email html email body
   */
  public void sendEmail(String sender, List<String> recipients, String subject, String email) {
    SendEmailRequest request =
        new SendEmailRequest()
            .withDestination(new Destination().withToAddresses(recipients))
            .withMessage(
                new Message()
                    .withBody(
                        new Body().withHtml(new Content().withCharset("UTF-8").withData(email)))
                    .withSubject(new Content().withCharset("UTF-8").withData(subject)))
            .withSource(sender);
    amazonSimpleEmailService.sendEmail(request);
  }
}
