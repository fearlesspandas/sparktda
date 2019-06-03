// package Simp
// import scala.collection.mutable.ArrayBuffer
// import org.apache.spark.{SparkConf, SparkContext}
//
//
// class complex (S: simplex) extends Serializable {
//   val base = S
//   //val cmplx = power2()
//   def power[A](t: Set[A]): Set[Set[A]] = {
//         @annotation.tailrec
//         def pwr(t: Set[A], ps: Set[Set[A]]): Set[Set[A]] =
//           if (t.isEmpty) ps
//           else pwr(t.tail, ps ++ (ps map (_ + t.head)))
//
//         pwr(t, Set(Set.empty[A])) //Powerset of ∅ is {∅}
//       }
//   def power2(t: simplex): Unit = {
//     // val conf = new SparkConf()
//     //   .setAppName("NBC")
//     //   .setMaster("local[4]")
//     //
//     val s = t.v
//     var Smplx = s.toDF
//     var Cmplx = Smplx.map(a => power[Int](a))
//     var out = Cmplx.map(a => a.toArray)
//     Cmplx.collect()
//     //println(Cmplx.collect().foreach(println))
//
//   }
// }
