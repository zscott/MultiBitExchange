package org.multibit.exchange.infrastructure.adaptor.persistence.mongo;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.io.Processors;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Factory to provide the following to the application:</p>
 * <ul>
 * <li>A running sandbox instance of MongoDB</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class MongodSandboxFactory {


  private final MongodExecutable mongodExecutable;

  private final MongodProcess mongodProcess;

  public MongodSandboxFactory() throws IOException {

    // TODO Consider alternatives for configuring the logger level for de.flapdoodle
    Logger logger = Logger.getLogger("de.flapdoodle");

    // log only severe messsages for de.flapdoodle
    logger.setLevel(Level.WARNING);

    RuntimeConfig runtimeConfig = new RuntimeConfig();
    runtimeConfig.setProcessOutput(
            new ProcessOutput(
                    Processors.logTo(logger, Level.INFO),
                    Processors.logTo(logger, Level.SEVERE),
                    Processors.named("[console>]", Processors.logTo(logger, Level.FINE))
            )
    );

    MongodConfig mongodConfig = new MongodConfig(Version.Main.V2_2);
    MongodStarter runtime = MongodStarter.getInstance(runtimeConfig);

    mongodExecutable = runtime.prepare(mongodConfig);
    mongodProcess = mongodExecutable.start();
  }

  public void shutdown() {
    if (mongodExecutable != null)
      mongodExecutable.stop();
  }

  public Mongo newMongo() throws UnknownHostException, MongoException {
    return new Mongo(new ServerAddress(
            mongodProcess.getConfig().net().getServerAddress(),
            mongodProcess.getConfig().net().getPort()));

  }

  public DB newDB(Mongo mongo) {
    return mongo.getDB("testdb_" + UUID.randomUUID().toString());
  }
}
