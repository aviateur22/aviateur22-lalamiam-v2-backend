package com.ctoutweb.lalamiam.infra.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource({"classpath:application.properties"})
public class EmailConfig {
  private static final Logger LOGGER = LogManager.getLogger();
  @Value("${mailer.smtp}")
  private String host;

  @Value("${mailer.port}")
  private int port;

  @Value("${mailer.account}")
  private String accountEmail;

  @Value("${mailer.password}")
  private String password;

  @Value("${mailer.secure}")
  private boolean isSecure;

  @Value("${mailer.smtp.auth}")
  private boolean smtpAuth;

  @Bean
  JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
    javaMailSenderImpl.setHost(host);
    javaMailSenderImpl.setPort(port);
    javaMailSenderImpl.setUsername(accountEmail);
    javaMailSenderImpl.setPassword(password);

    Properties props = javaMailSenderImpl.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", smtpAuth);
    props.put("mail.smtp.starttls.enable", isSecure);
    props.put("mail.debug", "false");

    return javaMailSenderImpl;
  }
}