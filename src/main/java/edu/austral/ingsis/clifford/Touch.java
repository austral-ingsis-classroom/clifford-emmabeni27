package edu.austral.ingsis.clifford;

public final class Touch implements FileSystemOperation<String>, CommandFactory {
  private final String name;

  public Touch(String name) {
    this.name = name;
  }

  @Override
  public Result execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Not a directory");
    }

    for (char c : name.toCharArray()) {
      if (c == ' ' || c == '/') {
        return new Error("File name can contain neither ' ' nor /");
      }
    }

    FileSystemComponent newFile = new File(name, "File", currentDirectory);
    ((Directory) currentDirectory).addChild(newFile);
    return new Success("'" + name + "' file created");
  }

  @Override
  public String commandName() {
    return "touch";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      return new ErrorOperation("File name required for 'touch' command");
    }

    String fileName = parts[1];
    return new Touch(fileName);
  }
}
