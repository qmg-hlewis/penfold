organization := "org.huwtl"

name := "penfold"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.1"

scalacOptions ++= Seq( "-deprecation", "-unchecked", "-feature" )

seq(webSettings :_*)

conflictWarning in ThisBuild := ConflictWarning.disable

libraryDependencies ++= Seq(
  "org.scalatra" % "scalatra_2.10" % "2.2.1",
  "org.scalatra" % "scalatra-scalate_2.10" % "2.2.1",
  "ch.qos.logback" % "logback-classic" % "1.0.7" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,compile",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,compile" artifacts Artifact("javax.servlet", "jar", "jar"),
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "com.github.philcali" % "cronish_2.10" % "0.1.3",
  "org.scalaj" % "scalaj-time_2.10.0-M7" % "0.6",
  "com.theoryinpractise" % "halbuilder-core" % "2.0.2",
  "org.json4s" %% "json4s-jackson" % "3.2.4",
  "com.typesafe.slick" %% "slick" % "1.0.1",
  "c3p0" % "c3p0" % "0.9.1.2",
  "org.hsqldb" % "hsqldb" % "2.3.0",
  "com.googlecode.flyway" % "flyway-core" % "2.2",
  "net.debasishg" % "redisclient_2.10" % "2.11",
  "org.mockito" % "mockito-all" % "1.9.0" % "test",
  "org.specs2" % "specs2_2.10" % "2.1.1" % "test",
  "org.scalatra" %% "scalatra-specs2" % "2.2.1" % "test",
  "redis.embedded" % "embedded-redis" % "0.1" % "test"
)

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "clojars.org" at "http://clojars.org/repo/"