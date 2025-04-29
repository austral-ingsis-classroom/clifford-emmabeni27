package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.*;

import edu.austral.ingsis.clifford.Result;
import edu.austral.ingsis.clifford.Success;
import org.junit.jupiter.api.Test;

public class ResultTest {

  @Test
  public void testSuccess() {
    // Crear un objeto Success
    String message = "Operation succeeded";
    Success success = new Success(message);

    // Verificar que implementa la interfaz Result
    assertTrue(success instanceof Result);

    // Verificar que getMessage() devuelve el mensaje correcto
    assertEquals(message, success.getMessage());
  }

  @Test
  public void testError() {
    // Crear un objeto Error
    String message = "Operation failed";
    Error error = new Error(message);

    // Verificar que implementa la interfaz Result
    assertTrue(error instanceof Error);

    // Verificar que getMessage() devuelve el mensaje correcto
    assertEquals(message, error.getMessage());
  }

  @Test
  public void testPolymorphism() {
    // Verificar que podemos usar polimorfismo con Result
    Result success = new Success("Success message");
    Error error = new Error("Error message");

    assertEquals("Success message", success.getMessage());
    assertEquals("Error message", error.getMessage());
  }
}
