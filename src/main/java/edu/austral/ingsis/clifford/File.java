package edu.austral.ingsis.clifford;

import java.util.Objects;

public final class File implements Component {

  private final String name;
  private Component parent;
  private final String type;

  public File(String name, String type, Component parent) {
    this.name = name;
    this.type = type;
    this.parent = parent;
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
    return Objects.equals(type, "Directory");
  }
}
