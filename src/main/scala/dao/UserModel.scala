package dao

import com.datastax.driver.core.Row
import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel, IntColumn, ResultSet, StringColumn}
import persistence.User
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

/**
  * Created by seven4n on 13/02/17.
  */
class UserModel extends CassandraTable[ConcreteUserModel, User] {

  override def tableName(): String = "users"

  object id extends IntColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object lastname extends StringColumn(this)

  override def fromRow(r: Row): User = User(id(r), name(r), lastname(r))
}

abstract class ConcreteUserModel extends UserModel with RootConnector {

  def getUserById (id : Int): Future[Option[User]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def storeUser (user: User): Future[ResultSet] = {
    insert
      .value(_.id, user.id)
      .value(_.name, user.name)
      .value(_.lastname, user.lastname)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def getAllUsers () : Future[List[User]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .fetch()
  }

}