package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.out.EmailSenderPort;
import com.jcaa.usersmanagement.domain.exception.EmailSenderException;
import com.jcaa.usersmanagement.domain.model.EmailDestinationModel;
import com.jcaa.usersmanagement.domain.model.UserModel;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public final class EmailNotificationService {

  private static final String SUBJECT_CREATED = "Tu cuenta ha sido creada — Gestión de Usuarios";
  private static final String SUBJECT_UPDATED =
      "Tu cuenta ha sido actualizada — Gestión de Usuarios";

  private static final String TOKEN_NAME     = "name";
  private static final String TOKEN_EMAIL    = "email";
  private static final String TOKEN_PASSWORD = "password";
  private static final String TOKEN_ROLE     = "role";
  private static final String TOKEN_STATUS   = "status";

  private final EmailSenderPort emailSenderPort;

  public void notifyUserCreated(final UserModel user, final String plainPassword) {
    // Ley de Déméter: usar métodos delegadores en lugar de encadenamiento profundo
    final Map<String, String> tokens = Map.of(
        TOKEN_NAME, user.getNameValue(),
        TOKEN_EMAIL, user.getEmailValue(),
        TOKEN_PASSWORD, plainPassword,
        TOKEN_ROLE, user.getRoleDisplayName());
    prepareAndSendNotification(user, SUBJECT_CREATED, "user-created.html", tokens);
  }

  public void notifyUserUpdated(final UserModel user) {
    // Clean Code - Regla 11 (evitar duplicación): misma estructura que notifyUserCreated —
    // loadTemplate → renderTemplate → buildDestination → sendOrLog.
    // Esta lógica de orquestación debería extraerse a un método genérico privado.
    // Clean Code - Regla 25 y 26: misma sobrecompactación que arriba.
    // Ley de Déméter: usar métodos delegadores en lugar de encadenamiento profundo
    final Map<String, String> tokens = Map.of(
        TOKEN_NAME, user.getNameValue(),
        TOKEN_EMAIL, user.getEmailValue(),
        TOKEN_ROLE, user.getRoleDisplayName(),
        TOKEN_STATUS, user.getStatusDisplayName());
    prepareAndSendNotification(user, SUBJECT_UPDATED, "user-updated.html", tokens);
  }

  private void prepareAndSendNotification(
      final UserModel user,
      final String subject,
      final String templateName,
      final Map<String, String> tokens) {
    final String template = loadTemplate(templateName);
    final String body = renderTemplate(template, tokens);
    final EmailDestinationModel destination = buildDestination(user, subject, body);
    send(destination);
  }



  private static EmailDestinationModel buildDestination(
      final UserModel user, final String subject, final String body) {
    // Ley de Déméter: usar métodos delegadores en lugar de encadenamiento profundo
    return new EmailDestinationModel(
        user.getEmailValue(), user.getNameValue(), subject, body);
  }

  private String loadTemplate(final String templateName) {
    final String path = "/templates/" + templateName;
    try (InputStream inputStream = openResourceStream(path)) {
      if (Objects.isNull(inputStream)) {
        throw EmailSenderException.becauseSendFailed(
            new IllegalStateException("Template not found: " + path));
      }
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (final IOException ioException) {
      throw EmailSenderException.becauseSendFailed(ioException);
    }
  }

  InputStream openResourceStream(final String path) {
    return getClass().getResourceAsStream(path);
  }

  private static String renderTemplate(String template, final Map<String, String> values) {
    String result = template;
    for (final Map.Entry<String, String> tokenEntry : values.entrySet()) {
      final String token = "{{" + tokenEntry.getKey() + "}}";
      result = result.replace(token, tokenEntry.getValue());
    }
    return result;
  }

  private void send(final EmailDestinationModel destination) {
    try {
      emailSenderPort.send(destination);
    } catch (final EmailSenderException senderException) {
      log.log(
          Level.WARNING,
          "[EmailNotificationService] No se pudo enviar correo. Causa: {0}",
          senderException.getMessage());
      throw senderException;
    }
  }
}
