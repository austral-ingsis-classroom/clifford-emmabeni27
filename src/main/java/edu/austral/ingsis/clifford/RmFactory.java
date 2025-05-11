package edu.austral.ingsis.clifford;

public class RmFactory implements CommandFactory {
  @Override
  public String commandName() {
    return "rm";
  }

  @Override
  public Operation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      return new ErrorOperation("File/Directory name required for 'rm' command");
    }

    boolean recursive = false;
    String name = null;

    for (int i = 1; i < parts.length; i++) {
      if ("--recursive".equals(parts[i])) {
        recursive = true;
      } else {
        name = parts[i];
      }
    }

    if (name == null) {
      return new ErrorOperation("File/Directory name required for 'rm' command");
    }

    return new Rm(name, recursive);
  }
}
