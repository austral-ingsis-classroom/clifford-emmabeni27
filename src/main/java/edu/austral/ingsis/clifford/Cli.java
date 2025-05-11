package edu.austral.ingsis.clifford;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Cli {

  private Component currentDirectory;
  private final Map<String, CommandFactory> commands;

  public Cli(Component rootDirectory, List<CommandFactory> factories) {
    this.currentDirectory = rootDirectory;
    this.commands =
        Collections.unmodifiableMap(
            factories.stream()
                .collect(Collectors.toMap(CommandFactory::commandName, factory -> factory)));
  }

  public String executeCommand(String command) {
    String[] parts = command.split(" ");
    String operation = parts[0];

    CommandFactory factory = commands.get(operation);
    if (factory == null) {
      return new Error("No result").getMessage();
    }

    Operation<String> operacion = factory.fromParts(parts);
    Result result = operacion.execute(currentDirectory);

    if ("cd".equals(operation) // cd afecta el estado del cli
        && result instanceof Success
        && result.getMessage().startsWith("moved to directory")) {
      String dirName = parts[1];
      Component newDir = ResolvePath.resolvePath(dirName, currentDirectory);
      if (newDir != null && newDir.isDirectory()) {
        currentDirectory = newDir;
      }
    }
    // REVISAR PQ ESTA APARTE
    return result.getMessage();
  }

  // If we need to provide access to the current directory
  public Component getCurrentDirectory() {
    return currentDirectory;
  }
}
