package edu.austral.ingsis.clifford;

public class CdFactory implements CommandFactory {

  @Override
  public String commandName() {
    return "cd";
  }

  @Override
  public Operation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      return new ErrorOperation("Directory name required for 'cd' command");
    }

    String dirName = parts[1];
    return new Cd(dirName);
  }
}
