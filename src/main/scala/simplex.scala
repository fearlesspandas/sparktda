package Simp
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{Row, DataFrame, DataFrameWriter}
// import org.apache.spark.sql.SQLContext.implicits._
@SerialVersionUID(100L)
class simplex (desc: Set[(Integer,Integer)], spark: SparkSession) extends Serializable {
  import spark.implicits._
  // val spark = SparkSession.builder().getOrCreate()
  // import spark.implicits._
  //  def main(args: Array[String]): Unit = {
  //    // val spark = SparkSession.builder()
  //    // .master("local")
  //    // .appName("example of SparkSession")
  //    // .getOrCreate()
  //    // SparkSession.builder()
  //
  //  }
  // def partition(de: Set[(Integer,Integer)]): ArrayBuffer[Set[Int]] = {
  //   var sims = ArrayBuffer[Set[Int]]()
  //   var n = 0
  //   def makeSimp(numIns: Integer,dim: Integer): DataFrame[Set[Int]] = {
  //     var subsimps = ArrayBuffer[Set[Int]]()
  //     for (x <- 1 to numIns) {
  //       var S = Set((n to n+dim).toArray:_*)
  //       n = n+dim+1
  //       subsimps += S
  //     }
  //     return subsimps
  //     }
  //   for ((numIns,dim)<-de) {
  //
  //     for (s <- makeSimp(numIns,dim)) sims += s
  //   }
  //   //de.foreach( (numIns: Integer,dim: Integer) => makeSimp(numIns,dim))
  //   return sims
  // }
  def SimpDF(n: Integer ,dim: Integer): DataFrame =
    {

      // var n = 0
      // var subsimps = Set[Set[Int]]()
      // var S = Set((0 to dim)
      // n = n+dim+1
      val a = (0 to dim)
      var df = a.toDF("0")
      for (x <- 1 to n*dim) {
             // var S = Set((n to n+dim).toArray:_*)
             // n = n+dim+1
             // subsimps += S
             val tmp = df.withColumn(x.toString, df((x-1).toString) + 1)
             df = tmp
           }
      return df

    }
  //val v = partition(desc)
  // def power[A](t: Set[A]): Set[Set[A]] = {
  //       @annotation.tailrec
  //       def pwr(t: Set[A], ps: Set[Set[A]]): Set[Set[A]] =
  //         if (t.isEmpty) ps
  //         else pwr(t.tail, ps ++ (ps map (_ + t.head)))
  //
  //       pwr(t, Set(Set.empty[A])) //Powerset of ∅ is {∅}
  //     }
  // def power2(t: DataFrame): Unit = {
  //   var Smplx = t
  //   var Cmplx = Smplx.map(a => (a,a.power(Set(a))))
  //   Cmplx.show
  // }
}
