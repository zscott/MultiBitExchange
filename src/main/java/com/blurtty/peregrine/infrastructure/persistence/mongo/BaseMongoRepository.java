package com.blurtty.peregrine.infrastructure.persistence.mongo;


import com.google.common.collect.Lists;
import com.mongodb.DB;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

/**
 * <p>Abstract base class to provide the following to MongoDB-based repositories:</p>
 * <ul>
 * <li>Provision of common methods</li>
 * </ul>
 *
 * @param <T> is the entity type
 * @param <K> is the key type for the entity
 * @since 0.0.1
 */
public abstract class BaseMongoRepository<T extends Entity<K>, K> implements EntityRepository<T, K> {

  /**
   * Provides logging for this class
   */
  private Logger log = LoggerFactory.getLogger(BaseMongoRepository.class);

  protected final JacksonDBCollection<T, K> entitiesCollection;

  private final DB mongoDb;

  public BaseMongoRepository(DB mongoDb, JacksonDBCollection<T, K> entitiesCollection) {
    this.mongoDb = mongoDb;
    this.entitiesCollection = entitiesCollection;
  }

  public DB getMongoDb() {
    return mongoDb;
  }

  public K save(T entity) {
    WriteResult<T, K> writeResult = entitiesCollection.save(entity);
    if (writeResult.getDbObjects().length != 0) {
      // Had an insert so we can safely reference the ID
      entity.setId(writeResult.getSavedId());
      return writeResult.getSavedId();
    }
    // Return the original ID (no change)
    return entity.getId();
  }


  public K create(T entity) {
    return entitiesCollection.insert(entity).getSavedId();
  }

  public List<K> createAll(List<T> entities) {
    return entitiesCollection.insert(entities).getSavedIds();
  }

  public K upsert(T entity) {
    WriteResult<T, K> writeResult = entitiesCollection.update(entity, entity, true, false);
    if (writeResult.getDbObjects().length != 0) {
      // Had an insert so we can safely reference the ID
      return writeResult.getSavedId();
    }
    // Return the original ID (no change)
    return entity.getId();
  }

  public List<K> upsertAll(List<T> entities) {

    // TODO Need to use updateMulti() and construct a suitable query
    List<K> ids = Lists.newArrayList();

    for (T entity : entities) {
      ids.add(upsert(entity));
    }
    return ids;
  }

  public void hardDelete(T entity) {
    entitiesCollection.remove(entity);
  }

}

