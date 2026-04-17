package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String SAVE_FAILED_TEMPLATE = "Failed to save user with ID: '%s'.";
  private static final String UPDATE_FAILED_TEMPLATE = "Failed to update user with ID: '%s'.";
  private static final String FIND_BY_ID_FAILED_TEMPLATE = "Failed to find user with ID: '%s'.";
  private static final String FIND_BY_EMAIL_FAILED_TEMPLATE = "Failed to find user with email: '%s'.";
  private static final String FIND_ALL_FAILED_MESSAGE = "Failed to retrieve all users.";
  private static final String DELETE_FAILED_TEMPLATE = "Failed to delete user with ID: '%s'.";
  private static final String CONNECTION_FAILED_MESSAGE = "Could not establish database connection.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(SAVE_FAILED_TEMPLATE, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(UPDATE_FAILED_TEMPLATE, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(FIND_BY_ID_FAILED_TEMPLATE, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(FIND_BY_EMAIL_FAILED_TEMPLATE, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(FIND_ALL_FAILED_MESSAGE, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(DELETE_FAILED_TEMPLATE, userId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(CONNECTION_FAILED_MESSAGE, cause);
  }
}
