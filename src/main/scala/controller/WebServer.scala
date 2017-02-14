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
      path("hello") {
        get {
          onSuccess(findUser()) {
            case Some(user) => complete(StatusCodes.OK, user)
            case None => complete(StatusCodes.NotFound)
          }
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  def findUser() : Future[Option[User]] = {
        UserService.getUserById(2)
  }

}
