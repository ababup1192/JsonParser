package org.ababup1192

import scala.util.parsing.combinator._

object JsonParser extends RegexParsers {

  def stringLiteral: Parser[String] = "\"[-a-zA-Z0-9:*/+,#$%& ]+\"".r ^^ {
    _.replaceAll("\"", "")
  }

  def intLiteral: Parser[Int] = """[1-9][0-9]*|0""".r ^^ {
    _.toInt
  }

  def floatingPointNumber: Parser[Double] = """-?[0-9]+\.[0-9]+""".r ^^ {
    _.toDouble
  }

  def value: Parser[Any] = obj | arr |
    stringLiteral | floatingPointNumber | intLiteral |
    "null" ^^ { _ => null } | "true" ^^ { _ => true } | "false" ^^ { _ => false }

  def obj: Parser[Map[String, Any]] = "{" ~> repsep(member, ",") <~ "}" ^^ {
    Map() ++ _
  }

  def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

  def member: Parser[(String, Any)] = stringLiteral ~ ":" ~ value ^^ { case k ~ ":" ~ v => (k, v) }

  def parse(input: String) = parseAll(value, input)
}
