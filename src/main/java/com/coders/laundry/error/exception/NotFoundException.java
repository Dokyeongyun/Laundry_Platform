package com.coders.laundry.error.exception;

import com.coders.laundry.error.ErrorResponse;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

  private Class<?> clazz;

  private Object input;

  public NotFoundException(String message, Class<?> clazz, Object input) {
    super(message);
    this.clazz = clazz;
    this.input = input;
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  public ErrorResponse buildErrorResponse() {
    return ErrorResponse.builder()
        .message(getMessage())
        .model(clazz.getTypeName())
        .input(input)
        .build();
  }
}
