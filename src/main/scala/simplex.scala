package Simp
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.{SparkConf, SparkContext}

@SerialVersionUID(100L)
class simplex (desc: Set[(Integer,Integer)]) extends Serializable {
  def partition(de: Set[(Integer,Integer)]): ArrayBuffer[Set[Int]] = {
    var sims = ArrayBuffer[Set[Int]]()
    var n = 0
    def makeSimp(numIns: Integer,dim: Integer): ArrayBuffer[Set[Int]] = {
      var subsimps = ArrayBuffer[Set[Int]]()
      for (x <- 1 to numIns) {
        var S = Set((n to n+dim).toArray:_*)
        n = n+dim+1
        subsimps += S
      }
      return subsimps
      }
    for ((numIns,dim)<-de) {

      for (s <- makeSimp(numIns,dim)) sims += s
    }
    //de.foreach( (numIns: Integer,dim: Integer) => makeSimp(numIns,dim))
    return sims
  }
  val v = partition(desc)
  def power[A](t: Set[A]): Set[Set[A]] = {
        @annotation.tailrec
        def pwr(t: Set[A], ps: Set[Set[A]]): Set[Set[A]] =
          if (t.isEmpty) ps
          else pwr(t.tail, ps ++ (ps map (_ + t.head)))

        pwr(t, Set(Set.empty[A])) //Powerset of ∅ is {∅}
      }
  def power2(t: Array[simplex]): Unit = {
    var Smplx = t.toDF
    var Cmplx = Smplx.map(a => (a,a.power(Set(a.v))))
    Cmplx.show
  }
}
