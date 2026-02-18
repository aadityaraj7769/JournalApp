package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EmailServiceTest {

  @Autowired
  private EmailService emailService;

  @Test
   void testSendMail() {
    emailService.sendMail("aadityaraj7769@gmail.com", "Test Subject", "Test Body");
  }
}
