package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.constant;

/**
 * Constantes para nombres de enums de usuario.
 * Regla 18: Evitar magic strings y literales sin nombre.
 */
public final class UserEnumConstants {

  // Roles
  public static final String ROLE_ADMIN = "ADMIN";
  public static final String ROLE_MEMBER = "MEMBER";
  public static final String ROLE_REVIEWER = "REVIEWER";

  // Statuses
  public static final String STATUS_ACTIVE = "ACTIVE";
  public static final String STATUS_INACTIVE = "INACTIVE";
  public static final String STATUS_PENDING = "PENDING";
  public static final String STATUS_BLOCKED = "BLOCKED";

  private UserEnumConstants() {
    throw new UnsupportedOperationException("Utility class");
  }
}
