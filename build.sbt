inThisBuild(Seq(
	organization	:= "de.djini",
	version			:= "0.3.0",
	
	scalaVersion	:= "2.12.0",
	scalacOptions	++= Seq(
		"-feature",
		"-deprecation",
		"-unchecked",
		"-Ywarn-unused-import",
		"-Xfatal-warnings"
		// "-Ymacro-debug-lite"
	),
	scalaJSUseRhino	:= true
))

lazy val `sjs-dom-builder` =
	(project in file("."))
	.aggregate(
		`sjs-dom-builder-core`,
		`sjs-dom-builder-example`
	)
	.settings(
		publishArtifact := false
		//publish		:= {},
		//publishLocal	:= {}
	)
	
lazy val `sjs-dom-builder-core`	=
		(project in file("sub/core"))
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
