package edu.austral.ingsis.clifford;

public class Success implements Result {
  private final String message;

  public Success(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
