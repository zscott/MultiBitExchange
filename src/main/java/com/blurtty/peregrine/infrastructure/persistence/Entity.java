package com.blurtty.peregrine.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.ObjectId;

/**
 * <p>An entity aggregate root - provides the following to repositories:</p>
 * <ul>
 * <li>Methods common to all entities to ease persistence and global identification</li>
 * </ul>
 *
 * <p>An aggregate is a collection of objects that are bound together by a root entity, otherwise known as an AggregateRoot.</p>
 * <p>An AggregateRoot is a Domain Driven Design concept.</p>
 *
 * <p>An AggregateRoot is an Entity that aggregates other ValueObjects and Entities and guarantees the consistency of
 * changes being made within the aggregate.</p>
 *
 * <p>In the context of the repository pattern, an Entity is the only object your client code loads from the repository.</p>
 *
 * <p>AggregateRoot Design Rules:</p>
 * <ol>
 * <li>Model true invariants in consistency boundaries</li>
 * <li>Keep Aggregates as small as possible. Which attributes/values must be consistent?</li>
 * <li>Reference other Aggregates by Identity only.</li>
 * <li>When multiple Aggregates must be updated by a single client request use eventual consistency.</li>
 * </ol>
 *
 * <p>See the <a href="http://en.wikipedia.org/wiki/Domain-driven_design">Wikipedia article on DDD</a> for more details</p>
 *
 * <p>For a deeper understanding of Aggregates and AggregateRoots:</p>
 * <ol>
 * <li><a href="http://dddcommunity.org/wp-content/uploads/files/pdf_articles/Vernon_2011_1.pdf">Part I: Modeling a Single Aggregate </a></li>
 * <li><a href="http://dddcommunity.org/wp-content/uploads/files/pdf_articles/Vernon_2011_2.pdf">Part II: Making Aggregates Work Together </a></li>
 * <li><a href="http://dddcommunity.org/wp-content/uploads/files/pdf_articles/Vernon_2011_3.pdf">Part III: Gaining Insight Through Discovery </a></li>
 *
 * </ol>
 *
 * @param <K> is the key type for the common (e.g. String )
 *
 * @since 0.0.1
 */
public interface Entity<K> {

  /**
   * The annotations are set here on the interface to maintain consistency
   *
   * @return The unique ID for this entity
   */
  @ObjectId
  @JsonProperty("_id")
  K getId();

  /**
   * The annotations are set here on the interface to maintain consistency
   *
   * @param id The unique ID for this entity
   */
  @ObjectId
  @JsonProperty("_id")
  void setId(K id);

}
