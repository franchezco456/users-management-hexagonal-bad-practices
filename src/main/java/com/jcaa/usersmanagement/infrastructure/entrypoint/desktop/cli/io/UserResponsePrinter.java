package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-10s : %s%n";

  private final ConsoleIO console;

  public void print(final UserResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID",     response.id());
    console.printf(ROW_FORMAT, "Name",   response.name());
    console.printf(ROW_FORMAT, "Email",  response.email());
    console.printf(ROW_FORMAT, "Role",   response.role());
    // Clean Code - Regla 16: se llama al auxiliar que tiene la cadena if/else larga
    console.printf(ROW_FORMAT, "Status", getStatusLabel(response.status()));
    console.println(SEPARATOR);
  }

  public void printList(final List<UserResponse> users) {

    if (users.isEmpty()) {
      console.println("  No users found.");
      return;
    }
    console.printf("%n  Total: %d user(s)%n", users.size());
    users.forEach(this::print);
  }

  // Clean Code - Regla 27 (código listo para leer, no solo para compilar):
  // Intención clara: mostrar resumen de usuarios, o "no encontrados" si la lista está vacía.
  // Descompuesto en pasos explícitos y legibles sin necesidad de explicación oral del autor.
  public void printSummary(final List<UserResponse> users) {
    if (isUsersListEmpty(users)) {
      console.println("  No users found.");
      return;
    }

    final String usersSummaryText = buildUsersSummaryText(users);
    console.println(usersSummaryText);
  }

  private boolean isUsersListEmpty(final List<UserResponse> users) {
    return users == null || users.isEmpty();
  }

  private String buildUsersSummaryText(final List<UserResponse> users) {
    final StringBuilder summary = new StringBuilder();
    for (final UserResponse user : users) {
      final String userLine = String.format("  %s (%s)%n", user.name(), getStatusLabel(user.status()));
      summary.append(userLine);
    }
    return summary.toString();
  }

  // Clean Code - Regla 16 (evitar condicionales repetitivas cuando el polimorfismo aporta claridad):
  // El método getDisplayLabel() en el enum UserStatus encapsula la lógica de mapeo de estados.
  // Esto elimina la cascada de if/else y centraliza la regla en un único lugar.
  private static String getStatusLabel(final String status) {
    return UserStatus.fromString(status).getDisplayLabel();
  }
}