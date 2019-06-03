package classify
import scala.collection.mutable.HashMap
import scala.collection.immutable.Vector
import org.apache.spark.{SparkConf, SparkContext}
import classify.Category
import scala.math._
class NBC(N: Int, Cts: Array[Category]) extends Serializable{
  val Cats = Cts

  def normpdf(x: Double,mu: Double,va: Double): Double = {
    return (1/math.sqrt(2*va*math.Pi))*math.exp((-1)*(x-mu)*(x-mu)/(2*va))
  }
  def multidimpdf(x: Vector[Double], m: Vector[Double], v: Vector[Double]): Double = {
    var totalprob = 1.0
    println("multidimpdf was called")
    for (i <- 0 to m.size -1) {
      totalprob *= normpdf(x(i),m(i),v(i))
    }
    return totalprob
  }
  def classify(x: Vector[Double]): String = {
    var v1 = Vector[Double](1d to x.size by 1d :_*)
    var v2 = v1
    var maxcat = new Category(x.size,"null",v1,v2)
    var maxprob = 0.0
    val conf = new SparkConf()
      .setAppName("NBC")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)
    var Cats = sc.parallelize(Cts)
    var probs = Cats.map(c => (c,multidimpdf(x,c.means,c.variance)))
    var mostprobs = probs.reduce((a,b) => {
      if(a._2 < b._2) b else a
    })
    //mostprobs.collect()
    print("Your Most Likely Category is: ")
    println(mostprobs._1.name)

    // for (c <- Cts) {
    //   var prb = multidimpdf(x,c.means,c.variance)
    //   if (maxprob < prb){
    //     maxprob = prb
    //     maxcat = c
    //   }
    // }
    sc.stop()
    return maxcat.name
  }

}
