package dao

import com.datastax.driver.core.Row
import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel, IntColumn, ResultSet, StringColumn}
import persistence.{Transfer, User}
import com.outworkers.phantom.dsl._

import scala.concurrent.Future


/**
  * Created by seven4n on 20/02/17.
  */
class TransferModel extends CassandraTable[ConcreteTransferModel, Transfer] {

  override def tableName(): String = "transfers"

  object id extends LongColumn(this) with PartitionKey

  object originAccount extends LongColumn(this)

  object destinationAccount extends LongColumn(this)

  object transferType extends StringColumn(this)

  object balance extends DoubleColumn(this)

  override def fromRow(r: Row): Transfer = Transfer(id(r), originAccount(r), destinationAccount(r), transferType(r), balance(r))

}

abstract class ConcreteTransferModel extends TransferModel with RootConnector {

  def getTransferById (id : Long): Future[Option[Transfer]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def getAllTransfer () : Future[List[Transfer]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .fetch()
  }

}
