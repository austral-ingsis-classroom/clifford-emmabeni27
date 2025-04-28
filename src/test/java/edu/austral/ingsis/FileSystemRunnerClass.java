package edu.austral.ingsis;

import edu.austral.ingsis.clifford.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerClass implements FileSystemRunner {

  @Override
  public List<String> executeCommands(List<String> commands) {
    // Creamos el directorio raíz inmutable
    Directory rootDirectory = new Directory("/", "Directory", null, new ArrayList<>());

    // Cargamos las fábricas dinámicamente
    List<CommandFactory> factories = CommandFactoryLoader.load();

    // Creamos el CLI, pasándole el directorio raíz y las fábricas
    Cli cli = new Cli(rootDirectory, factories);

    List<String> results = new ArrayList<>();

    // Ejecutamos cada comando y guardamos el resultado
    for (String command : commands) {
      String result = cli.executeCommand(command);
      results.add(result);
    }

    return results;
  }
}
