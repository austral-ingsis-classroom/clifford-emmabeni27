package edu.austral.ingsis.clifford;

import java.util.List;

public class Ls implements FileSystemOperation {

  public String order;

  public Ls(String order) {
    this.order = order;
  }

  @Override
  public void execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      throw new RuntimeException("Current node is not a directory. Cannot list content.");
    }
    Directory directory = (Directory) currentDirectory;
    List<FileSystemComponent> children = directory.getChildren();

    if (order.equals("asc")) {
      children.sort((a, b) -> a.getName().compareTo(b.getName()));
    } else if (order.equals("desc")) {
      children.sort((a, b) -> b.getName().compareTo(a.getName()));
    } else {
      for (FileSystemComponent child : children) {
        System.out.println(child.getName());
      }
    }
  }
}

