package controller


import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, onSuccess, path, pathPrefix, post}
import com.outworkers.phantom.dsl.ResultSet
import persistence.User
import service.UserService
import spray.json.DefaultJsonProtocol

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by seven4n on 20/02/17.
  */
case class UsersRoute (implicit val executionContext : ExecutionContext) extends Directives with SprayJsonSupport with DefaultJsonProtocol {

  implicit val userFormat = jsonFormat4(User)

  def route = {

    get {
      pathPrefix("users" / LongNumber) { id =>
        onSuccess(findUser(id)) {
          case Some(user) => complete(StatusCodes.OK, user)
          case None => complete(StatusCodes.NotFound)
        }
      }
    } ~
      get {
        path("users") {
          onSuccess(findAllUsers()) {
            case Nil => complete(StatusCodes.NotFound)
            case x :: xs => complete(StatusCodes.OK, x :: xs)
          }
        }
      } ~
      (post & entity(as[User])) { person =>
        path("users") {
          val f = storeUser(person)
          onSuccess(f) { extraction =>
            complete(StatusCodes.Created)
          }
        }
      }

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
