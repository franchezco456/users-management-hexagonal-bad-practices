package com.jcaa.usersmanagement.domain.exception;

public final class EmailSenderException extends DomainException {

  private static final String SMTP_FAILED_TEMPLATE =
      "No se pudo enviar el correo a '%s'. Error SMTP: %s";
  private static final String SEND_FAILED_MESSAGE =
      "La notificacion por correo no pudo ser enviada.";

  private EmailSenderException(final String message) {
    super(message);
  }

  private EmailSenderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static EmailSenderException becauseSmtpFailed(
      final String destinationEmail, final String smtpError) {
    return new EmailSenderException(
        String.format(SMTP_FAILED_TEMPLATE, destinationEmail, smtpError));
  }

  public static EmailSenderException becauseSendFailed(final Throwable cause) {
    return new EmailSenderException(SEND_FAILED_MESSAGE, cause);
  }
}
