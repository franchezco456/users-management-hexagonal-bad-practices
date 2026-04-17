package com.jcaa.usersmanagement.domain.model;

import java.util.Objects;
import lombok.Value;

@Value
public class EmailDestinationModel {

  String destinationEmail;
  String destinationName;
  String subject;
  String body;

  public EmailDestinationModel(
      final String destinationEmail,
      final String destinationName,
      final String subject,
      final String body) {
    this.destinationEmail = validateNotBlank(destinationEmail, "El email del destinatario es requerido.");
    this.destinationName  = validateNotBlank(destinationName,  "El nombre del destinatario es requerido.");
    this.subject          = validateNotBlank(subject,          "El asunto es requerido.");
    this.body             = validateNotBlank(body,             "El cuerpo del mensaje es requerido.");
  }

  private static String validateNotBlank(final String value, final String errorMessage) {
    Objects.requireNonNull(value, errorMessage);
    // VIOLACIÓN Regla 10: mensajes de error hardcodeados en el cuerpo del método,
    // en lugar de definirse como constantes con nombre descriptivo.
    if (value.trim().isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
    return value;
  }
}
