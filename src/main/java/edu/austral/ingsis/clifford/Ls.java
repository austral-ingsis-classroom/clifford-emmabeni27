package edu.austral.ingsis.clifford;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ls implements FileSystemOperation<String>, CommandFactory {

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
    List<String> names =
        directory.getChildren().stream()
            .map(FileSystemComponent::getName)
            .collect(Collectors.toList());

    // Solo ordeno si se especifica --ord (asc/desc)
    if ("asc".equals(order)) {
      names.sort(String::compareTo);
    } else if ("desc".equals(order)) {
      names.sort(Comparator.reverseOrder());
    }
    // Si order es "none" o cualquier otro valor, no se ordena

    return String.join(" ", names);
  }

  @Override
  public String commandName() {
    return "ls"; // Nombre del comando
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    String order = "none";
    if (parts.length > 1 && parts[1].startsWith("--ord")) {
      order = parts[1].split("=")[1]; // Extraemos el valor después de --ord
    }
    return new Ls(order); // Creamos una nueva instancia de Ls con el parámetro `order`
  }
}
