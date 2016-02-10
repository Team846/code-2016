enablePlugins(FRCPlugin)

organization := "com.lynbrookrobotics"
teamNumber := 846

name := "code-2016"

version := "0.1-SNAPSHOT"

autoScalaLibrary := false

resolvers += "WPILib-Maven" at "http://team846.github.io/wpilib-maven"
resolvers += "Funky-Repo" at "http://team846.github.io/repo"

libraryDependencies += "com.lynbrookrobotics" % "potassium" % "0.1-SNAPSHOT"

libraryDependencies += "com.lynbrookrobotics" % "funky-dashboard_2.11" % "0.1.0-SNAPSHOT"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

libraryDependencies += "org.mozilla" % "rhino" % "1.7R4"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.19" % Test

robotClass := "com.lynbrookrobotics.sixteen.LaunchRobot"