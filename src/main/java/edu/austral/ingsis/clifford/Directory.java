package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Directory implements Component {

  private final String name;
  private final String type;
  private Component parent;
  private final List<Component> children;

  public Directory(String name, String type, Component parent, List<Component> children) {
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
  public void setParent(Component newParent) {
    this.parent = newParent;
  }

  @Override
  public Component getParent() {
    return parent;
  }

  @Override
  public boolean isDirectory() {
    return true;
  }

  public void addChild(Component child) {
    children.add(child);
    child.setParent(this);
  }

  public void removeChild(Component child) {
    children.remove(child);
  }

  public List<Component> getChildren() {
    return Collections.unmodifiableList(new ArrayList<>(children));
  }
}
