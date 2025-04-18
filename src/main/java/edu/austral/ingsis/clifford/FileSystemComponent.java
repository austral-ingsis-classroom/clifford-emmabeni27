package edu.austral.ingsis.clifford;

public interface FileSystemComponent {
    String getName();
    String getType(); // "file" o "directory"

    void setParent(FileSystemComponent parent);
    FileSystemComponent getParent();

    boolean isDirectory();
}
