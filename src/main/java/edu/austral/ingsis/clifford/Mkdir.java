package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

public class Mkdir implements FileSystemOperation {
  String name;

  public Mkdir(String name) {
    this.name = name;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {

    if (!currentDirectory.isDirectory()) {
      throw new RuntimeException("Not a directory");
    }
    for (char c : name.toCharArray()) {
      if (c == ' ' || c == '/') {
        throw new IllegalArgumentException("Directory name can contain neither ' ' nor /");
      }
    }
    List<FileSystemComponent> children = new ArrayList<>();
    FileSystemComponent newDirectory = new Directory(name, "Directory", currentDirectory, children);
    ((Directory) currentDirectory).addChild(newDirectory);
    return "'" + name + "' directory created";
  }
}
