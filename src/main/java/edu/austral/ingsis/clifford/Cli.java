package edu.austral.ingsis.clifford;

import java.util.Arrays;

public class Cli {

  private FileSystemComponent currentDirectory;

  public Cli(FileSystemComponent rootDirectory) {
    this.currentDirectory = rootDirectory;
  }

  public String executeCommand(String command) {
    String[] parts = command.split(" ");
    String operation = parts[0];

    try {
      switch (operation) {
        case "ls":
          return handleLs(parts);
        case "cd":
          return handleCd(parts);
        case "touch":
          return handleTouch(parts);
        case "mkdir":
          return handleMkdir(parts);
        case "rm":
          return handleRm(parts);
        case "pwd":
          return handlePwd();
        default:
          return "Unknown command: " + operation;
      }
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }

  private String handleLs(String[] parts) {
    String order = "none";
    if (parts.length > 1 && parts[1].startsWith("--ord")) {
      order = parts[1].split("=")[1];
    }

    FileSystemOperation<String> lsOperation = new Ls(order);
    return lsOperation.execute(currentDirectory);
  }

  private String handleCd(String[] parts) {
    if (parts.length < 2) {
      return "Directory name required";
    }

    String dirName = parts[1];
    FileSystemOperation<String> cdOperation = new Cd(dirName);
    String result = cdOperation.execute(currentDirectory);

    // Si el resultado es exitoso, actualizar el directorio actual
    if (!result.startsWith("Error") && !result.contains("does not exist")) {
      FileSystemComponent target = ResolvePath.resolvePath(dirName, currentDirectory);
      if (target != null) {
        this.currentDirectory = target;
      }
    }

    return result;
  }

  private String handleTouch(String[] parts) {
    if (parts.length < 2) {
      return "File name required";
    }

    String fileName = parts[1];
    FileSystemOperation<String> touchOperation = new Touch(fileName);
    return touchOperation.execute(currentDirectory);
  }

  private String handleMkdir(String[] parts) {
    if (parts.length < 2) {
      return "Directory name required";
    }

    String dirName = parts[1];
    FileSystemOperation<String> mkdirOperation = new Mkdir(dirName);
    return mkdirOperation.execute(currentDirectory);
  }

  private String handleRm(String[] parts) {
    if (parts.length < 2) {
      return "File/Directory name required";
    }

    boolean recursive = Arrays.asList(parts).contains("--recursive");

    // busco el argumento que NO es --recursive asumiendo que es el nombre que me interesa
    String name =
        Arrays.stream(parts)
            .filter(part -> !part.equals("--recursive") && !part.equals("rm"))
            .findFirst()
            .orElse(null);

    if (name == null) {
      return "File/Directory name required";
    }

    FileSystemOperation<String> rmOperation = new Rm(name, recursive);
    return rmOperation.execute(currentDirectory);
  }

  private String handlePwd() {
    FileSystemOperation<String> pwdOperation = new Pwd();
    return pwdOperation.execute(currentDirectory);
  }
}
