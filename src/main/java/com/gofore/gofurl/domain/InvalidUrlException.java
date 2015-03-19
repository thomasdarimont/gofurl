package com.gofore.gofurl.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Malformed URL")
public class InvalidUrlException extends RuntimeException {
}
