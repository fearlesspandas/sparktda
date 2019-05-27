import Simp.simplex
import org.apache.spark.{SparkConf, SparkContext}
import scala.collection.mutable.ArrayBuffer
import classify.{NBC,Category}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.util.MLUtils
object driver {
    val spark = SparkSession.builder().getOrCreate()
  // import spark.implicits._
   def main(args: Array[String]): Unit = {
     println("This was printed by main")
     val s = new simplex("/home/landon/git/sparktda/secondfile.csv",spark)
     val D = s.DF
     D.show
     s.filterCmplx(Array("v9")).show
     val lo = D.filter(D("v9").equalTo(1)).agg(min(D("ind")))
     lo.show
     s.filterCmplx(Array("v3","v4","v5")).show
     s.lor(Array("v3","v4","v5")).show
     // val spark = SparkSession.builder()
     // .master("local")
     // .appName("example of SparkSession")
     // .getOrCreate()
     // SparkSession.builder()

   }
}
