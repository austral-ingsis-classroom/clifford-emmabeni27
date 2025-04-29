package edu.austral.ingsis.clifford;

public final class Touch implements FileSystemOperation<String>, CommandFactory {
  private final String name;

  public Touch(String name) {
    this.name = name;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return "Not a directory";
    }

    for (char c : name.toCharArray()) {
      if (c == ' ' || c == '/') {
        return "File name can contain neither ' ' nor /";
      }
    }

    FileSystemComponent newFile = new File(name, "File", currentDirectory);
    ((Directory) currentDirectory).addChild(newFile);
    return ("'" + name + "' file created");
  }

  @Override
  public String commandName() {
    return "touch";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      throw new IllegalArgumentException("File name required for 'touch' command");
    }

    String fileName = parts[1];
    return new Touch(fileName);
  }
}