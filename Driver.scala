



/* app4.scala */
import org.apache.spark.{SparkConf, SparkContext}

object Driver {
  def notmain(args: Array[String]) = {
    val conf = new SparkConf()
      .setAppName("my-app")
      .setMaster("local[2]")

    val sc = new SparkContext(conf)

  }
}
