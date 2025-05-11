package edu.austral.ingsis.clifford;

public interface CommandFactory {
  String commandName();

  Operation<String> fromParts(String[] parts);
}
