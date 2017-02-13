package persistence

case class User(id : Int, name : String, lastname : String) {
  override def toString: String = "id: " + id + " -name: " + name + " -lastname: " + lastname
}
