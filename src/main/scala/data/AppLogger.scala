package data

import org.slf4j.LoggerFactory

trait AppLogger extends Serializable  {
  val logger = LoggerFactory.getLogger(getClass.getName)
}