package classify
import scala.collection.immutable.Vector

class Category (N:Int,nm: String,m: Vector[Double],v: Vector[Double]) extends Serializable{
  val means = Vector[Double](m:_*)
  val variance = Vector[Double](v:_*)
  val name = nm
  val dim = N
}
