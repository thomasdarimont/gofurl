package com.gofore.gofurl.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Short url not found")
public class UrlNotFoundException extends RuntimeException {
}
