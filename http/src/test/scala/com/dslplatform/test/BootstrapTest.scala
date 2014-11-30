package com.dslplatform.test

import java.util.concurrent.{ExecutorService, Executors}

import com.dslplatform.api.client.{ClientPersistableRepository, ClientSearchableRepository, HttpClient}
import com.dslplatform.api.patterns.{PersistableRepository, SearchableRepository}
import com.dslplatform.mock._
import org.slf4j.{Logger, LoggerFactory}
import org.specs2.mutable._

class BootstrapTest extends Specification {

  override def is = s2"""
    This specification checks the dependency management
      resolve default                           $defaults
      resolve provided logger                   $initial
      resolve provided initial Executor         $initialExecutor
      resolve a persistable repository          $resolvePersistableRepository
      resolve a searchable repository           $resolveSearchableRepository
"""

  def defaults = {
    val locator = com.dslplatform.api.client.Bootstrap.init("/test-project.props")
    try {
      locator.resolve[HttpClient].isInstanceOf[HttpClient] must beTrue
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }

  def initial = {
    val logger = LoggerFactory.getLogger("test-logger")
    val initialComponents: Map[Object, AnyRef] = Map(classOf[Logger] -> logger)
    val locator = com.dslplatform.api.client.Bootstrap.init("/test-project.props", initialComponents)
    try {
      locator.resolve[Logger] mustEqual (logger)
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }

  def resolvePersistableRepository = {
    val locator = com.dslplatform.api.client.Bootstrap.init("/test-project.props")
    try {
      locator.resolve[PersistableRepository[Agg]].isInstanceOf[ClientPersistableRepository[_]] must beTrue
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }

  def resolveSearchableRepository = {
    val locator = com.dslplatform.api.client.Bootstrap.init("/test-project.props")
    try {
      locator.resolve[SearchableRepository[AggGrid]].isInstanceOf[ClientSearchableRepository[_]] must beTrue
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }

  def initialExecutor = {
    val initialComponents: Map[Object, AnyRef] = Map(classOf[ExecutorService] -> Executors.newCachedThreadPool())
    val locator = com.dslplatform.api.client.Bootstrap.init("/test-project.props", initialComponents)
    try {
      locator.resolve[HttpClient].isInstanceOf[HttpClient] must beTrue
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }
}
