package edu.austral.ingsis.clifford;

public class Touch implements FileSystemOperation{
  String name;

  public Touch(String name){
    this.name=name;
  }

  @Override
  public void execute(FileSystemComponent currentDirectory) {

    if(!currentDirectory.isDirectory()){
      throw new RuntimeException("Not a directory");
    }
    for(char c : name.toCharArray()){
      if (c == ' ' || c == '/'){
        throw new IllegalArgumentException("File name can contain neither ' ' nor /");
      }
    }
    FileSystemComponent newFile = new File(name, "File", currentDirectory);
    ((Directory) currentDirectory).addChild(newFile);
    System.out.println("'" + name + "' file created");
  }
}
