package controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import persistence.User
import service.UserService
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

object WebServer extends SprayJsonSupport with DefaultJsonProtocol {

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val userFormat = jsonFormat3(User)
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
    get {
      pathPrefix("users" / IntNumber) { id =>
        onSuccess(findUser(id)) {
          case Some(user) => complete(StatusCodes.OK, user)
          case None       => complete(StatusCodes.NotFound)
        }
      }
    }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  }

  def findUser(id : Int) : Future[Option[User]] = {
    UserService.getUserById(id)
  }

}
