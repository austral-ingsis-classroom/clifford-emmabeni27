package edu.austral.ingsis.clifford;

public final class Error implements Result {
  private final String message;

  public Error(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}