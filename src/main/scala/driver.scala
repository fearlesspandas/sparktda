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
    spark.sparkContext.setLogLevel("ERROR")
  // import spark.implicits._
   def main(args: Array[String]): Unit = {
     println("This was printed by main")
     val s = new simplex("/home/jim/git/sparktda/secondfile.csv",spark)
     val D = s.DF
     D.show
     //here a simp is just the collection of it's vertices
     //hence from the incidence matrix we can filter out for
     //simplex d
     val simp = D.filter(col("d").equalTo(1)).select("v")
     s.getBoundary(simp).show
     val out = s.iterBoundary(simp,s.getBoundary(simp))
     out.show
     println("Column Count: " + out.columns.size.toString)
     out.write.mode("overwrite").option("header","true").csv("/data/out2")
     s.deltaK(out,2).show
     //simplex s_k => delta_k(s)
     //col(x in s_k) = 1 if  order(<x in (y: y in s_k-1))%2 == 0 else -1
    //base_df = s => Row(ds_1.vertexset,ds_1. col(s in s_k))

     // val spark = SparkSession.builder()
     // .master("local")
     // .appName("example of SparkSession")
     // .getOrCreate()
     // SparkSession.builder()

   }
}
