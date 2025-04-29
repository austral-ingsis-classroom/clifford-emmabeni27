package edu.austral.ingsis.clifford;

public interface FileSystemOperation<T> {
  Result execute(FileSystemComponent currentDirectory);
}
