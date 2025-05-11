package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

public final class Mkdir implements Operation<String> {
  private final String name;

  public Mkdir(String name) {
    this.name = name;
  }

  @Override
  public Result execute(Component currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Not a directory");
    }

    for (char c : name.toCharArray()) {
      if (c == ' ' || c == '/') {
        return new Error("Directory name can contain neither ' ' nor /");
      }
    }

    List<Component> children = new ArrayList<>();
    Component newDirectory = new Directory(name, "Directory", currentDirectory, children);
    ((Directory) currentDirectory).addChild(newDirectory);
    return new Success("'" + name + "' directory created");
  }
}
