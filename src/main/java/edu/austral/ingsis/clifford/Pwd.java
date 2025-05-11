package edu.austral.ingsis.clifford;

public final class Pwd implements Operation<String> {

  public Pwd() {}

  @Override
  public Result execute(Component currentDirectory) {
    StringBuilder path = new StringBuilder();
    Component dir = currentDirectory;

    while (dir.getParent() != null) {
      path.insert(0, "/" + dir.getName());
      dir = dir.getParent();
    }

    return new Success(path.toString());
  }
}
