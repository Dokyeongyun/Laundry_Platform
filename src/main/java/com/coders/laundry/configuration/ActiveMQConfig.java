package com.coders.laundry.configuration;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class ActiveMQConfig {

  @Value("${activemq.broker.url}")
  private String brokerUrl;

  @Bean
  public Queue queue() {
    return new ActiveMQQueue("laundryQueue");
  }

  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory(brokerUrl);
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    return new JmsTemplate(connectionFactory());
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    FixedBackOff backOff = new FixedBackOff();
    backOff.setMaxAttempts(5);
    backOff.setInterval(2500L);
    factory.setBackOff(backOff);
    factory.setConnectionFactory(connectionFactory());
    factory.setConcurrency("1"); // 리스너들이 컨슘할때 사용할 세션갯수, 3-5 : 최소 3개~최대 5개
    return factory;
  }
}
