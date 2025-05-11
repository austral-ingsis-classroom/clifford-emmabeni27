package edu.austral.ingsis.clifford;

public class PwdFactory implements CommandFactory {
  @Override
  public String commandName() {
    return "pwd";
  }

  @Override
  public Operation<String> fromParts(String[] parts) {
    return new Pwd();
  }
}
