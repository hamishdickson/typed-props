# typed-props

Very simple proof of concept - wouldn't it be ace if you could make properties type safe?

For example, I should be able to create the property

```
my.property.is.an.int:Int=2
this.one.is.a.string:String=test
```

and be able to get them safely in my code

```scala
val p: Option[Int] = "my.property.is.an.int".prop[Int]
```

That is the hope of this project.

# Status

This project is in proof of concept phase at the moment. The idea might not go anywhere or equally may be embarrassingly easy. If you need a good library to do this now, then you probably need [config](https://github.com/typesafehub/config)
