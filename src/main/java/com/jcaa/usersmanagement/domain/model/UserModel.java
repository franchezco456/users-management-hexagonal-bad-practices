package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;

import lombok.Value;

// Clean Code - Regla 15 (inmutabilidad como preferencia de diseño):
// Se ha cambiado @Data por @Value para lograr verdadera inmutabilidad.
// Con @Value todos los campos son final y no hay setters generados.
// El estado solo puede cambiar mediante métodos que retornan una nueva instancia
// (como activate() y deactivate()), asegurando que las invariantes se respeten.
// Esto encapsula correctamente el estado y previene modificaciones no autorizadas.
@Value
public class UserModel {

  UserId id;
  UserName name;
  UserEmail email;
  UserPassword password;
  UserRole role;
  UserStatus status;

  public static UserModel create(
      final UserId id,
      final UserName name,
      final UserEmail email,
      final UserPassword password,
      final UserRole role) {
    return new UserModel(id, name, email, password, role, UserStatus.PENDING);
  }

  public UserModel activate() {
    return new UserModel(id, name, email, password, role, UserStatus.ACTIVE);
  }

  public UserModel deactivate() {
    return new UserModel(id, name, email, password, role, UserStatus.INACTIVE);
  }

  public boolean isAllowedToLogin() {
    return status == UserStatus.ACTIVE;
  }

  // Métodos delegadores para romper Ley de Deméter: no exponen los value objects internos
  public String getIdValue() {
    return id.value();
  }

  public String getNameValue() {
    return name.value();
  }

  public String getEmailValue() {
    return email.value();
  }

  public String getPasswordValue() {
    return password.value();
  }

  public boolean passwordMatches(final String plainPassword) {
    return password.verifyPlain(plainPassword);
  }

  public String getRoleDisplayName() {
    return role.name();
  }

  public String getStatusDisplayName() {
    return status.name();
  }

}
