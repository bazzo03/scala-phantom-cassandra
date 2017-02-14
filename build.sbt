name := "my-akka-http"

version := "1.0"

scalaVersion := "2.11.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("websudos", "oss-releases"),
  "spray repo"                       at "http://repo.spray.io",
  "Typesafe repository snapshots"    at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases"     at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com"
)

libraryDependencies ++= {

  val akkaV = "2.4.4"
  val phantomV = "2.0.2"
  val akkaHttpV   = "10.0.3"
  val scalaTestV  = "3.0.1"

  Seq(
    "com.outworkers"      %%  "phantom-dsl"                 % phantomV,
    "com.outworkers"      %%  "phantom-streams"             % phantomV,
    "com.websudos"        %%  "util-testing"                % "0.13.0"    % "test, provided",
    "com.typesafe.akka"   %%  "akka-slf4j"                  % akkaV,
    "com.typesafe.akka"   %%  "akka-stream-testkit"         % akkaV,
    "com.typesafe.play"   %%  "play-streams-experimental"   % "2.4.6",
    "com.typesafe.akka"   %%  "akka-actor"                   % akkaV,
    "com.typesafe.akka"   %%  "akka-stream"                  % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"                 % akkaV,
    "com.typesafe.akka"   %%  "akka-http"                    % akkaHttpV,
    "com.typesafe.akka"   %%  "akka-http-spray-json"         % akkaHttpV,
    "com.typesafe.akka"   %%  "akka-http-testkit"            % akkaHttpV,
    "org.scalatest"       %%  "scalatest"                    % scalaTestV % "test"

  )
}




    