package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.config.UserControllerProvider;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Clean Code - Regla 24 (consistencia semántica):
// Todo el proyecto usa java.util.logging.Logger (vía @Log de Lombok o Logger.getLogger()),
// pero esta clase usa org.slf4j.Logger + LoggerFactory de una librería diferente.
// El mismo concepto —"logger de la aplicación"— se resuelve con dos frameworks distintos
// sin justificación. Un lector no puede saber cuál es el estándar del proyecto.
// La regla dice: las mismas ideas deben resolverse igual en todo el proyecto.
//
// Clean Code - Regla 22 (código difícil de borrar y refactorizar):
// main() está acoplado directamente a tres clases concretas: DependencyContainer,
// UserManagementCli y ConsoleIO. Si se quiere reemplazar cualquiera de ellas
// (p. ej., cambiar el entrypoint de CLI a GUI), hay que editar el punto de entrada
// de la aplicación. No hay ninguna abstracción que proteja este acoplamiento.
public final class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) {
    log.info("Starting Users Management System...");
    try (final Scanner scanner = new Scanner(System.in)) {
      final UserControllerProvider container = buildContainer();
      final ConsoleIO consoleIO = buildConsole(scanner);
      final UserManagementCli cli = buildCli(container, consoleIO);
      run(cli);
    }
  }

  private static UserControllerProvider buildContainer() {
    return new DependencyContainer();
  }

  private static ConsoleIO buildConsole(final Scanner scanner) {
    return new ConsoleIO(scanner, System.out);
  }

  private static UserManagementCli buildCli(
      final UserControllerProvider container, final ConsoleIO consoleIO) {
    return new UserManagementCli(container.userController(), consoleIO);
  }

  private static void run(final UserManagementCli cli) {
    cli.start();
  }
}