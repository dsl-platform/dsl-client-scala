package com.dslplatform.test

import com.dslplatform.api.patterns.ServiceLocator
import com.dslplatform.test.simple.SimpleRoot
import org.specs2.mutable._
import org.specs2.specification.Step

class AggregateEventTest extends Specification {

  override def is = s2"""
    Aggregate Event simple
      instantiate event on persisted root   ${located(instantiateEventOnPersistedRoot)}
      just instantiate                      $justInstantiate
      submit event                          ${located(submitEvent)}
                                            ${Step(located.close())}
"""

  val located = new Located

  implicit val duration = scala.concurrent.duration.FiniteDuration(1, "seconds")

  def instantiateEventOnPersistedRoot = { implicit locator : ServiceLocator =>
    val uri = SimpleRoot().create()
    pending
  }

  def justInstantiate = {
    pending
  }

  def submitEvent = { implicit locator : ServiceLocator =>
    val uri = SimpleRoot().create()
    pending
  }
}
