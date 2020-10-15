package sjs.dom.builder

import org.scalajs.dom.document
import org.scalajs.dom.window
import org.scalajs.dom.raw._

import sjs.dom.builder.syntax._

object Example {
	def main():Unit	= {
		window.onload	= (_:Event) => document.body appendChild simple
	}

	def simple:HTMLDivElement	=
			div(
				"this is a div with a ",
				a(
					href = "https://www.scala-js.org/api/scalajs-dom/0.9.0/",
					"link"
				),
				" in it."
			)

	def complex:HTMLDivElement	=
			div(
				className	= "test",
				onclick		= (ev:Event) => println("click"),
				"this is a div with a ",
				a(
					href = "https://www.scala-js.org/api/scalajs-dom/0.9.0/",
					"link"
				),
				" in it.",
				Vector(div(), div()),
				Vector(" ", "and", " ", "a", " ", "sequence of nodes"),
				a()
			)
}
