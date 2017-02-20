package controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives

import scala.concurrent.ExecutionContext

/**
  * Created by seven4n on 20/02/17.
  */
case class AccountsRoute (implicit val executionContext : ExecutionContext) extends Directives {



  def route =
  get {
    pathPrefix("accounts") {
      complete(StatusCodes.OK, "Hello World!")
    }
  } ~
    get {
      pathPrefix("accounts") {
        complete(StatusCodes.OK, "Hello World!")
      }
    }
}
