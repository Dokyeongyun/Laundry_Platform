package com.coders.laundry.client.activemq;

import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

  @JmsListener(destination = "laundryQueue")
  public void consume(final Message jsonMessage) {
    System.out.printf("receive : %s%n", jsonMessage);
  }
}
