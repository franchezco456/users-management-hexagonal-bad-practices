package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateUserRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.constant.UserEnumConstants;

@RequiredArgsConstructor
public final class UpdateUserHandler implements OperationHandler {

  private final UserController userController;
  private final ConsoleIO console;
  private final UserResponsePrinter printer;

  @Override
  public void handle() {
    final String id   = console.readRequired("User ID                                       : ");
    final String name = console.readRequired("New name                                      : ");
    final String email= console.readRequired("New email                                     : ");
    final String password = console.readOptional("New password (leave blank to keep current)    : ");
    final String rolePrompt = String.format("Role   (%s / %s / %s)            : ",
      UserEnumConstants.ROLE_ADMIN,
      UserEnumConstants.ROLE_MEMBER,
      UserEnumConstants.ROLE_REVIEWER);
    final String statusPrompt = String.format("Status (%s / %s / %s / %s): ",
      UserEnumConstants.STATUS_ACTIVE,
      UserEnumConstants.STATUS_INACTIVE,
      UserEnumConstants.STATUS_PENDING,
      UserEnumConstants.STATUS_BLOCKED);
    final String role = console.readRequired(rolePrompt);
    final String status = console.readRequired(statusPrompt);

    try {
      final UserResponse updatedUser = userController.updateUser(
          new UpdateUserRequest(
              id,
              name,
              email,
              password.isBlank() ? null : password,
              role,
              status));
      console.println("\n  User updated successfully.");
      printer.print(updatedUser);
    } catch (final UserNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}