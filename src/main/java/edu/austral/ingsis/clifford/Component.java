package edu.austral.ingsis.clifford;

public interface Component {
  String getName();

  String getType();

  void setParent(Component parent);

  Component getParent();

  boolean isDirectory();
}
