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
		// TODO use something like this to get at the variable name
		// c.asInstanceOf[reflect.macros.runtime.Context].callsiteTyper.context.enclosingContextChain.map(_.tree.asInstanceOf[c.Tree])
		val Select(_, TermName(elementName))		= c.prefix.tree
		val Literal(Constant(methodName:String))	= name
		if (methodName != "apply")	c.abort(c.enclosingPosition, s"unhandled applyDynamic call to Builder member '${methodName}'")
		
		build[T](elementName, targetTypeTag, args map Right.apply)
	}
	
	def applyDynamicNamed[T](name:c.Tree)(args:Tree*)(implicit targetTypeTag:c.WeakTypeTag[T]):c.Tree	= {
		val Select(_, TermName(elementName))		= c.prefix.tree
		val Literal(Constant(methodName:String))	= name
		if (methodName != "apply")	c.abort(c.enclosingPosition, s"unhandled applyDynamicNamed call to Builder member '${methodName}'")
	
		build[T](elementName, targetTypeTag, args map { arg =>
			val q"scala.Tuple2.apply[$kt,$vt]($kv, $av)" = arg
			val Literal(Constant(argName:String)) = kv
			if (argName != "")	Left(TermName(argName) -> av)
			else				Right(av)
		})
	}
	
	// either property or child
	private def build[T](elementName:String, targetTypeTag:c.WeakTypeTag[T], args:Seq[Either[(TermName,c.Tree),c.Tree]]):c.Tree	= {
		q"""{
			val el	= _root_.org.scalajs.dom.document.createElement($elementName).asInstanceOf[$targetTypeTag]
			..${ args.toList map {
				case Left((fieldName,av))	=> q"el.$fieldName = $av"
				case Right(av)				=> q"el.appendChild(_root_.sjs.dom.builder.Fraggable.asNode($av))"
			} }
			el
		}"""
	}
}
