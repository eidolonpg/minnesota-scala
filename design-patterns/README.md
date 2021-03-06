Scala Design Patterns
=====================

*Presentation Date*: August 16, 2017
*Topic* Practical strategies and patterns for writing effective Scala.

# Project Organization

- The `examples` package contains in-order examples, E00-E06, referenced in the talk.
- The rest of the code represents the "final" version of the code.

## Notes

- Many functions are stubbed, the focus is on the types and organization
- This is not a runnable application and does not have runnable tests. It's just for
  browsing and reference.
- See the ScalaDoc for more information about a given part.

# Context

We have been tasked with implementing the Foo domain. Users need to be able to create
Foos, and need those Foos to be validated prior to entry. More specifically each Foo...

- Has values associated to a Bar. Each Bar specifies a valid range for values.
  A Foo should only have values associated to a Bar that exists, and a Foo should only have values
  which satisfy their Bar's valid range.
- Should have a unique name.

Additionally we might need to notify some downstream system after creating a Foo.

# Design Patterns

- Abstracting Computation
- Weighted Companion Monad (Represent computation as a sequence of transformations)
- Errors are Data
- Type Classes

## Design Patterns to Read About

- Free Monads

# Links to External Resources

- [Cats](http://typelevel.org/cats/) - Scala library for Functional Programming
- [Scalaz](https://github.com/scalaz/scalaz) - Scala library for Functional Programming
- [Monix](https://monix.io/) - Asynchronous programming library for Scala
- [Cats: Type Classes](http://typelevel.org/cats/typeclasses.html)
- [Cats: Free Monads](http://typelevel.org/cats/datatypes/freemonad.html)
