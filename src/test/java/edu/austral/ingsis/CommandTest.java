// package edu.austral.ingsis.clifford;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import static org.junit.jupiter.api.Assertions.*;
//
// public class CommandTest {
//  private FileSystemComponent rootDirectory;
//  private Directory testDirectory;
//
//  @BeforeEach
//  public void setUp() {
//    // Configuración inicial para las pruebas
//    List<FileSystemComponent> rootChildren = new ArrayList<>();
//    rootDirectory = new Directory("root", "Directory", null, rootChildren);
//
//    // Crear un directorio de prueba
//    List<FileSystemComponent> testDirChildren = new ArrayList<>();
//    testDirectory = new Directory("test", "Directory", rootDirectory, testDirChildren);
//    ((Directory) rootDirectory).addChild(testDirectory);
//  }
//
//  @Test
//  public void testMkdir() {
//    // Probar el comando mkdir
//    Mkdir mkdir = new Mkdir("newDir");
//    Result result = mkdir.execute(testDirectory);
//
//    assertTrue(result instanceof Success);
//    assertEquals("'newDir' directory created", result.getMessage());
//
//    // Verificar que el directorio se creó
//    boolean found = testDirectory.getChildren().stream()
//      .anyMatch(child -> "newDir".equals(child.getName()) && child.isDirectory());
//    assertTrue(found);
//
//    // Probar con un nombre de directorio inválido
//    Mkdir mkdirInvalid = new Mkdir("invalid/dir");
//    Result resultInvalid = mkdirInvalid.execute(testDirectory);
//
//    assertTrue(resultInvalid instanceof Error);
//    assertEquals("Directory name can contain neither ' ' nor /", resultInvalid.getMessage());
//  }
//
//  @Test
//  public void testTouch() {
//    // Probar el comando touch
//    Touch touch = new Touch("newFile");
//    Result result = touch.execute(testDirectory);
//
//    assertTrue(result instanceof Success);
//    assertEquals("'newFile' file created", result.getMessage());
//
//    // Verificar que el archivo se creó
//    boolean found = testDirectory.getChildren().stream()
//      .anyMatch(child -> "newFile".equals(child.getName()) && !child.isDirectory());
//    assertTrue(found);
//
//    // Probar con un nombre de archivo inválido
//    Touch touchInvalid = new Touch("invalid/file");
//    Result resultInvalid = touchInvalid.execute(testDirectory);
//
//    assertTrue(resultInvalid instanceof Error);
//    assertEquals("File name can contain neither ' ' nor /", resultInvalid.getMessage());
//  }
//
//  @Test
//  public void testLs() {
//    // Agregar algunos archivos y directorios para probar
//    testDirectory.addChild(new File("file1", "File", testDirectory));
//    testDirectory.addChild(new File("file2", "File", testDirectory));
//    List<FileSystemComponent> subDirChildren = new ArrayList<>();
//    testDirectory.addChild(new Directory("subDir", "Directory", testDirectory, subDirChildren));
//
//    // Probar el comando ls sin orden
//    Ls ls = new Ls("none");
//    Result result = ls.execute(testDirectory);
//
//    assertTrue(result instanceof Success);
//    String content = result.getMessage();
//    assertTrue(content.contains("file1"));
//    assertTrue(content.contains("file2"));
//    assertTrue(content.contains("subDir"));
//
//    // Probar el comando ls con orden ascendente
//    Ls lsAsc = new Ls("asc");
//    Result resultAsc = lsAsc.execute(testDirectory);
//
//    assertTrue(resultAsc instanceof Success);
//    assertEquals("file1 file2 subDir", resultAsc.getMessage());
//
//    // Probar el comando ls con orden descendente
//    Ls lsDesc = new Ls("desc");
//    Result resultDesc = lsDesc.execute(testDirectory);
//
//    assertTrue(resultDesc instanceof Success);
//    assertEquals("subDir file2 file1", resultDesc.getMessage());
//  }
//
//  @Test
//  public void testCd() {
//    // Crear un subdirectorio para probar cd
//    List<FileSystemComponent> subDirChildren = new ArrayList<>();
//    Directory subDir = new Directory("subDir", "Directory", testDirectory, subDirChildren);
//    testDirectory.addChild(subDir);
//
//    // También crear un archivo para probar el caso de error
//    testDirectory.addChild(new File("testFile", "File", testDirectory));
//
//    // Probar el comando cd a un directorio válido
//    Cd cd = new Cd("subDir");
//    Result result = cd.execute(testDirectory);
//
//    assertTrue(result instanceof Success);
//    assertEquals("moved to directory 'subDir'", result.getMessage());
//
//    // Probar cd a un archivo (debería fallar)
//    Cd cdToFile = new Cd("testFile");
//    Result resultToFile = cdToFile.execute(testDirectory);
//
//    assertTrue(resultToFile instanceof Error);
//    assertEquals("'testFile' is not a directory", resultToFile.getMessage());
//
//    // Probar cd a un directorio que no existe
//    Cd cdNonExistent = new Cd("nonExistentDir");
//    Result resultNonExistent = cdNonExistent.execute(testDirectory);
//
//    assertTrue(resultNonExistent instanceof Error);
//    assertEquals("'nonExistentDir' directory does not exist", resultNonExistent.getMessage());
//  }
//
//  @Test
//  public void testPwd() {
//    // Crear una estructura jerárquica para probar pwd
//    List<FileSystemComponent> level1Children = new ArrayList<>();
//    Directory level1 = new Directory("level1", "Directory", rootDirectory, level1Children);
//    ((Directory) rootDirectory).addChild(level1);
//
//    List<FileSystemComponent> level2Children = new ArrayList<>();
//    Directory level2 = new Directory("level2", "Directory", level1, level2Children);
//    level1.addChild(level2);
//
//    // Probar pwd desde nivel 2
//    Pwd pwd = new Pwd();
//    Result result = pwd.execute(level2);
//
//    assertTrue(result instanceof Success);
//    assertEquals("/level1/level2", result.getMessage());
//  }
//
//  @Test
//  public void testRm() {
//    // Crear un archivo y un directorio para probar rm
//    testDirectory.addChild(new File("fileToRemove", "File", testDirectory));
//
//    List<FileSystemComponent> subDirChildren = new ArrayList<>();
//    Directory subDir = new Directory("dirToRemove", "Directory", testDirectory, subDirChildren);
//    testDirectory.addChild(subDir);
//
//    // Probar rm en un archivo
//    Rm rmFile = new Rm("fileToRemove", false);
//    Result resultFile = rmFile.execute(testDirectory);
//
//    assertTrue(resultFile instanceof Success);
//    assertEquals("'fileToRemove' removed", resultFile.getMessage());
//
//    // Verificar que el archivo fue eliminado
//    boolean fileFound = testDirectory.getChildren().stream()
//      .anyMatch(child -> "fileToRemove".equals(child.getName()));
//    assertFalse(fileFound);
//
//    // Probar rm en un directorio sin recursivo (debería fallar)
//    Rm rmDirNoRecursive = new Rm("dirToRemove", false);
//    Result resultDirNoRecursive = rmDirNoRecursive.execute(testDirectory);
//
//    assertTrue(resultDirNoRecursive instanceof Error);
//    assertEquals("cannot remove 'dirToRemove', is a directory",
// resultDirNoRecursive.getMessage());
//
//    // Probar rm en un directorio con recursivo
//    Rm rmDirRecursive = new Rm("dirToRemove", true);
//    Result resultDirRecursive = rmDirRecursive.execute(testDirectory);
//
//    assertTrue(resultDirRecursive instanceof Success);
//    assertEquals("'dirToRemove' removed", resultDirRecursive.getMessage());
//
//    // Verificar que el directorio fue eliminado
//    boolean dirFound = testDirectory.getChildren().stream()
//      .anyMatch(child -> "dirToRemove".equals(child.getName()));
//    assertFalse(dirFound);
//  }
//
//  @Test
//  public void testErrorOperation() {
//    // Probar la clase ErrorOperation
//    ErrorOperation errorOp = new ErrorOperation("Test error message");
//    Result result = errorOp.execute(rootDirectory);
//
//    assertTrue(result instanceof Error);
//    assertEquals("Test error message", result.getMessage());
//  }
// }
//
