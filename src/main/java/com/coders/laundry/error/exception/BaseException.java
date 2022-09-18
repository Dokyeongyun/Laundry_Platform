package com.coders.laundry.error.exception;

import com.coders.laundry.error.ErrorBuildable;
import com.coders.laundry.error.ErrorResponse;
import java.io.Serial;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException implements ErrorBuildable {

  @Serial
  private static final long serialVersionUID = 181755367808226645L;

  public BaseException(String message) {
    super(message);
  }

  public abstract HttpStatus getStatus();

  @Override
  public ErrorResponse buildErrorResponse() {
    return ErrorResponse.builder()
        .message(getMessage())
        .build();
  }
}
