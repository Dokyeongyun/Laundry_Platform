package com.coders.laundry.controller;

import com.coders.laundry.client.activemq.MessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ActiveMQTestController {

  private final MessageProducer producer;
  private final ObjectMapper mapper;

  @PostMapping("/mq")
  public HttpStatus produce(
      @RequestBody Map<String, Object> map
  ) throws JsonProcessingException {
    producer.produce(mapper.writeValueAsString(map));

    return HttpStatus.OK;
  }
}
