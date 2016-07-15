package typedProps

import org.scalatest.{FlatSpec, Matchers}

class TypedPropsTest extends FlatSpec with Matchers {
  "A property finder" should "use type classes" in {
    import typedProps.PropsImplicits._

    val i: Option[Int] = "test.int.prop".prop[Int]

    i should be (Some(2))
  }
}
