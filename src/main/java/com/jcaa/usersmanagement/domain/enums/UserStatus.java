package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidUserStatusException;

public enum UserStatus {
  ACTIVE("Activo"),
  INACTIVE("Inactivo"),
  PENDING("Pendiente de activacion"),
  BLOCKED("Bloqueado"),
  DELETED("Eliminado");

  private final String displayLabel;

  UserStatus(final String displayLabel) {
    this.displayLabel = displayLabel;
  }

  public String getDisplayLabel() {
    return displayLabel;
  }

  public static UserStatus fromString(final String value) {
    for (final UserStatus status : values()) {
      if (status.name().equalsIgnoreCase(value)) {
        return status;
      }
    }
    throw InvalidUserStatusException.becauseValueIsInvalid(value);
  }
}
