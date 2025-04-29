package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.*;

import edu.austral.ingsis.clifford.Cli;
import edu.austral.ingsis.clifford.CommandFactory;
import edu.austral.ingsis.clifford.CommandFactoryLoader;
import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystemComponent;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegrationTest {
  private Cli cli;

  @BeforeEach
  public void setUp() {
    // Crear un sistema de archivos para las pruebas
    List<FileSystemComponent> rootChildren = new ArrayList<>();
    FileSystemComponent rootDirectory = new Directory("root", "Directory", null, rootChildren);
    List<CommandFactory> factories = CommandFactoryLoader.load();
    cli = new Cli(rootDirectory, factories);
  }

  @Test
  public void testComplexScenario() {
    // Escenario: Crear una estructura de directorios y archivos,
    // navegar entre ellos y realizar operaciones

    // 1. Crear varios directorios
    assertEquals("'projects' directory created", cli.executeCommand("mkdir projects"));
    assertEquals("'docs' directory created", cli.executeCommand("mkdir docs"));
    assertEquals("'downloads' directory created", cli.executeCommand("mkdir downloads"));

    // 2. Verificar que ls muestra los directorios creados
    String lsResult = cli.executeCommand("ls");
    assertTrue(lsResult.contains("projects"));
    assertTrue(lsResult.contains("docs"));
    assertTrue(lsResult.contains("downloads"));

    // 3. Entrar a un directorio y crear archivos y subdirectorios
    assertEquals("moved to directory 'projects'", cli.executeCommand("cd projects"));
    assertEquals("'project1' directory created", cli.executeCommand("mkdir project1"));
    assertEquals("'project2' directory created", cli.executeCommand("mkdir project2"));
    assertEquals("'notes.txt' file created", cli.executeCommand("touch notes.txt"));

    // 4. Verificar la ubicación actual
    String pwdResult = cli.executeCommand("pwd");
    assertTrue(pwdResult.contains("/projects"));

    // 5. Entrar a un subdirectorio y crear más archivos
    assertEquals("moved to directory 'project1'", cli.executeCommand("cd project1"));
    assertEquals("'main.java' file created", cli.executeCommand("touch main.java"));
    assertEquals("'config.xml' file created", cli.executeCommand("touch config.xml"));

    // 6. Verificar la ubicación y contenido
    String pwdProject1 = cli.executeCommand("pwd");
    assertTrue(pwdProject1.contains("/projects/project1"));

    String lsProject1 = cli.executeCommand("ls");
    assertTrue(lsProject1.contains("main.java"));
    assertTrue(lsProject1.contains("config.xml"));

    // 7. Volver al directorio padre
    assertEquals("moved to directory 'projects'", cli.executeCommand("cd .."));
    String pwdBack = cli.executeCommand("pwd");
    assertTrue(pwdBack.contains("/projects"));

    // 8. Ir a la raíz
    assertEquals("moved to directory 'root'", cli.executeCommand("cd /"));

    // 9. Eliminar archivos y directorios
    assertEquals("moved to directory 'projects'", cli.executeCommand("cd projects"));
    assertEquals("'notes.txt' removed", cli.executeCommand("rm notes.txt"));

    // Verificar que el archivo fue eliminado
    String lsAfterRm = cli.executeCommand("ls");
    assertFalse(lsAfterRm.contains("notes.txt"));

    // 10. Intentar eliminar un directorio sin --recursive (debe fallar)
    String rmResult = cli.executeCommand("rm project1");
    assertEquals("cannot remove 'project1', is a directory", rmResult);

    // 11. Eliminar un directorio con --recursive
    assertEquals("'project1' removed", cli.executeCommand("rm --recursive project1"));

    // Verificar que el directorio fue eliminado
    String lsAfterRmDir = cli.executeCommand("ls");
    assertFalse(lsAfterRmDir.contains("project1"));
    assertTrue(lsAfterRmDir.contains("project2"));
  }
}
