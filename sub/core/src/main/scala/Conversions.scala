package sjs.dom.builder

import scala.language.implicitConversions
import scala.collection.immutable.{ Seq => ISeq }

import org.scalajs.dom.document
import org.scalajs.dom.raw._

trait Conversions {
	implicit def stringAsNode(s:String):Node	= document createTextNode s
	
	implicit def seqAsNode[T<%Node](it:ISeq[T]):Node	= {
		val out	= document createDocumentFragment ()
		it foreach { jt => out appendChild jt }
		out
	}
	implicit def optionAsNode[T<%Node](it:Option[T]):Node	= {
		val out	= document createDocumentFragment ()
		it foreach { jt => out appendChild jt }
		out
	}
	
	def fragment(its:Node*):Node	= {
		val out	= document createDocumentFragment ()
		its foreach out.appendChild
		out
	}
}
