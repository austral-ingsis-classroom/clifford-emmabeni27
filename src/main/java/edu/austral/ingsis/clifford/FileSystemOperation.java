package edu.austral.ingsis.clifford;

public interface FileSystemOperation<T> {
  T execute(FileSystemComponent currentDirectory);
}