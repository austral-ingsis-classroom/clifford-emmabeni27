package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Directory implements FileSystemComponent {

  private final String name;
  private final String type;
  private FileSystemComponent parent;
  private final List<FileSystemComponent> children;

  public Directory(
    String name, String type, FileSystemComponent parent, List<FileSystemComponent> children) {
    this.name = name;
    this.type = type;
    this.parent = parent;
    this.children = new ArrayList<>(children);
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
    this.parent = newParent;
  }

  @Override
  public FileSystemComponent getParent() {
    return parent;
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  public void addChild(FileSystemComponent child) {
    children.add(child);
    child.setParent(this);
  }

  public void removeChild(FileSystemComponent child) {
    children.remove(child);
  }

  public List<FileSystemComponent> getChildren() {
    return Collections.unmodifiableList(new ArrayList<>(children));
  }
}