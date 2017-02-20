package dao

import com.datastax.driver.core.Row
import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel, PartitionKey, StringColumn, UUID, UUIDColumn}
import persistence.{Address}
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

/**
  * Created by seven4n on 20/02/17.
  */
class AddressModel extends CassandraTable[ConcreteAddressModel, Address] {

  override def tableName(): String = "addresses"

  object id extends LongColumn(this) with PartitionKey

  object street extends StringColumn(this)

  object zipCode extends StringColumn(this)

  override def fromRow(r: Row): Address = Address(id(r), street(r), zipCode(r))
}

abstract class ConcreteAddressModel extends AddressModel with RootConnector {

  def getAddressById (id : Long): Future[Option[Address]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def getAllAddresses(): Future[List[Address]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .fetch()
  }

}
