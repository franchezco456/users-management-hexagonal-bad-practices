package com.jcaa.usersmanagement.domain.model;

import java.util.Objects;
import lombok.Value;

@Value
public class EmailDestinationModel {

  private static final String EMAIL_REQUIRED_ERROR = "El email del destinatario es requerido.";
  private static final String NAME_REQUIRED_ERROR = "El nombre del destinatario es requerido.";
  private static final String SUBJECT_REQUIRED_ERROR = "El asunto es requerido.";
  private static final String BODY_REQUIRED_ERROR = "El cuerpo del mensaje es requerido.";

  String destinationEmail;
  String destinationName;
  String subject;
  String body;

  public EmailDestinationModel(
      final String destinationEmail,
      final String destinationName,
      final String subject,
      final String body) {
    this.destinationEmail = validateNotBlank(destinationEmail, EMAIL_REQUIRED_ERROR);
    this.destinationName  = validateNotBlank(destinationName,  NAME_REQUIRED_ERROR);
    this.subject          = validateNotBlank(subject,          SUBJECT_REQUIRED_ERROR);
    this.body             = validateNotBlank(body,             BODY_REQUIRED_ERROR);
  }

  private static String validateNotBlank(final String value, final String errorMessage) {
    Objects.requireNonNull(value, errorMessage);
    if (value.trim().isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
    return value;
  }
}
