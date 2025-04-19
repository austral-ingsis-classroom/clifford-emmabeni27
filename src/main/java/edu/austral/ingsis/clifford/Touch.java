package edu.austral.ingsis.clifford;

public class Touch implements FileSystemOperation<String>{
  String name;

  public Touch(String name) {
    this.name = name;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {

    if (!currentDirectory.isDirectory()) {
      return "Not a directory";
    }
    for (char c : name.toCharArray()) {
      if (c == ' ' || c == '/') {
        return "File name can contain neither ' ' nor /";
      }
    }
    FileSystemComponent newFile = new File(name, "File", currentDirectory);
    ((Directory) currentDirectory).addChild(newFile);
    return ("'" + name + "' file created");
  }
}
