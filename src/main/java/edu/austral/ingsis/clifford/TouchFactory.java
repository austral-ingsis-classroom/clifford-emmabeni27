package edu.austral.ingsis.clifford;

public class TouchFactory implements CommandFactory {
  @Override
  public String commandName() {
    return "touch";
  }

  @Override
  public Operation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      return new ErrorOperation("File name required for 'touch' command");
    }

    String fileName = parts[1];
    return new Touch(fileName);
  }
}
