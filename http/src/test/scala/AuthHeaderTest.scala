import java.io.ByteArrayInputStream

import com.dslplatform.api.client.{HttpHeaderProvider, SettingsHeaderProvider, HttpClient}
import org.specs2._

class AuthHeaderTest extends Specification {

  def is = s2"""
  Header Provider is resolved from the ServiceLocator
    provide with auth header                    $auth
    provide with project id                     $pid
    privide custom                              $custom
  """

  private val withAuthHeader =
    """
      |auth=someAuth
      |api-url=https://dsl-platform.com/test
      |package-name=model
    """.stripMargin

  private val withPidHeader =
    """
      |username=user
      |project-id=0-0-0-0-0
      |api-url=https://dsl-platform.com/test
      |package-name=model
    """.stripMargin

  def auth = {
    val properties = new java.util.Properties()
    properties.load(new ByteArrayInputStream(withAuthHeader.getBytes("UTF-8")))
    val locator = com.dslplatform.api.client.Bootstrap.init(properties)
    try {
      locator.resolve[SettingsHeaderProvider].getHeaders("Authorization").contains("someAuth") must beTrue
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }

  def pid = {
    val properties = new java.util.Properties()
    properties.load(new ByteArrayInputStream(withPidHeader.getBytes("UTF-8")))
    val locator = com.dslplatform.api.client.Bootstrap.init(properties)
    try {
      locator.resolve[SettingsHeaderProvider].getHeaders.get("Authorization") must beSome
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }

  def custom = {
    val locator = com.dslplatform.api.client.Bootstrap.init("/test-project.props",
      Map[Object, AnyRef](classOf[HttpHeaderProvider] -> new HttpHeaderProvider {
        override def getHeaders: Map[String, String] = Map("Do" -> "More")
      }))
    try {
      val headers: Map[String, String] = locator.resolve[HttpHeaderProvider].getHeaders
      println(headers)
      headers("Do") === "More"
    } finally {
      locator.resolve[HttpClient].shutdown()
    }
  }
}
