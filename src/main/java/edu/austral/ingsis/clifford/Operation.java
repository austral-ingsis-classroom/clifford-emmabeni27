package edu.austral.ingsis.clifford;

public interface Operation<T> {
  Result execute(Component currentDirectory);
}
