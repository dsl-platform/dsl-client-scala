package com.dslplatform.api.client

import scala.reflect.ClassTag
import scala.concurrent.Future

class HttpApplicationProxy(httpClient: HttpClient)
    extends ApplicationProxy {

  val APPLICATION_URI = "RestApplication.svc"

  import HttpClientUtil._

  def get[TResult: ClassTag](
    command: String, expectedStatus: Set[Int]): Future[TResult] =
    httpClient.sendRequest[TResult](
      GET, APPLICATION_URI / command, expectedStatus)

  def post[TArgument, TResult: ClassTag](
    command: String,
    argument: TArgument,
    expectedStatus: Set[Int]): Future[TResult] =
    httpClient.sendRequest[TResult](
      POST[TArgument](argument), APPLICATION_URI / command,
      expectedStatus)
}
