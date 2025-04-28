package edu.austral.ingsis;

import static edu.austral.ingsis.clifford.ResolvePath.resolvePath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.ingsis.clifford.Cd;
import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.FileSystemComponent;
import edu.austral.ingsis.clifford.Ls;
import edu.austral.ingsis.clifford.Mkdir;
import edu.austral.ingsis.clifford.Pwd;
import edu.austral.ingsis.clifford.ResolvePath;
import edu.austral.ingsis.clifford.Rm;
import edu.austral.ingsis.clifford.Touch;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class RandomTest {
  @Test
  void testLsWithAscendingOrder() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    root.addChild(new File("b.txt", "File", root));
    root.addChild(new File("a.txt", "File", root));
    Ls ls = new Ls("asc");
    assertEquals("a.txt b.txt", ls.execute(root));
  }

  @Test
  void testLsWithDescendingOrder() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    root.addChild(new File("b.txt", "File", root));
    root.addChild(new File("a.txt", "File", root));
    Ls ls = new Ls("desc");
    assertEquals("b.txt a.txt", ls.execute(root));
  }

  @Test
  void testLsWithInvalidOrder() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    root.addChild(new File("b.txt", "File", root));
    root.addChild(new File("a.txt", "File", root));
    Ls ls = new Ls("random");
    assertEquals("b.txt a.txt", ls.execute(root)); // mantiene orden de inserción
  }

  @Test
  void testRmNonExistentFile() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Rm rm = new Rm("ghost.txt", false);
    assertEquals("Element not found: ghost.txt", rm.execute(root));
  }

  @Test
  void testRmDirectoryWithoutRecursiveFlag() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory childDir = new Directory("child", "Directory", root, new ArrayList<>());
    root.addChild(childDir);
    Rm rm = new Rm("child", false);
    assertEquals("cannot remove 'child', is a directory", rm.execute(root));
  }

  @Test
  void testRmDirectoryWithRecursiveFlag() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory childDir = new Directory("child", "Directory", root, new ArrayList<>());
    root.addChild(childDir);
    Rm rm = new Rm("child", true);
    assertEquals("'child' removed", rm.execute(root));
  }

  @Test
  void testTouchWithSpaceInName() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Touch touch = new Touch("bad name");
    assertEquals("File name can contain neither ' ' nor /", touch.execute(root));
  }

  @Test
  void testTouchWithSlashInName() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Touch touch = new Touch("bad/name");
    assertEquals("File name can contain neither ' ' nor /", touch.execute(root));
  }

  @Test
  void testResolveRootPath() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory child = new Directory("child", "Directory", root, new ArrayList<>());
    root.addChild(child);
    FileSystemComponent result = resolvePath("/", child);
    assertEquals(root, result);
  }

  @Test
  void testResolveDotDotFromRoot() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    FileSystemComponent result = resolvePath("..", root);
    assertEquals(root, result); // root has no parent, stays at root
  }

  @Test
  void testResolveInvalidPath() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    FileSystemComponent result = resolvePath("nonexistent", root);
    assertNull(result);
  }

  @Test
  void testPwdAtRoot() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Pwd pwd = new Pwd();
    assertEquals("", pwd.execute(root)); // no hay padres, devuelve vacío
  }

  @Test // REVISAR!!
  void testPwdInSubdirectory() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory child = new Directory("child", "Directory", root, new ArrayList<>());
    root.addChild(child);
    Directory grandchild = new Directory("grand", "Directory", child, new ArrayList<>());
    child.addChild(grandchild);

    Pwd pwd = new Pwd();
    assertEquals("/child", pwd.execute(child));
    assertEquals("/child/grand", pwd.execute(grandchild));
  }

  @Test
  void testMkdirNotDirectory() {
    File fakeDir = new File("file.txt", "File", null);
    Mkdir mkdir = new Mkdir("newDir");

    Exception exception = assertThrows(RuntimeException.class, () -> mkdir.execute(fakeDir));
    assertEquals("Not a directory", exception.getMessage());
  }

  @Test
  void testMkdirWithSpaceInName() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Mkdir mkdir = new Mkdir("new dir");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> mkdir.execute(root));
    assertEquals("Directory name can contain neither ' ' nor /", exception.getMessage());
  }

  @Test
  void testMkdirWithSlashInName() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Mkdir mkdir = new Mkdir("bad/name");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> mkdir.execute(root));
    assertEquals("Directory name can contain neither ' ' nor /", exception.getMessage());
  }

  @Test
  void testValidMkdir() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Mkdir mkdir = new Mkdir("docs");
    String result = mkdir.execute(root);
    assertEquals("'docs' directory created", result);
    assertEquals(1, root.getChildren().size());
    assertEquals("docs", root.getChildren().get(0).getName());
  }

  @Test
  void testMkdirFailsWhenNotDirectory() {
    File file = new File("file.txt", "File", null);
    Mkdir mkdir = new Mkdir("newdir");

    RuntimeException ex = assertThrows(RuntimeException.class, () -> mkdir.execute(file));
    assertEquals("Not a directory", ex.getMessage());
  }

  @Test
  void testMkdirFailsWithSpaceInName() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Mkdir mkdir = new Mkdir("bad name");

    IllegalArgumentException ex =
      assertThrows(IllegalArgumentException.class, () -> mkdir.execute(root));
    assertEquals("Directory name can contain neither ' ' nor /", ex.getMessage());
  }

  @Test
  void testMkdirFailsWithSlashInName() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Mkdir mkdir = new Mkdir("bad/name");

    IllegalArgumentException ex =
      assertThrows(IllegalArgumentException.class, () -> mkdir.execute(root));
    assertEquals("Directory name can contain neither ' ' nor /", ex.getMessage());
  }

  @Test
  void testMkdirSuccess() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Mkdir mkdir = new Mkdir("docs");

    String result = mkdir.execute(root);

    assertEquals("'docs' directory created", result);
    assertEquals(1, root.getChildren().size());
    assertEquals("docs", root.getChildren().get(0).getName());
  }

  @Test // REVISAR
  void testPwdAtRootS() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Pwd pwd = new Pwd();
    assertEquals("", pwd.execute(root)); // caso raíz
  }

  @Test // REVISAR
  void testPwdOneLevelDeep() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory child = new Directory("child", "Directory", root, new ArrayList<>());
    root.addChild(child);

    Pwd pwd = new Pwd();
    assertEquals("/child", pwd.execute(child)); // un nivel
  }

  @Test // REVISAR
  void testPwdTwoLevelsDeep() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory child = new Directory("child", "Directory", root, new ArrayList<>());
    root.addChild(child);
    Directory grand = new Directory("grand", "Directory", child, new ArrayList<>());
    child.addChild(grand);

    Pwd pwd = new Pwd();
    assertEquals("/child/grand", pwd.execute(grand)); // dos niveles
  }

  @Test
  void testCdToValidSubdirectory() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory sub = new Directory("sub", "Directory", root, new ArrayList<>());
    root.addChild(sub);

    Cd cd = new Cd("sub");
    assertEquals("moved to directory 'sub'", cd.execute(root));
  }

  @Test
  void testCdToNonExistentDirectory() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Cd cd = new Cd("missing");

    assertEquals("'missing' directory does not exist", cd.execute(root));
  }

  @Test
  void testCdOnNonDirectoryNode() {
    File file = new File("file.txt", "File", null);
    Cd cd = new Cd("any");

    assertEquals("Not a directory", cd.execute(file));
  }

  @Test
  void testResolvePathDotReturnsSameDir() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    assertEquals(root, ResolvePath.resolvePath(".", root));
  }

  @Test
  void testResolvePathDoubleDotReturnsParent() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory child = new Directory("child", "Directory", root, new ArrayList<>());
    root.addChild(child);

    assertEquals(root, ResolvePath.resolvePath("..", child));
  }

  @Test
  void testResolvePathAbsolute() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    Directory docs = new Directory("docs", "Directory", root, new ArrayList<>());
    root.addChild(docs);

    assertEquals(docs, ResolvePath.resolvePath("/docs", root));
  }

  @Test
  void testResolvePathInvalid() {
    Directory root = new Directory("root", "Directory", null, new ArrayList<>());
    assertNull(ResolvePath.resolvePath("missing", root));
  }
}