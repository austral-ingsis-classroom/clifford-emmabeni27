package edu.austral.ingsis.clifford;

public class Pwd implements FileSystemOperation {
  @Override
  public void execute(FileSystemComponent currentDirectory) {
    StringBuilder path = new StringBuilder();

    FileSystemComponent dir = currentDirectory;


    while (dir.getParent() != null) {
      path.insert(0, "/" + dir.getName());
      dir = dir.getParent();
    }


    System.out.println(path.toString());
  }
}
