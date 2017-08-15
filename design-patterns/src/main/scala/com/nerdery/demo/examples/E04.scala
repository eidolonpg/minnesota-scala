package com.nerdery.demo.examples

import com.nerdery.demo.Foos
import com.nerdery.demo.domain.{EntityVersion, Foo}
import com.nerdery.demo.error.{FooError, FooErrorTransformer}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future

object E04 {
  object ApplicationInterface {
    def createFoo(request: String): Future[String] = {
      parseRequest(request)
        .flatMap(Foos.createFoo)
        .map(serializeResponse)
        .onErrorHandle(errorHandler)
        .runAsync
    }

    private def parseRequest(request: String): Task[Foo] = ???

    private def serializeResponse(foo: (Foo, EntityVersion)): String = ???

    private def errorHandler(e: Throwable): String = {
      e match {
        case fe: FooError => FooErrorTransformer.asMessage(fe).mkString(",")
        case _ => e.getMessage
      }
    }
  }
}
