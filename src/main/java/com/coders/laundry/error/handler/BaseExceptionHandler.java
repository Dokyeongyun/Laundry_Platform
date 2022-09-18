package com.coders.laundry.error.handler;

import com.coders.laundry.error.ErrorResponse;
import com.coders.laundry.error.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

  @ExceptionHandler(BaseException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
    return ResponseEntity.status(e.getStatus()).body(e.buildErrorResponse());
  }
}
