package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.service.dto.command.LoginCommand;
import com.jcaa.usersmanagement.domain.exception.InvalidCredentialsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class LoginService implements LoginUseCase {

  private final GetUserByEmailPort getUserByEmailPort;
  private final Validator validator;

  @Override
  public UserModel execute(final LoginCommand command) {
    validateCommand(command);

    final UserEmail email = new UserEmail(command.email());

    final UserModel user = findUserByEmail(email);
    verifyPassword(user, command.password());
    validateUserStatus(user);

    return user;
  }

  private UserModel findUserByEmail(final UserEmail email) {
    final UserModel user = getUserByEmailPort.getByEmail(email).orElse(null);
    if (user == null) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
    return user;
  }

  private void verifyPassword(final UserModel user, final String plainPassword) {
    // Clean Code - Regla 14: acceso profundo a internals del value object.
    // user -> getPassword() -> verifyPlain() en lugar de delegar con user.passwordMatches(plain).
    if (!user.getPassword().verifyPlain(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
  }

  private void validateUserStatus(final UserModel user) {
    if (!user.isAllowedToLogin()) {
      throw InvalidCredentialsException.becauseUserIsNotActive();
    }
  }

  private void validateCommand(final LoginCommand command) {
    final Set<ConstraintViolation<LoginCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
