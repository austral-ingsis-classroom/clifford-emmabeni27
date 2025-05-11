package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Ls implements Operation<String> {

  private final String order;

  public Ls(String order) {
    this.order = order;
  }

  @Override
  public Result execute(Component currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Current node is not a directory. Cannot list content.");
    }

    Directory directory = (Directory) currentDirectory;
    List<String> names =
        new ArrayList<>(
            directory.getChildren().stream().map(Component::getName).collect(Collectors.toList()));

    if ("asc".equals(order)) {
      names.sort(String::compareTo);
    } else if ("desc".equals(order)) {
      names.sort(Comparator.reverseOrder());
    }

    return new Success(String.join(" ", names)); // une resultados
  }
}
