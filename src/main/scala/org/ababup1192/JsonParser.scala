package org.ababup1192

import scala.util.parsing.combinator._

object JsonParser extends RegexParsers {

  def stringLiteral: Parser[Any] = "\"[-a-zA-Z0-9:*/+,#$%& ]+\"".r

  def intLiteral: Parser[Any] = """[1-9][0-9]*|0""".r

  def floatingPointNumber: Parser[Any] = """-?[0-9]+\.[0-9]+""".r

  def value: Parser[Any] = obj | arr |
    stringLiteral | floatingPointNumber | intLiteral |
    "null" | "true" | "false"

  def obj: Parser[Any] = "{" ~ repsep(member, ",") ~ "}"

  def arr: Parser[Any] = "[" ~ repsep(value, ",") ~ "]"

  def member: Parser[Any] = stringLiteral ~ ":" ~ value

  def parse(input: String) = parseAll(value, input)
}
