package edu.austral.ingsis.clifford;

public final class Touch implements Operation<String> {
  private final String name;

  public Touch(String name) {
    this.name = name;
  }

  @Override
  public Result execute(Component currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Not a directory");
    }

    for (char c : name.toCharArray()) {
      if (c == ' ' || c == '/') {
        return new Error("File name can contain neither ' ' nor /");
      }
    }

    Component newFile = new File(name, "File", currentDirectory);
    ((Directory) currentDirectory).addChild(newFile);
    return new Success("'" + name + "' file created");
  }
}
