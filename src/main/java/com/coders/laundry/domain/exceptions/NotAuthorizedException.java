package com.coders.laundry.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "there is not sufficient authorization.")
public class NotAuthorizedException extends RuntimeException {
}
