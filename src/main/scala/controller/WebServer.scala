package controller


import java.util.UUID

import com.outworkers.phantom.dsl.ResultSet
import persistence.User
import service.UserService

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent._
import ExecutionContext.Implicits.global


object WebServer {

  def main(args: Array[String]) {
    print ("\n\n\n Hello, let's try to bring the saved user with id: 1")
    val future :Future[Option[User]] = UserService.getUserById(1)
    future onComplete {
      case Success (smth) => print ("\n\n User Found: " +smth.getOrElse())
      case Failure (err) => print (err)
    }

    val future2 : Future[ResultSet] = UserService.storeUser(User(UUID.randomUUID().hashCode(), "Pepito", "Perez"))
    future2 onComplete {
      case Success (smth) => print ("\n\n User added correctly")
      case Failure (err) => print (err)
    }
  }
}