package edu.austral.ingsis.clifford;

public final class Pwd implements FileSystemOperation<String>, CommandFactory {

  public Pwd() {}

  @Override
  public Result execute(FileSystemComponent currentDirectory) {
    StringBuilder path = new StringBuilder();
    FileSystemComponent dir = currentDirectory;

    while (dir.getParent() != null) {
      path.insert(0, "/" + dir.getName());
      dir = dir.getParent();
    }

    return new Success(path.toString());
  }

  @Override
  public String commandName() {
    return "pwd";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    return new Pwd();
  }
}
