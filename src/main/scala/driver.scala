import Simp.simplex
import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable.ArrayBuffer
import classify.{NBC,Category}
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.util.MLUtils
object driver {
  // val spark = SparkSession.builder().getOrCreate()
  // import spark.implicits._
   def main(args: Array[String]): Unit = {
     // val spark = SparkSession.builder()
     // .master("local")
     // .appName("example of SparkSession")
     // .getOrCreate()
     // SparkSession.builder()

   }
}
