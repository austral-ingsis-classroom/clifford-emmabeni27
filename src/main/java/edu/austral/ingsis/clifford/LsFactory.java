package edu.austral.ingsis.clifford;

public class LsFactory implements CommandFactory {
  @Override
  public String commandName() {
    return "ls";
  }

  @Override
  public Operation<String> fromParts(String[] parts) {
    String order = "none";
    if (parts.length > 1 && parts[1].startsWith("--ord")) {
      order = parts[1].split("=")[1];
    }
    return new Ls(order);
  }
}
