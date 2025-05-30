package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CommandFactoryLoader {

  private CommandFactoryLoader() {}

  public static List<CommandFactory> load() {
    List<CommandFactory> factories = new ArrayList<>();

    factories.add(new LsFactory());
    factories.add(new MkdirFactory());
    factories.add(new CdFactory());
    factories.add(new TouchFactory());
    factories.add(new PwdFactory());
    factories.add(new RmFactory());

    return Collections.unmodifiableList(factories);
  }
}
//  public static List<CommandFactory> load() {
//    List<CommandFactory> factories = new ArrayList<>();
//
//    // busca y obteine las implementaciones de CommandFactory. De esta manera, si agrego una nueva
// funci[on que implementa la interfaz, no tengo que agregarla maneualmente a la lista
//    ServiceLoader<CommandFactory> loader = ServiceLoader.load(CommandFactory.class);
//    for (CommandFactory factory : loader) {
//      factories.add(factory);
//    }
//
//    return factories;

// PARA USARLO Y QUE CARGUE LAS CLASES AUTOM[ATICAMENTE NECESITAR[IA AGREGAT UN ARCHIVO A META-INF,
// PERO NO PUEDO PQ ES UN READ ONLY
