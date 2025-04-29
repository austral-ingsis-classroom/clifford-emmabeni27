package edu.austral.ingsis.clifford;

public final class Cd implements FileSystemOperation<String>, CommandFactory {

  private final String moveTo;

  public Cd(String moveTo) {
    this.moveTo = moveTo;
  }

  @Override
  public Result execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Not a directory");
    }

    FileSystemComponent target = ResolvePath.resolvePath(moveTo, currentDirectory);

    if (target == null) {
      return new Error("'" + moveTo + "' directory does not exist");
    }

    if (!target.isDirectory()) {
      return new Error("'" + moveTo + "' is not a directory");
    }

    return new Success("moved to directory '" + target.getName() + "'");
  }

  @Override
  public String commandName() {
    return "cd";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      return new ErrorOperation("Directory name required for 'cd' command");
    }

    String dirName = parts[1];
    return new Cd(dirName);
  }
}
