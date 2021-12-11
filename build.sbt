Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(Seq(
	organization	:= "de.djini",
	version			:= "0.12.0",

	scalaVersion	:= "2.13.7",
	scalacOptions	++= Seq(
		"-feature",
		"-deprecation",
		"-unchecked",
		"-Werror",
		"-Xlint",
		"-Xsource:3",
		// "-Ymacro-debug-lite"
	),

	versionScheme	:= Some("early-semver"),
))

lazy val noTestSettings	=
	Seq(
		test		:= {},
		testQuick	:= {}
	)

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
	(project in file("modules/core"))
	.enablePlugins(
		ScalaJSPlugin
	)
	.dependsOn()
	.settings(
		noTestSettings,
		libraryDependencies ++= Seq(
			"org.scala-lang"	%	"scala-reflect"	% scalaVersion.value	% "compile",
			"org.scala-js"		%%%	"scalajs-dom"	% "2.0.0"				% "compile"
		)
	)

lazy val `sjs-dom-builder-example`	=
	(project in file("modules/example"))
	.enablePlugins(
		ScalaJSPlugin
	)
	.dependsOn(
		`sjs-dom-builder-core`
	)
	.settings(
		noTestSettings,
		// automatically start on import
		scalaJSUseMainModuleInitializer := true,
	)

TaskKey[Seq[File]]("bundle")	:=
	Vector(
		(`sjs-dom-builder-example` / Compile / fastOptJS).value.data
	)
