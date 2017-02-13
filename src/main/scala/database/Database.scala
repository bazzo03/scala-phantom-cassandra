package database

import connector.Connector._
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef
import dao.ConcreteUserModel

/**
  * Created by seven4n on 13/02/17.
  */
class MyDatabase(override val connector : KeySpaceDef)  extends Database [MyDatabase] (connector) {

  object usersModel extends ConcreteUserModel with connector.Connector

}

object ProductionDB extends MyDatabase(connector)


trait ProductionDatabaseProvider {
  def database: MyDatabase
}

trait ProductionDatabase extends ProductionDatabaseProvider {
  override val database = ProductionDB
}

