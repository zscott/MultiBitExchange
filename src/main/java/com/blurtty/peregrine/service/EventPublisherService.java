package com.blurtty.peregrine.service;

/**
 * <p>EventPublisherService to provide the following to the application:</p>
 * <ul>
 * <li>An abstraction capable of publishing a specific type of event</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface EventPublisherService<T> {
  void publish(T event);
}
