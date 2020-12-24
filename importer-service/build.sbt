scalaVersion := "2.13.1"

sourceGenerators in Compile += (avroScalaGenerateSpecific in Compile).taskValue
avroScalaSource in Compile := (sourceManaged in Compile).value

libraryDependencies += "org.apache.avro" % "avro" % "1.10.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
