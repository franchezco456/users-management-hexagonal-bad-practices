package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserNameException extends DomainException {

  private static final String EMPTY_VALUE_MESSAGE = "The user name must not be empty.";
  private static final String TOO_SHORT_TEMPLATE =
      "The user name must have at least %d characters.";

  private InvalidUserNameException(final String message) {
    super(message);
  }

  public static InvalidUserNameException becauseValueIsEmpty() {
    return new InvalidUserNameException(EMPTY_VALUE_MESSAGE);
  }

  public static InvalidUserNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserNameException(String.format(TOO_SHORT_TEMPLATE, minimumLength));
  }
}
