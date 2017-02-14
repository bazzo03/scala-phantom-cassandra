package service

import com.outworkers.phantom.dsl.ResultSet
import database.ProductionDatabase
import persistence.User

import scala.concurrent.Future

trait UserService extends ProductionDatabase {

  def getUserById(id : Int): Future[Option[User]] = {
    database.usersModel.getUserById(id)
  }

  def storeUser(user : User) : Future[ResultSet] = {
    database.usersModel.storeUser(user)
  }

  def getAllUsers() : Future[List[User]] = {
    database.usersModel.getAllUsers
  }
}

object UserService extends UserService with ProductionDatabase {

}
