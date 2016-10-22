package sjs.dom.builder

import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

class Builder[T] extends Dynamic {
	def applyDynamic(name:String)(args:Any*):T	=
			macro BuilderMacros.applyDynamic[T]
	
	def applyDynamicNamed(name:String)(args:(String,Any)*):T	=
			macro BuilderMacros.applyDynamicNamed[T]
}

final class BuilderMacros(val c:Context) {
	import c.universe._
	
	def applyDynamic[T](name:c.Tree)(args:Tree*)(implicit targetTypeTag:c.WeakTypeTag[T]):c.Tree	= {
		val Select(_, TermName(elementName))	= c.prefix.tree
		
		val Literal(Constant(methodName:String))	= name
		if (methodName != "apply")	c.abort(c.enclosingPosition, s"unhandled applyDynamic call to Builder member '${methodName}'")
		
		val modifications	= args.toList map { arg =>
			q"el.appendChild($arg)"
		}	
		
		q"""{
			val el	= _root_.org.scalajs.dom.document.createElement($elementName).asInstanceOf[$targetTypeTag]
			..$modifications
			el
		}"""
	}
	
	def applyDynamicNamed[T](name:c.Tree)(args:Tree*)(implicit targetTypeTag:c.WeakTypeTag[T]):c.Tree	= {
		val Select(_, TermName(elementName))	= c.prefix.tree
		
		val Literal(Constant(methodName:String))	= name
		if (methodName != "apply")	c.abort(c.enclosingPosition, s"unhandled applyDynamicNamed call to Builder member '${methodName}'")
	
		val modifications	= args.toList map { arg =>
			val q"scala.Tuple2.apply[$kt,$vt]($kv, $av)" = arg
			val Literal(Constant(argName:String)) = kv
				
			if (argName != "") {
				val fieldName	= TermName(argName)
				q"el.$fieldName = $av"
			}
			else {
				q"el.appendChild($av)"
			}
		}
		
		q"""{
			val el	= _root_.org.scalajs.dom.document.createElement($elementName).asInstanceOf[$targetTypeTag]
			..$modifications
			el
		}"""
	}
}
