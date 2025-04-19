package edu.austral.ingsis.clifford;

import java.util.Objects;

public class File implements FileSystemComponent {
  String name;
  String type;
  FileSystemComponent parent;

  public File(String name, String type, FileSystemComponent parent) {
    this.name = name;
    this.type = type;
    this.parent = parent;
  }

  // no necesito el actualDir, lo puedo reconstruir con get parent

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setParent(FileSystemComponent newParent) {
    parent = newParent;
  }

  @Override
  public FileSystemComponent getParent() {
    return parent;
  }

  @Override
  public boolean isDirectory() {
    return Objects.equals(type, "Directory");
  }
}
