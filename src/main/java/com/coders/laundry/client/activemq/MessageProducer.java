package com.coders.laundry.client.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

  private final JmsTemplate jmsTemplate;

  @Autowired
  public MessageProducer(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public void produce(String text) {
    jmsTemplate.send("laundryQueue", session -> session.createTextMessage(text));
  }
}
