package Simp
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
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
         .option("header", "true") //first line in file has headers
         .option("mode", "DROPMALFORMED")
         .option("delimiter",",")
         .load(filename)
  }

  var DF = load(source)
  def SimpDF(n: Integer ,dim: Integer): DataFrame =
    {
      val a = (0 to dim)
      var df = a.toDF("0")
      for (x <- 1 to n*dim) {
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
    def lor(v: Array[String]): DataFrame = {
      val lor = filterCmplx(v).agg(min("ind")).withColumnRenamed("min(ind)","LOR")
      val lorsimp = lor.join(DF).filter(DF("ind") === lor("LOR"))
      val vv = ArrayBuffer(v:_*)
      vv += "LOR"
      return lorsimp.select(vv.head,vv.tail:_*)
    }
    def mat(sc: SparkContext): BlockMatrix = {
      val E = Matrices.dense(2,2,Array(1,2,3,4))
      val D = Matrices.dense(2,2,Array(1,2,3,4))
      val blocks = sc.parallelize(Seq(((0,0),D),((0,1),E)))
      val bmat = new BlockMatrix(blocks,2,2)
      return bmat
    }

    def boundaryColFromInc(df:DataFrame): DataFrame = {
      df.
    }
    def minInt(n:Int,m:Int):Int={
      return if (n<=m) n else m
    }
    def choose(n:Int, k:Int):Int={
      if (0<=k && k<=n){
            var num=1
            var den=1
            var nn = n
            for (t <- 1 to minInt(k,nn-k)+1){
                num*=nn
                den*=t
                nn-=1
            }
            return num/den
      }
      else{
          return 0
        }
    }
    def subSimps(k:Int,s:Seq):DataFrame = {

    }
    def getSimpsK(k:Int,simp:DataFrame):DataFrame={
      val cnt = simp.count
      if(k<cnt){
        val v = simp.select("v").map(_.getString(0)).collect.toList
        val I = v.map(x => (v.indexOf(x),x.toString.toInt)).toDF("i","v")
        //var d = Seq(1 to cnt.toInt:_*).map(x => if(x%2 == 0) 1 else -1).toDF("orientation")
        return simp.join(I).filter(simp("v").equalTo(I("v"))).withColumn("or",when(col("i").mod(2).equalTo(0),lit(1)).otherwise(lit(-1)))



    }
    else{ return simp }
    }
}
