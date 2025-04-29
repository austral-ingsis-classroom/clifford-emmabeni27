package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CommandFactoryLoader {

  private CommandFactoryLoader() {
    // Private constructor to prevent instantiation
  }

  public static List<CommandFactory> load() {
    List<CommandFactory> factories = new ArrayList<>();

    factories.add(new Ls(""));
    factories.add(new Mkdir(""));
    factories.add(new Cd(""));
    factories.add(new Touch(""));
    factories.add(new Pwd());
    factories.add(new Rm("", true));

    return Collections.unmodifiableList(factories);
  }
}