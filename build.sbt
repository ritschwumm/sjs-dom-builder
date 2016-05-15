inThisBuild(Seq(
	organization	:= "de.djini",
	version			:= "0.0.1-SNAPSHOT",
	
	scalaVersion	:= "2.11.8",
	scalacOptions	++= Seq(
		"-feature",
		"-deprecation",
		"-unchecked",
		"-Ywarn-unused-import",
		"-Xfatal-warnings"
	)
))

lazy val `sjsdom` =
	(project in file("."))
	.aggregate(
		`sjsdom-macros`,
		`sjsdom-core`,
		`sjsdom-example`
	)
	.settings(
		publishArtifact := false
		//publish		:= {},
		//publishLocal	:= {}
	)
	
lazy val `sjsdom-macros`	=
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
		
lazy val `sjsdom-core`	=
		(project in file("sub/core"))
		.enablePlugins(
			ScalaJSPlugin
		)
		.dependsOn(
			`sjsdom-macros`
		)
		.settings()
		
lazy val `sjsdom-example`	=
		(project in file("sub/example"))
		.enablePlugins(
			ScalaJSPlugin
		)
		.dependsOn(
			`sjsdom-core`
		)
		.settings()
		
TaskKey[Seq[File]]("bundle")	:=
		Vector(
			(fastOptJS	in (`sjsdom-example`, Compile)).value.data
		)
