package publishing

import java.io.File

import org.apache.avro.SchemaCompatibility.SchemaCompatibilityType
import org.apache.avro.{Schema, SchemaCompatibility}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters.CollectionHasAsScala

class CDCTests extends AnyWordSpec with Matchers {

  "Published schemas" when {

    new File("src/test/avro").listFiles().filter(_.isDirectory).foreach { cdcFolder =>

      s"validated against ${cdcFolder.getName}" should {

        cdcFolder.listFiles().filter(_.getName.endsWith(".avsc")).foreach { schemaFile =>

          s"be compatible regarding ${schemaFile.getName}" in {

            val producerSchemaFile = new File(s"src/main/avro/${schemaFile.getName}")
            assert(producerSchemaFile.exists(), "Expected producer schema doesn't exist!")

            val consumerSchema = Schema.parse(schemaFile)
            val producerSchema = Schema.parse(producerSchemaFile)

            val schemaPairCompatibility =
              SchemaCompatibility.checkReaderWriterCompatibility(consumerSchema, producerSchema)

            if (schemaPairCompatibility.getResult.getCompatibility == SchemaCompatibilityType.COMPATIBLE)
              succeed
            else
              fail(schemaPairCompatibility.getResult.getIncompatibilities.asScala.mkString(", "))
          }
        }
      }
    }
  }
}
