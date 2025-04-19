package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Directory implements FileSystemComponent {

  String name;
  String type;
  FileSystemComponent parent;
  private List<FileSystemComponent> children = new ArrayList<>();

  public Directory(
      String name, String type, FileSystemComponent parent, List<FileSystemComponent> children) {
    this.name = name;
    this.type = type;
    this.parent = parent;
    this.children = children;
  }

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

  public void addChild(FileSystemComponent child) {
    children.add(child);
    child.setParent(this); // el nodo debe tener un parent! se lo asigno en esta linea
  }

  public void removeChild(FileSystemComponent child) {
    children.remove(child);
  }

  public List<FileSystemComponent> getChildren() {
    return new ArrayList<>(children);
  }
}
