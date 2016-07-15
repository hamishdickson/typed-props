# typed-props

Very simple proof of concept - wouldn't it be ace if you could have a type safe properties file?

For example, I should be able to add type parameters to properties in a properties file like so

```
my.property.is.an.int:Int=2
this.one.is.a.string:String=test
```

and then be absolutely sure that when I use them in my code they conform to what my code requires

```scala
val ip: Option[Int] = "my.property.is.an.int".prop[Int]
```

That is the hope of this project.

# Status

This project is in proof of concept phase at the moment. The idea might not go anywhere or equally may be embarrassingly easy. If you need a good library to do this now, then you probably need [config](https://github.com/typesafehub/config)
