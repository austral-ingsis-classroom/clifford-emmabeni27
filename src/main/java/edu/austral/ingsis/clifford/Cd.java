package edu.austral.ingsis.clifford;

public class Cd implements FileSystemOperation{

  String moveTo;

  public Cd(String moveTo){
    this.moveTo = moveTo;
  }

  @Override
  public void execute(FileSystemComponent currentDirectory) {

    if (!currentDirectory.isDirectory()) {
      throw new RuntimeException("Not a directory");
    }
    FileSystemComponent target = ResolvePath.resolvePath(moveTo, currentDirectory);

    if (target == null || !target.isDirectory()) {
      throw new RuntimeException("Directory not found: " + moveTo);
    }

    System.out.println("Moved to directory: '" + target.getName() + "'");
  }

  }

