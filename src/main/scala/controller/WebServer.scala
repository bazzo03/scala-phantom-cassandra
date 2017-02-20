package controller

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.outworkers.phantom.dsl.ResultSet
import persistence.User
import service.UserService
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future


object WebServer extends SprayJsonSupport with DefaultJsonProtocol {

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val userFormat = jsonFormat4(User)
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
        UsersRoute().route ~
        AccountsRoute().route


    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)


  }

  def findUser(id: Long): Future[Option[User]] = {
    UserService.getUserById(id)
  }

  def findAllUsers(): Future[List[User]] = {
    UserService.getAllUsers()
  }

  def storeUser(user: User): Future[ResultSet] = {
    UserService.storeUser(user)
  }


}
