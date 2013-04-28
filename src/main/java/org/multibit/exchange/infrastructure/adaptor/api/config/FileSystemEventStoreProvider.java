package org.multibit.exchange.infrastructure.adaptor.api.config;

import com.google.inject.Provider;
import java.io.File;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;

/**
 * <p>Provider to provide the following to guice:</p>
 * <ul>
 * <li>Instances of an EventStore</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class FileSystemEventStoreProvider implements Provider<EventStore> {

  private final String pathToEventStoreDir;

  public FileSystemEventStoreProvider(String pathToEventStoreDir) {
    this.pathToEventStoreDir = pathToEventStoreDir;
  }

  @Override
  public EventStore get() {
    return new FileSystemEventStore(new SimpleEventFileResolver(new File(pathToEventStoreDir)));
  }
}
