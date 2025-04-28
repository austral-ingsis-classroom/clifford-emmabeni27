package edu.austral.ingsis.clifford;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Ls implements FileSystemOperation<String>, CommandFactory {

  private final String order; // Campo final

  public Ls(String order) {
    this.order = order;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return "Current node is not a directory. Cannot list content.";
    }

    Directory directory = (Directory) currentDirectory;
    List<String> names =
        directory.getChildren().stream()
            .map(FileSystemComponent::getName)
            .collect(Collectors.toList());

    if ("asc".equals(order)) {
      names.sort(String::compareTo);
    } else if ("desc".equals(order)) {
      names.sort(Comparator.reverseOrder());
    }

    return String.join(" ", names);
  }

  @Override
  public String commandName() {
    return "ls";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    String order = "none";
    if (parts.length > 1 && parts[1].startsWith("--ord")) {
      order = parts[1].split("=")[1];
    }
    return new Ls(order);
  }
}
