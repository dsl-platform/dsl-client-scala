package com.dslplatform.api.patterns

/** Aggregate root is a meaningful object in the domain.
  * It can be viewed as a write boundary for entities and value objects
  * that will maintain write consistency.
  *
  * Usually it represents a single table, but can span several tables
  * and can be used like document or similar data structure.
  * Since every aggregate is also an entity, it has a unique
  * identification represented by its URI.
  *
  * DSL example:
  * {{{
  * module Todo {
  *   aggregate Task {
  *     DateTime startedAt;
  *     DateTime? finishedAt;
  *     Int? priority;
  *     Seq&lt;Note&gt; notes;
  *   }
  *   value Note {
  *     Date entered;
  *     String remark;
  *   }
  * }
  * }}}
  *
  */
trait AggregateRoot
    extends Identifiable
