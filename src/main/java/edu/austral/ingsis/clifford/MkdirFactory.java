package edu.austral.ingsis.clifford;

public class MkdirFactory implements CommandFactory {
  @Override
  public String commandName() {
    return "mkdir";
  }

  @Override
  public Operation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      return new ErrorOperation("Directory name required for 'mkdir' command");
    }

    String dirName = parts[1];
    return new Mkdir(dirName);
  }
}
