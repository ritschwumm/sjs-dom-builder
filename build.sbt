inThisBuild(Seq(
	organization	:= "de.djini",
	version			:= "0.0.1",
	
	scalaVersion	:= "2.11.8",
	scalacOptions	++= Seq(
		"-feature",
		"-deprecation",
		"-unchecked",
		"-Ywarn-unused-import",
		"-Xfatal-warnings"
		// "-Ymacro-debug-lite"
	)
))

lazy val `sjs-dom-builder` =
	(project in file("."))
	.aggregate(
		`sjs-dom-builder-macros`,
		`sjs-dom-builder-core`,
		`sjs-dom-builder-example`
	)
	.settings(
		publishArtifact := false
		//publish		:= {},
		//publishLocal	:= {}
	)
	
lazy val `sjs-dom-builder-macros`	=
		(project in file("sub/macros"))
		.enablePlugins(
			ScalaJSPlugin
		)
		.dependsOn()
		.settings(
			libraryDependencies ++= Seq(
				"org.scala-lang"	%	"scala-reflect"	% scalaVersion.value	% "compile",
				"org.scala-js"		%%%	"scalajs-dom"	% "0.9.1"				% "compile"
			)
		)
		
lazy val `sjs-dom-builder-core`	=
		(project in file("sub/core"))
		.enablePlugins(
			ScalaJSPlugin
		)
		.dependsOn(
			`sjs-dom-builder-macros`
		)
		.settings()
		
lazy val `sjs-dom-builder-example`	=
		(project in file("sub/example"))
		.enablePlugins(
			ScalaJSPlugin
		)
		.dependsOn(
			`sjs-dom-builder-core`
		)
		.settings()
		
TaskKey[Seq[File]]("bundle")	:=
		Vector(
			(fastOptJS	in (`sjs-dom-builder-example`, Compile)).value.data
		)
