package org.ababup1192

import org.scalatest._

class ParserSpec extends FlatSpec with Matchers {
  "Json parser" should "return lisp like value" in {

    val parseResult = JsonParser.parse( """
    {
      "address book" : {
          "name" : "John Smith",
          "address" : {
            "street" : "10 market street",
            "city"   : "San Fransisco, CA",
            "zip"    : 94111
          },
          "phone number" : [
            "080-xxx-xxxx",
            "080-xxx-yyyy"
          ]
      }
    }""")

    parseResult.get should be === Map("address book" ->
      Map(
        "name" -> "John Smith",
        "address" -> Map(
          "street" -> "10 market street", "city" -> "San Fransisco, CA", "zip" -> 94111
        ),
        "phone number" -> List("080-xxx-xxxx", "080-xxx-yyyy")
      )
    )
  }
}
