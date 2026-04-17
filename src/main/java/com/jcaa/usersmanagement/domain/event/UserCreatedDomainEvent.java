package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.model.UserModel;
import java.util.Map;
import lombok.Getter;

@Getter
public final class UserCreatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "user.created";

  private final UserModel user;

  public UserCreatedDomainEvent(final UserModel user) {
    super(EVENT_NAME);
    this.user = user;
  }

  @Override
  public Map<String, String> payload() {
    // Ley de Deméter: usar métodos delegadores en lugar de encadenamiento profundo
    return Map.of(
        "id", user.getIdValue(),
        "name", user.getNameValue(),
        "email", user.getEmailValue(),
        "role", user.getRoleDisplayName(),
        "status", user.getStatusDisplayName());
  }
}
