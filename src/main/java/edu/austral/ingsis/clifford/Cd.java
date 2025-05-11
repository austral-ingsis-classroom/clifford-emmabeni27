package edu.austral.ingsis.clifford;

public final class Cd implements Operation<String> {

  private final String moveTo;

  public Cd(String moveTo) {
    this.moveTo = moveTo;
  }

  @Override
  public Result execute(Component currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Not a directory");
    }

    Component target = ResolvePath.resolvePath(moveTo, currentDirectory);

    if (target == null) {
      return new Error("'" + moveTo + "' directory does not exist");
    }

    if (!target.isDirectory()) {
      return new Error("'" + moveTo + "' is not a directory");
    }

    return new Success("moved to directory '" + target.getName() + "'");
  }
}
