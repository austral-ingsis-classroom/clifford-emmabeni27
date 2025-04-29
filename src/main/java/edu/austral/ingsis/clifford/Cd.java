package edu.austral.ingsis.clifford;

public final class Cd implements FileSystemOperation<String>, CommandFactory {

  private final String moveTo;

  public Cd(String moveTo) {
    this.moveTo = moveTo;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return "Not a directory";
    }

    FileSystemComponent target = ResolvePath.resolvePath(moveTo, currentDirectory);

    if (target == null) {
      return "'" + moveTo + "' directory does not exist";
    }

    if (!target.isDirectory()) {
      return "'" + moveTo + "' is not a directory";
    }

    return "moved to directory '" + target.getName() + "'";
  }

  @Override
  public String commandName() {
    return "cd";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      throw new IllegalArgumentException("Directory name required for 'cd' command");
    }

    String dirName = parts[1];
    return new Cd(dirName);
  }
}