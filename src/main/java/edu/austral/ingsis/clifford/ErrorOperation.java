package edu.austral.ingsis.clifford;

public class ErrorOperation implements FileSystemOperation<String> {
  private final String errorMessage;

  public ErrorOperation(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public Result execute(FileSystemComponent currentDirectory) {
    return new Error(errorMessage);
  }
}
