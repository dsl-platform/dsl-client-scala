package com.dslplatform.api.patterns

import com.dslplatform.api.client.DomainProxy

import scala.concurrent.Future

/** Service for searching and counting domain objects.
  * Search can be performed using {@link Specification specification},
  * paged using limit and offset arguments.
  * Custom sort can be provided using Seq of property->direction pairs.
  *
  * Specification can be declared in DSL or custom search can be built on client
  * and sent to server.
  *
  * When permissions are applied, server can restrict which results will be returned to the client.
  * Service should be used when Future is a preferred way of interacting with the remote server.
  *
  * @tparam TSearchable domain object type.
  */
trait SearchableRepository[TSearchable <: Searchable] {

  /** Returns an IndexedSeq with all domain objects.
    * @return              future with all domain objects
    */
  def search: Future[IndexedSeq[TSearchable]] = search()

  /** Returns an IndexedSeq of domain objects satisfying {@link Specification[TSearchable] specification}
    * with up to <code>limit</code> results.
    * <code>offset</code> can be used to skip initial results.
    * <code>order</code> should be given as a Seq of pairs of
    * <code>{@literal <String, Boolean>}</code>
    * where first is a property name and second is whether it should be sorted
    * ascending over this property.
    *
    * @param specification search predicate
    * @param limit         maximum number of results
    * @param offset        number of results to be skipped
    * @param order         custom ordering
    * @return              future to domain objects which satisfy search predicate
    */
  def search(
      specification: Option[Specification[TSearchable]] = None,
      limit: Option[Int] = None,
      offset: Option[Int] = None,
      order: Map[String, Boolean] = Map.empty): Future[IndexedSeq[TSearchable]]

  /** Helper method for searching domain objects.
    * Returns a Seq of domain objects satisfying {@link Specification[TSearchable] specification}
    *
    * @param specification search predicate
    * @return              future to domain objects which satisfy search predicate
    */
  def search(
      specification: Specification[TSearchable]): Future[IndexedSeq[TSearchable]] =
    search(Option(specification))

  /** Helper method for searching domain objects.
    * Returns a Seq of domain objects satisfying {@link Specification[TSearchable] specification}
    * with up to <code>limit</code> results.
    *
    * @param specification search predicate
    * @param limit         maximum number of results
    * @return              future to domain objects which satisfy search predicate
    */
  def search(
      specification: Specification[TSearchable],
      limit: Int): Future[IndexedSeq[TSearchable]] =
    search(Option(specification), Option(limit))

  /** Returns the number of elements
    *
    * @return              future with number of domain objects
    */
  def count: Future[Long] = count()

  /** Returns the number of elements satisfying provided specification.
    *
    * @param specification search predicate
    * @return              future with number of domain objects which satisfy specification
    */
  def count(specification: Option[Specification[TSearchable]] = None): Future[Long]

  /** Helper method for counting domain objects.
    * Returns the number of elements satisfying provided specification.
    *
    * @param specification search predicate
    * @return              future with number of domain objects which satisfy specification
    */
  def count(
      specification: Specification[TSearchable]): Future[Long] =
    count(Option(specification))
}
