package sjs.dom.builder

import scala.collection.immutable.{ Seq => ISeq }

import org.scalajs.dom
import org.scalajs.dom.raw

object Fraggable {
	implicit def NodeIsFraggable[T<:raw.Node]:Fraggable[T]	=
			by(identity)
		
	implicit val StringIsFraggable:Fraggable[String]	=
			by(dom.document.createTextNode)
	
	implicit def ISeqIsFraggable[T:Fraggable]:Fraggable[ISeq[T]]	=
			by { nodes =>
				val out	= dom.document createDocumentFragment ()
				nodes map asNode[T] foreach out.appendChild
				out
			}
			
	implicit def OptionIsFraggable[T:Fraggable]:Fraggable[Option[T]]	=
			by { nodes =>
				val out	= dom.document createDocumentFragment ()
				nodes map asNode[T] foreach out.appendChild
				out
			}
	
	//------------------------------------------------------------------------------
	
	def apply[T:Fraggable]:Fraggable[T]	=
			implicitly[Fraggable[T]]
		
	def by[T](asNodeFunc:T=>raw.Node):Fraggable[T]	=
			new Fraggable[T] {
				def asNode(it:T):raw.Node = asNodeFunc(it)
			}
			
	def asNode[T:Fraggable](it:T):raw.Node	=
			implicitly[Fraggable[T]] asNode it
}

// BETTER use a Contravariant typeclass to keep this invariant
trait Fraggable[-T] {
	def asNode(it:T):raw.Node
}
