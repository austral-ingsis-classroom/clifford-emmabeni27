package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

public final class Mkdir implements FileSystemOperation<String>, CommandFactory {
  private final String name;

  public Mkdir(String name) {
    this.name = name;
  }

  @Override
  public Result execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Not a directory");
    }

    for (char c : name.toCharArray()) {
      if (c == ' ' || c == '/') {
        return new Error("Directory name can contain neither ' ' nor /");
      }
    }

    List<FileSystemComponent> children = new ArrayList<>();
    FileSystemComponent newDirectory = new Directory(name, "Directory", currentDirectory, children);
    ((Directory) currentDirectory).addChild(newDirectory);
    return new Success("'" + name + "' directory created");
  }

  @Override
  public String commandName() {
    return "mkdir";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      return new ErrorOperation("Directory name required for 'mkdir' command");
    }

    String dirName = parts[1];
    return new Mkdir(dirName);
  }
}
