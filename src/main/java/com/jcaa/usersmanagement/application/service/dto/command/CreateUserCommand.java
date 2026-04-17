package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CreateUserCommand(
    @NotBlank String id,
    @NotBlank
        @Size(min = 3)
        String name,
    @NotBlank
        @Email
        String email,
    @NotBlank
        @Size(min = 8)
        String password,
    @NotBlank String role)
{
}
