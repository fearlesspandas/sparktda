package Simp
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{Row, DataFrame, DataFrameWriter}
import org.apache.spark.mllib.linalg.{Matrices,Matrix}
import org.apache.spark.mllib.linalg.distributed.BlockMatrix


// import org.apache.spark.sql.SQLContext.implicits._
@SerialVersionUID(100L)
class simplex (source: String, spark: SparkSession) extends Serializable {
  import spark.implicits._
  def load(filename: String): DataFrame = {
    return spark.read
         .format("csv")
         .option("header", "false") //first line in file has headers
         .option("mode", "DROPMALFORMED")
         .option("delimiter",",")
         .load(filename)
  }

  var DF = load(source)
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
    def filterCmplx(smps : Array[(String,String)]): DataFrame = {
      var t1 = DF
      for(i <- 0 to smps.size-1){
        val t2 = t1.filter(t1(smps(i)._1).equalTo(smps(i)._2))
        t1 = t2
      }
      return t1
    }
    def filterCmplx(smps : Array[String]): DataFrame = {
      var t1 = DF
      for(i <- 0 to smps.size-1){
        val t2 = t1.filter(t1(smps(i)).equalTo(1))
        t1 = t2
      }
      return t1
    }
    def mat(sc: SparkContext): BlockMatrix = {
      val E = Matrices.dense(2,2,Array(1,2,3,4))
      val D = Matrices.dense(2,2,Array(1,2,3,4))
      val blocks = sc.parallelize(Seq(((0,0),D),((0,1),E)))
      val bmat = new BlockMatrix(blocks,2,2)
      return bmat
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
