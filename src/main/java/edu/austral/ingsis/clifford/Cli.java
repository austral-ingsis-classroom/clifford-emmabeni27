package edu.austral.ingsis.clifford;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cli {

  private FileSystemComponent currentDirectory;
  private final Map<String, CommandFactory> commands;

  public Cli(FileSystemComponent rootDirectory, List<CommandFactory> factories) {
    this.currentDirectory = rootDirectory;
    this.commands =
        factories.stream()
            .collect(Collectors.toMap(CommandFactory::commandName, factory -> factory));
  }

  public String executeCommand(String command) {
    String[] parts = command.split(" ");
    String operation = parts[0];

    CommandFactory factory = commands.get(operation);
    if (factory == null) {
      throw new IllegalArgumentException("No result");
    }

    FileSystemOperation operacion = factory.fromParts(parts);
    String result = (String) operacion.execute(currentDirectory);

    // Si el comando es cd y fue exitoso, actualiza el directorio actual
    if ("cd".equals(operation) && result.startsWith("moved to directory")) {
      // Necesitamos resolver la ruta nuevamente para obtener el componente
      String dirName = parts[1];
      FileSystemComponent newDir = ResolvePath.resolvePath(dirName, currentDirectory);
      if (newDir != null && newDir.isDirectory()) {
        currentDirectory = newDir;
      }
    }

    return result;
  }
}
// recibe comando como string
// descompone compando
// segun la funcion, llama a la correspondiente
// handle: manejos de error
