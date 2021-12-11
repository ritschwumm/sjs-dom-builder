package sjs.dom.builder

import org.scalajs.dom._

import sjs.dom.builder.syntax._

object Example {
	def main(args:Array[String]):Unit	= {
		window.onload	= (_:Event) => document.body appendChild simple
	}

	def simple:HTMLDivElement	=
		div(
			"this is a div with a ",
			a(
				href = "https://www.scala-js.org/api/scalajs-dom/0.9.5/",
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
				href = "https://www.scala-js.org/api/scalajs-dom/0.9.5/",
				"link"
			),
			" in it.",
			Vector(div(), div()),
			Vector(" ", "and", " ", "a", " ", "sequence of nodes"),
			a()
		)
}
