package edu.austral.ingsis;

import edu.austral.ingsis.clifford.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunnerClass implements FileSystemRunner {

  @Override
  public List<String> executeCommands(List<String> commands) {
    // creo sistema de archivos con raiz
    Directory rootDirectory = new Directory("/", "Directory", null, new ArrayList<>());
    Cli cli = new Cli(rootDirectory);

    List<String> results = new ArrayList<>();

    // ejecuto cada comando y guardo el resultado
    for (String command : commands) {
      String result = cli.executeCommand(command);
      results.add(result);
    }

    return results;
  }
}
