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

    def minInt(n:Int,m:Int):Int={
      return if (n<=m) n else m
    }

    //bdry(s) => s_k-1
    def getBoundary(simp:DataFrame):DataFrame={
      val cnt = simp.count

        val v = simp.select("v").map(_.getString(0)).collect.toList
        val I = v.map(x => (v.indexOf(x),x.toString.toInt)).toDF("i","v")
        //var d = Seq(1 to cnt.toInt:_*).map(x => if(x%2 == 0) 1 else -1).toDF("orientation")
        return simp.join(I,"v").withColumn("or",when(col("i").mod(2).equalTo(0),lit(1)).otherwise(lit(-1))).select("v","or")
    }
    //row_v=>
    //
      //    col(S-v) = 1,-1,0 is added to dataframe
      //
    def simpnm(simp:DataFrame): String = {
        var nm = simp.map("(" + _.getString(0) + ")").collect.toList
        var name = ""
        for(i <- 0 to nm.size - 1 ){
          name +=nm(i)
        }
        return name
    }
    def iterBoundary(simp: DataFrame,baseDF: DataFrame):DataFrame = {
      val v = simp.select("v").map(_.getString(0)).collect.toList
      var cmplx = baseDF
      val coldf = cmplx.columns.toSeq.toDF("column_name")
      for(i <- 0 to v.size-1) {
        //println("vertex removed:" + i.toString)
        var s = simp.filter(!col("v").equalTo(v(i)))
        var name = simpnm(s)
        if(coldf.filter(col("column_name").equalTo(name)).count == 0){
          val b = getBoundary(s).withColumnRenamed("or", name).withColumnRenamed("v","v_"+name)
          //b.show
          var tmp = cmplx.join(b,b("v_"+name)===cmplx("v"),"left_outer").drop("v_" + name)
          cmplx = tmp
          //println("total working complex")

          //cmplx.show()
          val tmp2 = if (s.count > 2) iterBoundary(s,cmplx) else cmplx
          cmplx = tmp2
        }

      }

//      val x = getBoundary(simp)
      return cmplx
    }
    def writeAllCmplx(incmat: DataFrame,outdir: String): Unit = {
      val cols = incmat.columns.toSeq
      for (i <- 1 to cols.size -1 ){
        var s = incmat.filter(col(cols(i)).equalTo(1)).select("v")
        var cmplx = iterBoundary(s,getBoundary(s))
        cmplx.write.mode("overwrite").option("header","true").csv(outdir + "/" + cols(i))
      }
    }
    def deltaK(cmplx: DataFrame,k: Int) : DataFrame = {
      val c = cmplx.columns.toSeq.toDF
      val cc = c.map(row => (row.getString(0),row.getString(0).count(_==')') ))
      val colseq = cc.filter(col("_2").equalTo(k)).select("_1").map(_.getString(0)).collect.toSeq
      val colbuff = ArrayBuffer(colseq:_*)
      colbuff += "v"
      return cmplx.select(colbuff.map(name => col(name)):_*)

    }

}
