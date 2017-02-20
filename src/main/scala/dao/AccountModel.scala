package dao

import com.datastax.driver.core.Row
import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel}
import com.outworkers.phantom.dsl._
import persistence.Account

import scala.concurrent.Future

/**
  * Created by seven4n on 20/02/17.
  */
class AccountModel extends CassandraTable[ConcreteAccountModel, Account] {

  override def tableName(): String = "accounts"

  object id extends LongColumn(this) with PartitionKey

  object balance extends DoubleColumn(this)

  object user extends LongColumn(this)

  override def fromRow(r: Row): Account = Account(id(r), balance(r), user(r))
}


abstract class ConcreteAccountModel extends AccountModel with RootConnector {

  def getAccountById (id : Long): Future[Option[Account]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def getAllAccounts(): Future[List[Account]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .fetch()
  }

}