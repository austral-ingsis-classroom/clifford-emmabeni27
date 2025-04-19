package edu.austral.ingsis.clifford;

public class Pwd implements FileSystemOperation<String> {
  @Override
  public String execute(FileSystemComponent currentDirectory) {
    StringBuilder path = new StringBuilder();

    FileSystemComponent dir = currentDirectory;

    while (dir.getParent() != null) {
      path.insert(0, "/" + dir.getName());
      dir = dir.getParent();
    }

    return path.toString();
  }
}
