package org.huwtl.penfold.domain.model

sealed trait Status {
  val name: String
}

object Status {
  case object Waiting extends Status {
    val name = "waiting"
  }

  case object Ready extends Status {
    val name = "ready"
  }

  case object Started extends Status {
    val name = "started"
  }

  case object Cancelled extends Status {
    val name = "cancelled"
  }

  case object Completed extends Status {
    val name = "completed"
  }

  def from(str: String): Option[Status] = {
    str.toLowerCase match {
      case Waiting.name => Some(Waiting)
      case Ready.name => Some(Ready)
      case Started.name => Some(Started)
      case Completed.name => Some(Completed)
      case _ => None
    }
  }
}
