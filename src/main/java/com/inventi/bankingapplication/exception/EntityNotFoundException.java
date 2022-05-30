package com.inventi.bankingapplication.exception;

import lombok.NonNull;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(@NonNull String id) {
    super(String.format("Object not found: ID=%s", id));
  }
}
