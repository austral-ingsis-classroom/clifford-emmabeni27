package edu.austral.ingsis.clifford;

import java.util.Arrays;
import java.util.List;

public class Cli {

  private FileSystemComponent currentDirectory;

  public Cli(FileSystemComponent rootDirectory) {
    this.currentDirectory = rootDirectory;  // Iniciamos con el directorio raíz
  }

  public void executeCommand(String command) {
    String[] parts = command.split(" ");  // Separar por espacios
    String operation = parts[0];  // El primer parámetro es el nombre de la operación

    switch (operation) {
      case "ls":
        handleLs(parts);
        break;

      case "cd":
        handleCd(parts);
        break;

      case "touch":
        handleTouch(parts);
        break;

      case "mkdir":
        handleMkdir(parts);
        break;

      case "rm":
        handleRm(parts);
        break;

      case "pwd":
        handlePwd();
        break;

      default:
        System.out.println("Unknown command: " + operation);
        break;
    }
  }

  private void handleLs(String[] parts) {
    String order = "none";  // Valor por defecto
    if (parts.length > 1 && parts[1].startsWith("--ord")) {
      order = parts[1].split("=")[1];  // Obtiene "asc" o "desc"
    }

    FileSystemOperation lsOperation = new Ls(order);
    lsOperation.execute(currentDirectory);
  }

  private void handleCd(String[] parts) {
    if (parts.length < 2) {
      System.out.println("Directory name required");
      return;
    }

    String dirName = parts[1];
    FileSystemOperation cdOperation = new Cd(dirName);
    cdOperation.execute(currentDirectory);
  }

  private void handleTouch(String[] parts) {
    if (parts.length < 2) {
      System.out.println("File name required");
      return;
    }

    String fileName = parts[1];
    FileSystemOperation touchOperation = new Touch(fileName);
    touchOperation.execute(currentDirectory);
  }

  private void handleMkdir(String[] parts) {
    if (parts.length < 2) {
      System.out.println("Directory name required");
      return;
    }

    String dirName = parts[1];
    FileSystemOperation mkdirOperation = new Mkdir(dirName);
    mkdirOperation.execute(currentDirectory);
  }

  private void handleRm(String[] parts) {
    if (parts.length < 2) {
      System.out.println("File/Directory name required");
      return;
    }

    String name = parts[1];
    boolean recursive = Arrays.asList(parts).contains("--recursive");  // Verificar si contiene --recursive
    FileSystemOperation rmOperation = new Rm(name, recursive);
    rmOperation.execute(currentDirectory);
  }

  private void handlePwd() {
    FileSystemOperation pwdOperation = new Pwd();
    pwdOperation.execute(currentDirectory);
  }
}
