package edu.austral.ingsis.clifford;

public interface CommandFactory {
  String commandName();

  FileSystemOperation<String> fromParts(String[] parts);
}