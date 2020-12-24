scalaVersion := "2.13.3" // Please make sure to sync the Scala version with the CircleCI docker image

sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue
avroScalaSource in Compile := (sourceManaged in Compile).value

libraryDependencies += "org.apache.avro" % "avro" % "1.10.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
