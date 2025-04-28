package edu.austral.ingsis.clifford;

public interface FileSystemOperation<
    T> { // uso generics as[i el putput se adapta a la necesidad de cada operacio[n

  // Ejecución del comando
  T execute(FileSystemComponent currentDirectory);
}
// adt para result
// cli
