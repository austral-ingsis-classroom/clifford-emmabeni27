package edu.austral.ingsis.clifford;

public class Cd implements FileSystemOperation<String>, CommandFactory {

  private String moveTo;

  public Cd(String moveTo) {
    this.moveTo = moveTo;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {
    // el directorio acutal, es un directorio?
    if (!currentDirectory.isDirectory()) {
      return "Not a directory";
    }

    // intento resolver ruta
    FileSystemComponent target = ResolvePath.resolvePath(moveTo, currentDirectory);

    // no encuentro ruta o no es directorio
    if (target == null) {
      return "'" + moveTo + "' directory does not exist";
    }

    // target encontrado no es un directorio
    if (!target.isDirectory()) {
      return "'" + moveTo + "' is not a directory";
    }

    return "moved to directory '" + target.getName() + "'";
  }

  @Override
  public String commandName() {
    return "cd"; // Este es el nombre del comando asociado con esta fábrica
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    // tiene el parametro necesario para ese comando?
    if (parts.length < 2) {
      throw new IllegalArgumentException("Directory name required for 'cd' command");
    }

    String dirName = parts[1]; // directorio al que me quiero mover
    return new Cd(dirName); // creo operacion con nombre del directorio
  }
}
