import junit.framework.TestCase
import scala.collection.mutable.ListBuffer
 
class Person {
 
  val pets = new ListBuffer[String]()
}
 
class ScalaTest extends TestCase {
 
  def testFlatMap() {
 
    // let's create some people, add some pets to them
    // the end result is we went to know all the pet's that these people have
    val p = new Person;
    p.pets += "cat"
    p.pets += "dog"
 
    val d = new Person;
    d.pets += "fish"
    d.pets += "bird"
    d.pets += "dog"
 
    val people = p :: d :: Nil
 
    val pets = people.flatMap(_.pets)
 
    // show me all the pets that we have in our people collection
    println(pets) // List(cat, dog, fish, bird, dog)

 
  }
 
}
