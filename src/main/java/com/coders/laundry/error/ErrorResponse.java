package com.coders.laundry.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

  private String message;

  private String model;

  private String field;

  private Object input;

  private Detail detail;
}
