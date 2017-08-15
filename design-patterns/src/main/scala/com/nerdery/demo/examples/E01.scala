package com.nerdery.demo.examples

object E01 {
  trait C[+R] {
    def run: R = ???

    def flatMap[U](f: (R) => C[U]): C[U] = ???
  }

  object C {
    def delay[R](f: => R): C[R] = ???

    def now[R](f: R): C[R] = ???

    def failure[R](e: Throwable): C[R] = ???
  }
}
