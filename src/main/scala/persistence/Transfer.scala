package persistence

import com.outworkers.phantom.dsl.UUID

/**
  * Created by seven4n on 20/02/17.
  */
case class Transfer (id : Long, originAccount : Long, destinationAccount : Long, transferType : String, amount : Double) {

}
