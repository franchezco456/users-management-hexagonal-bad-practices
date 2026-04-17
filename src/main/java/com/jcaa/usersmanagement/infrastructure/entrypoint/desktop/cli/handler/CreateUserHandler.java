package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateUserRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.constant.UserEnumConstants;
@Log
@RequiredArgsConstructor
public final class CreateUserHandler implements OperationHandler {

  private final UserController userController;
  private final ConsoleIO console;
  private final UserResponsePrinter printer;

  @Override
  public void handle() {
    final String id       = console.readRequired("ID                              : ");
    final String name     = console.readRequired("Name                            : ");
    final String email    = console.readRequired("Email                           : ");
    final String password = console.readRequired("Password                        : ");
    final String rolePrompt = String.format("Role (%s / %s / %s): ",
      UserEnumConstants.ROLE_ADMIN,
      UserEnumConstants.ROLE_MEMBER,
      UserEnumConstants.ROLE_REVIEWER);
    final String role = console.readRequired(rolePrompt);

    try {
      final UserResponse created =
          userController.createUser(new CreateUserRequest(id, name, email, password, role));
      console.println("\n  User created successfully.");
      printer.print(created);
    } catch (final UserAlreadyExistsException exception) {
      log.warning("Intento de creacion de usuario fallido: usuario ya existe.");
      console.println("  Error: " + exception.getMessage());
    }
  }
}