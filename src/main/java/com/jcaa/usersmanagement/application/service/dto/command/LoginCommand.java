package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginCommand(
    @NotBlank(message = "email must not be blank")
        String email,
    @NotBlank(message = "password must not be blank")
        @Size(min = 8, message = "password must have at least 8 characters")
        String password)
{

}
