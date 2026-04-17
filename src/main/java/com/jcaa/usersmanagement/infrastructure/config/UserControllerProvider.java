package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;

public interface UserControllerProvider {

  UserController userController();
}
