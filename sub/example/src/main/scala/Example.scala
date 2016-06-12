package sjsdom

import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import org.scalajs.dom.raw

import sjsdom.syntax._

@JSExport
object Example {
	@JSExport
	def main() {
		dom.window.onload	= (_:raw.Event) => dom.document.body appendChild simple
	}

	def simple:raw.HTMLDivElement	=
			div(
				"this is a div with a ",
				a(
					href = "https://www.scala-js.org/api/scalajs-dom/0.9.0/",
					"link"
				),
				" in it."
			)
	
	def complex:raw.HTMLDivElement	=
			div(
				className	= "test",
				onclick		= (ev:raw.Event) => println("click"),
				"this is a div with a ",
				a(
					href = "https://www.scala-js.org/api/scalajs-dom/0.9.0/",
					"link"
				),
				" in it.",
				Vector[raw.Node](" ", "and", " ", "a", " ", "sequence of nodes", a()),
				fragment(" ", "and", " ", "a", " ", "sequence of nodes", a())
			)
}
