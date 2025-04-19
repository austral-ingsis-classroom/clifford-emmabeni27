package edu.austral.ingsis.clifford;

public class Cd implements FileSystemOperation<String> {

  String moveTo;

  public Cd(String moveTo) {
    this.moveTo = moveTo;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {

    if (!currentDirectory.isDirectory()) {
      return "Not a directory";
    }
    FileSystemComponent target = ResolvePath.resolvePath(moveTo, currentDirectory);

    if (target == null || !target.isDirectory()) {
      return "'" + moveTo + "' " + "directory does not exist";
    }

    return "moved to directory '" + target.getName() + "'";
  }
}
