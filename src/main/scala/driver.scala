
//import Simp.complex
// import Simp.simplex
// import org.apache.spark.{SparkConf, SparkContext}
// import scala.collection.mutable.ArrayBuffer
// import classify.{NBC,Category}
// import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
// import org.apache.spark.mllib.util.MLUtils
// object driver {
//   def main(args: Array[String]): Unit = {
//     val conf = new SparkConf()
//       .setAppName("NBC")
//       .setMaster("local[2]")
//     val sc = new SparkContext(conf)
//     val sqlContext= new org.apache.spark.sql.SQLContext(sc)
//       import sqlContext.implicits._
//     // var N = 5
//     // var c1 = new Category(N,"one",Vector(0.0,0.0,0.0,0.0,0.0),Vector(1.0,1.0,0.5,1.0,1.0))
//     // var c2 = new Category(N,"three",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c3 = new Category(N,"four",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c4 = new Category(N,"five",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c5 = new Category(N,"six",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c6 = new Category(N,"seven",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c7 = new Category(N,"eight",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c8 = new Category(N,"nine",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c9 = new Category(N,"ten",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var c10 = new Category(N,"two",Vector(0.0,1.0,1.0,0.0,0.0),Vector(1.0,1.0,1.0,1.0,1.0))
//     // var C = ArrayBuffer[Category](c1,c2,c3,c4,c5,c6,c7,c8,c9,c10)
//     // for (i<- 1 to 1000){
//     //   C += new Category(N,s"$i",Vector(0.0,0.0,0.0,0.0,0.0),Vector(0.5,0.5,0.5,1.0,1.0))
//     // }
//     // //C.foreach(c => println(c.name))
//     // var nbc = new NBC(N, Array[Category](C.toArray:_*))
//     // var x = Vector(0.0,0.0,0.0,0.0,0.0)
//     //
//     // var s = new simplex(Set((10000,7)))
//     // var cmplx = new complex(s)
//     // cmplx.power2(s)
//     //println(nbc.classify(x))
//     //println(s.v)
//
//
//
//   }
// }
