package sjsdom

import scala.language.implicitConversions
import scala.collection.immutable.{ Seq => ISeq }

import org.scalajs.dom
import org.scalajs.dom.raw

trait Conversions {
	implicit def stringAsNode(s:String):raw.Node	= dom.document createTextNode s
	
	implicit def seqAsNode[T<%raw.Node](it:ISeq[T]):raw.Node	= {
		val out	= dom.document createDocumentFragment ()
		it foreach { jt => out appendChild jt }
		out
	}
	implicit def optionAsNode[T<%raw.Node](it:Option[T]):raw.Node	= {
		val out	= dom.document createDocumentFragment ()
		it foreach { jt => out appendChild jt }
		out
	}
	
	def fragment(its:raw.Node*):raw.Node	= {
		val out	= dom.document createDocumentFragment ()
		its foreach out.appendChild
		out
	}
}
