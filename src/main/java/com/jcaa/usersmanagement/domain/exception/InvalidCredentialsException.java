package com.jcaa.usersmanagement.domain.exception;

public final class InvalidCredentialsException extends DomainException {

  private static final String INVALID_CREDENTIALS_MESSAGE = "Correo o contrasena incorrectos.";
  private static final String ACCOUNT_NOT_ACTIVE_MESSAGE =
      "Tu cuenta no esta activa. Contacta al administrador.";

  private InvalidCredentialsException(final String message) {
    super(message);
  }

  public static InvalidCredentialsException becauseCredentialsAreInvalid() {
    return new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
  }

  public static InvalidCredentialsException becauseUserIsNotActive() {
    return new InvalidCredentialsException(ACCOUNT_NOT_ACTIVE_MESSAGE);
  }
}
