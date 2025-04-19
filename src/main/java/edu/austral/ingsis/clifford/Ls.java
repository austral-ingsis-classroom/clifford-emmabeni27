package edu.austral.ingsis.clifford;

import java.util.List;
import java.util.stream.Collectors;

public class Ls implements FileSystemOperation<String> {

  public String order;

  public Ls(String order) {
    this.order = order;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return "Current node is not a directory. Cannot list content.";
    }
    Directory directory = (Directory) currentDirectory;
    List<FileSystemComponent> children = directory.getChildren();

    if (children.isEmpty()) {
      return "";
    }

    if (order.equals("asc")) {
      children.sort((a, b) -> a.getName().compareTo(b.getName()));
    } else if (order.equals("desc")) {
      children.sort((a, b) -> b.getName().compareTo(a.getName()));
    }
    //si hago un for para iterar sobre nodos con un return, corta en el primer return y no devuelve el resto :(
    return children.stream()
      .map(FileSystemComponent::getName)
      .collect(Collectors.joining(" "));
  }
}

