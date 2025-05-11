package edu.austral.ingsis.clifford;

public class ErrorOperation implements Operation<String> {
  private final String errorMessage;

  public ErrorOperation(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public Result execute(Component currentDirectory) {
    return new Error(errorMessage);
  }
}
