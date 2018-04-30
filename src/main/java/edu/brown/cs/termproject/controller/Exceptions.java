package edu.brown.cs.termproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {

  ResourceNotFoundException() {
    super();
  }

  ResourceNotFoundException(Exception e) {
    super(e);
  }
}

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class UserNotFoundException extends RuntimeException {

  UserNotFoundException() {
    super();
  }

  UserNotFoundException(Exception e) {
    super(e);
  }
}
