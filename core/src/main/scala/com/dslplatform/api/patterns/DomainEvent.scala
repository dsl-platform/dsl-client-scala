package com.dslplatform.api.patterns

import org.joda.time.DateTime

/**
 * Domain event represents an meaningful business event that occurred in the system.
 * It is a message that back-end system knows how to process and that will
 * change the state of the system.
 * <p>
 * They are preferred way of manipulating data instead of simple CUD
 * operations (create, update, delete).
 * Unlike {@link AggregateDomainEvent aggregate domain event} which is tied to a change in a single
 * {@link AggregateRoot aggregate root}, domain event should be used when an action will result
 * in modifications to multiple aggregates, external call (like sending an email)
 * or some other action.
 * <p>
 * By default event will be applied immediately.
 * If {@code async} is used, event will be stored immediately but applied later.
 *
 * DomainEvent is defined in DSL with keyword {@code event}.
 *
 * <blockquote><pre>
 * module Todo {
 *   aggregate Task;
 *   event MarkDone {
 *     Task task;
 *   }
 * }
 * </pre></blockquote>
 *
 */
trait DomainEvent
    extends Identifiable {

  def createdAt: DateTime
  def processedAt: Option[DateTime]
}
