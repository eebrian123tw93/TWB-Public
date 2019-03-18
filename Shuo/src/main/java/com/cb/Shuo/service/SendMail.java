package com.cb.Shuo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SendMail {
  private static final Logger logger = LoggerFactory.getLogger(SendMail.class);

  public static void sendEmail(String receiver, String messageText) {
    try {
      final String from = "eebiran123tw93@gmail.com";
      final String pass = "lindia0912";

      List<Address> addresses = new ArrayList<>();
      addresses.add(new InternetAddress("eebrian123tw60@gmail.com"));
      addresses.add(new InternetAddress(receiver));

      // Get system properties
      Properties props = System.getProperties();
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");
      // Setup mail server
      // Get the default Session object.
      Session session =
          Session.getInstance(
              props,
              new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(from, pass);
                }
              });

      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));

      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

      // Set Subject: header field
      message.setSubject("TWB-Forgot Password");

      // Now set the actual message
      message.setText(messageText);

      // Send message
      logger.info("send mail to " + receiver);
      Transport.send(message);
      logger.info("success");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
