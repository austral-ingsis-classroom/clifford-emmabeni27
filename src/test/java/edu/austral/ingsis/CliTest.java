package edu.austral.ingsis.clifford;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CliTest {
  private Cli cli;
  private FileSystemComponent rootDirectory;

  @BeforeEach
  public void setUp() {
    // Crear una estructura de directorios básica para las pruebas
    List<FileSystemComponent> rootChildren = new ArrayList<>();
    rootDirectory = new Directory("root", "Directory", null, rootChildren);
    List<CommandFactory> factories = CommandFactoryLoader.load();
    cli = new Cli(rootDirectory, factories);
  }

  @Test
  public void testExecuteCommand_mkdir() {
    // Ejecutar el comando mkdir
    String result = cli.executeCommand("mkdir test_dir");
    assertEquals("'test_dir' directory created", result);

    // Verificar que el directorio se haya creado
    Directory root = (Directory) rootDirectory;
    boolean found =
        root.getChildren().stream()
            .anyMatch(child -> "test_dir".equals(child.getName()) && child.isDirectory());
    assertTrue(found);
  }

  @Test
  public void testExecuteCommand_touch() {
    // Ejecutar el comando touch
    String result = cli.executeCommand("touch test_file");
    assertEquals("'test_file' file created", result);

    // Verificar que el archivo se haya creado
    Directory root = (Directory) rootDirectory;
    boolean found =
        root.getChildren().stream()
            .anyMatch(child -> "test_file".equals(child.getName()) && !child.isDirectory());
    assertTrue(found);
  }

  @Test
  public void testExecuteCommand_ls() {
    // Crear algunos archivos y directorios
    cli.executeCommand("mkdir dir1");
    cli.executeCommand("mkdir dir2");
    cli.executeCommand("touch file1");

    // Ejecutar el comando ls
    String result = cli.executeCommand("ls");
    assertTrue(result.contains("dir1"));
    assertTrue(result.contains("dir2"));
    assertTrue(result.contains("file1"));
  }

  @Test
  public void testExecuteCommand_ls_withOrder() {
    // Crear algunos archivos y directorios
    cli.executeCommand("mkdir dir1");
    cli.executeCommand("mkdir dir2");
    cli.executeCommand("touch file1");

    // Ejecutar el comando ls con orden ascendente
    String resultAsc = cli.executeCommand("ls --ord=asc");
    String[] partsAsc = resultAsc.split(" ");
    // Verificar que estén en orden ascendente
    for (int i = 0; i < partsAsc.length - 1; i++) {
      assertTrue(partsAsc[i].compareTo(partsAsc[i + 1]) <= 0);
    }

    // Ejecutar el comando ls con orden descendente
    String resultDesc = cli.executeCommand("ls --ord=desc");
    String[] partsDesc = resultDesc.split(" ");
    // Verificar que estén en orden descendente
    for (int i = 0; i < partsDesc.length - 1; i++) {
      assertTrue(partsDesc[i].compareTo(partsDesc[i + 1]) >= 0);
    }
  }

  @Test
  public void testExecuteCommand_cd() {
    // Crear un directorio y cambiar a él
    cli.executeCommand("mkdir test_dir");
    String result = cli.executeCommand("cd test_dir");
    assertEquals("moved to directory 'test_dir'", result);

    // Verificar que el directorio actual ha cambiado
    assertEquals("test_dir", cli.getCurrentDirectory().getName());
  }

  @Test
  public void testExecuteCommand_cd_notFound() {
    // Intentar cambiar a un directorio que no existe
    String result = cli.executeCommand("cd non_existent_dir");
    assertEquals("'non_existent_dir' directory does not exist", result);
  }

  @Test
  public void testExecuteCommand_cd_notDirectory() {
    // Crear un archivo y tratar de cd a él
    cli.executeCommand("touch test_file");
    String result = cli.executeCommand("cd test_file");
    assertEquals("'test_file' is not a directory", result);
  }

  @Test
  public void testExecuteCommand_pwd() {
    // En el directorio raíz, pwd debería dar una cadena vacía o "/"
    String result = cli.executeCommand("pwd");
    assertTrue(result.isEmpty() || result.equals("/"));

    // Crear un directorio, cd a él y verificar pwd
    cli.executeCommand("mkdir test_dir");
    cli.executeCommand("cd test_dir");
    result = cli.executeCommand("pwd");
    assertTrue(result.contains("/test_dir"));
  }

  @Test
  public void testExecuteCommand_rm() {
    // Crear un archivo y eliminarlo
    cli.executeCommand("touch test_file");
    String result = cli.executeCommand("rm test_file");
    assertEquals("'test_file' removed", result);

    // Verificar que el archivo ya no existe
    Directory root = (Directory) rootDirectory;
    boolean found =
        root.getChildren().stream().anyMatch(child -> "test_file".equals(child.getName()));
    assertFalse(found);
  }

  @Test
  public void testExecuteCommand_rm_directory() {
    // Crear un directorio
    cli.executeCommand("mkdir test_dir");

    // Intentar eliminarlo sin --recursive
    String result = cli.executeCommand("rm test_dir");
    assertEquals("cannot remove 'test_dir', is a directory", result);

    // Verificar que el directorio aún existe
    Directory root = (Directory) rootDirectory;
    boolean found =
        root.getChildren().stream().anyMatch(child -> "test_dir".equals(child.getName()));
    assertTrue(found);

    // Eliminarlo con --recursive
    result = cli.executeCommand("rm --recursive test_dir");
    assertEquals("'test_dir' removed", result);

    // Verificar que el directorio ya no existe
    found = root.getChildren().stream().anyMatch(child -> "test_dir".equals(child.getName()));
    assertFalse(found);
  }

  @Test
  public void testExecuteCommand_invalidCommand() {
    // Intentar ejecutar un comando que no existe
    String result = cli.executeCommand("invalid_command");
    assertEquals("No result", result);
  }

  @Test
  public void testExecuteCommand_invalidParameters() {
    // Intentar ejecutar mkdir sin parámetros
    String result = cli.executeCommand("mkdir");
    assertEquals("Directory name required for 'mkdir' command", result);

    // Intentar ejecutar touch sin parámetros
    result = cli.executeCommand("touch");
    assertEquals("File name required for 'touch' command", result);

    // Intentar ejecutar cd sin parámetros
    result = cli.executeCommand("cd");
    assertEquals("Directory name required for 'cd' command", result);

    // Intentar ejecutar rm sin parámetros
    result = cli.executeCommand("rm");
    assertEquals("File/Directory name required for 'rm' command", result);
  }
}
