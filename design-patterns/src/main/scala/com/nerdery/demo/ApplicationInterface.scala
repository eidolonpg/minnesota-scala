package com.nerdery.demo

import com.nerdery.demo.domain.{EntityVersion, Foo}
import com.nerdery.demo.error.{FooError, FooErrorTransformer}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future

/**
  * This is an example of how some external interface (e.g. an HTTP controller) might
  * be implemented. I'm eschewing something like a JSON body in favor of focusing on the
  * core concepts. Here we're able to see a different implementation of the same monadic
  * pattern using map/etc. operations rather than a for-comprehension.
  */
object ApplicationInterface {
  def createFoo(request: String): Future[String] = {
    FooRequestParser.parseRequest(request)
      .flatMap(Foos.createFoo)
      .map(FooResponseSerializer.serializeResponse)
      .onErrorHandle(InterfaceErrorHandler.handleError)
      .runAsync
  }

  /**
    * Treat this like a public API for parsing requests.
    * Perhaps it doesn't actually use a Task, and uses a Try monad instead -- we have options.
    */
  object FooRequestParser {
    def parseRequest(request: String): Task[Foo] = ???
  }

  /**
    * Treat this like a public API for serializing responses for the Foo domain for
    * this specific interface.
    */
  object FooResponseSerializer {
    def serializeResponse(foo: (Foo, EntityVersion)): String = ???
  }

  /**
    * Treat this like the public API for handling all errors for this interface. We have FooError
    * right now, and we might have BarError in the future. This also allows things like database
    * errors to be safely transformed into something consumable. In this case we just return a message.
    */
  object InterfaceErrorHandler {
    def handleError(e: Throwable): String = {
      e match {
        case fe: FooError => FooErrorTransformer.asMessage(fe).mkString(",")
        case _ => e.getMessage
      }
    }
  }
}
