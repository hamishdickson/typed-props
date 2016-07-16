package typedProps

// not actually convinced at this point I need put
sealed trait PropsA[A]
case class Put[T](key: String, value: T) extends PropsA[Unit]
case class Get[T](key: String) extends PropsA[Option[T]]

object PropsA {
  import cats.free.Free
  import cats.free.Free.liftF
  import cats.{Id, ~>}

  type Props[A] = Free[PropsA, A]

  def put[T](key: String, value: T): Props[Unit] =
    liftF[PropsA, Unit](Put[T](key, value))

  def get[T](key: String): Props[Option[T]] =
    liftF[PropsA, Option[T]](Get[T](key))

  def update[T](key: String, f: T => T): Props[Unit] =
    for {
      maybe <- get[T](key)
      _ <- maybe.map(v => put[T](key, f(v))).getOrElse(Free.pure(()))
    } yield ()

  def impureCompiler: PropsA ~> Id  =
    new (PropsA ~> Id) {
      import scala.collection.mutable
      import scala.io.Source

      // todo - replace with something better
      val kvs = mutable.Map.empty[String, Any]

      // set in build.sbt
      val propFile: String = System.getProperty("property.file")
      val props = Source.fromFile(propFile).getLines()

      val ps = props.foldRight(kvs)((a, b) => {
        val c = a.split("""=""")
        val t = c(0).split(""":""")
        if (t(1) == "Int")
          b + (t(0) -> c(1).toInt)
        else
          b
      })

      def apply[A](fa: PropsA[A]): Id[A] = fa match {
        case Put(key, value) =>
          println(s"put($key, $value)")
          ps(key) = value
          ()
        case Get(key) =>
          println(s"""get($key)""")
          ps.get(key).map(_.asInstanceOf[A])
      }
    }
}

object PropsImplicits {
  import PropsA._

  implicit class IntProps(s: String) {
    def prop[T] = get[T](s).foldMap(impureCompiler)
  }
}
