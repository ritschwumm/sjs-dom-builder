package sjs.dom.builder

import org.scalajs.dom._

object Fraggable {
	implicit def NodeIsFraggable[T<:Node]:Fraggable[T]	=
		by(identity)

	implicit val StringIsFraggable:Fraggable[String]	=
		by(document.createTextNode)

	implicit def SeqIsFraggable[T:Fraggable]:Fraggable[Seq[T]]	=
		by { nodes =>
			val out	= document .createDocumentFragment ()
			nodes map asNode[T] foreach out.appendChild
			out
		}

	implicit def OptionIsFraggable[T:Fraggable]:Fraggable[Option[T]]	=
		by { nodes =>
			val out	= document .createDocumentFragment ()
			nodes map asNode[T] foreach out.appendChild
			out
		}

	//------------------------------------------------------------------------------

	def apply[T:Fraggable]:Fraggable[T]	=
		implicitly[Fraggable[T]]

	def by[T](asNodeFunc:T=>Node):Fraggable[T]	=
		new Fraggable[T] {
			def asNode(it:T):Node = asNodeFunc(it)
		}

	def asNode[T:Fraggable](it:T):Node	=
		implicitly[Fraggable[T]] asNode it
}

// BETTER use a Contravariant typeclass to keep this invariant
trait Fraggable[-T] {
	def asNode(it:T):Node
}
