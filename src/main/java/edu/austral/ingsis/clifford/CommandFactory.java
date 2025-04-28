package edu.austral.ingsis.clifford;

public interface CommandFactory {
  String commandName(); // devuelve la funcion, por ejemplo ls cd o touch

  FileSystemOperation<String> fromParts(String[] parts); // contruye objeto listo para ejecutar
}
