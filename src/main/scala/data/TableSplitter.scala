package data

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.col
class TableSplitter extends AppLogger {

  val dbName = "mixedtiles"
  val eventColumnName = "event_name"
  val seedPath = "src/main/resources/seed"

  def main(args: Seq[String]): Unit = {

    val spark: Option[SparkSession] = Option(getSparkSession)
    dropDb(dbName, spark)
    createDb(dbName, spark)
    val eventsDF: DataFrame = readDF(spark, seedPath)
    val distinctEvents: Array[Row] = eventsDF.select(eventColumnName).distinct().collect()
    splitAndWrite(spark, eventsDF, distinctEvents)
    printAllTables(spark, distinctEvents)
  }

  private def splitAndWrite(spark: Option[SparkSession], eventsDF: DataFrame, distinctEvents: Array[Row]): Unit = {
    distinctEvents.foreach {
      event: Row =>
        val eventName: String = event.get(0).asInstanceOf[String]
        val eventNameRenamed = renameEvent(eventName)
        val tableName = s"${dbName}.${eventNameRenamed}"
        val currentEventDF = eventsDF.filter(col(eventColumnName) === eventName)
        if (tableExists(tableName, spark)) {
          currentEventDF.write.insertInto(tableName)
        }
        else {
          currentEventDF.write.saveAsTable(tableName)
        }
    }
  }

  private def readDF(spark: Option[SparkSession], path: String): DataFrame = {
    spark.get.read.json(path)
  }

  private def createDb(dbName: String, spark: Option[SparkSession]): DataFrame = {
    logger.info(s"create database if not exists $dbName")
    spark.get.sql(s"create database if not exists $dbName")
  }

  private def dropDb(dbName: String, spark: Option[SparkSession]): DataFrame = {
    logger.info(s"drop database if exists $dbName cascade")
    spark.get.sql(s"drop database if exists $dbName cascade")
  }

  private def printAllTables(spark: Option[SparkSession], distinctEvents: Array[Row]) = {
    logger.info(s"show tables in ${dbName}")
    spark.get.sql(s"show tables in ${dbName}").show(truncate = false)
    distinctEvents.foreach {
      event: Row =>
        val eventName: String = event.get(0).asInstanceOf[String]
        val eventNameRenamed = renameEvent(eventName)
        val tableName = s"${dbName}.${eventNameRenamed}"
        logger.info(s"select * from ${tableName}")
        spark.get.sql(sqlText = s"select * from ${tableName}").show(truncate = false)
    }
  }

  /**
   * since table names can not contain white space or dashes but event name do, it is suggested to:
   * 1. stop naming events with white spaces or dashes
   * 2. in case an event was named with white spaces or dashes, replace those with an underscore ('_')
   * @param eventName
   * @return
   */
  private def renameEvent(eventName: String): String = {
    eventName.replace(" ", "_").replace("-", "_")
  }

  def tableExists(table: String, spark: Option[SparkSession]): Boolean =
    spark.get.catalog.tableExists(table)

  def getSparkSession: SparkSession =
    SparkSession
      .builder
      .master("local[*]")
      .config(new SparkConf())
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .getOrCreate()
}