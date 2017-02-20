package persistence

import com.outworkers.phantom.dsl.UUID

case class User(id : Long, name : String, lastname : String, address : Long) {
  override def toString: String = "id: " + id + " -name: " + name + " -lastname: " + lastname
}
