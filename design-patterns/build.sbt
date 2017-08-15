name := "design-patterns"
organization := "com.nerdery"
version := "1.0.0-FINAL"
scalaVersion := "2.12.3"
scalaVersion in ThisBuild := "2.12.3"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "0.9.0",
  "io.monix" %% "monix" % "2.3.0",
  "io.monix" %% "monix-cats" % "2.3.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test"
)

parallelExecution in Test := false
