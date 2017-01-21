enablePlugins(FRCPlugin, TravisCheckStylePlugin)

organization := "com.lynbrookrobotics"
teamNumber := 846

name := "code-2016"

version := "0.5.0"

robotClass := "com.lynbrookrobotics.sixteen.LaunchRobot"

scalaVersion := "2.11.7"

lazy val vision = project
lazy val rpivision = project

resolvers += "WPILib-Maven" at "http://team846.github.io/wpilib-maven"
resolvers += "Funky-Repo" at "http://team846.github.io/repo"
resolvers += "opencv-maven" at "https://github.com/WPIRoboticsProjects/opencv-maven/raw/mvn-repo"

libraryDependencies += "edu.wpi.first" % "wpilib" % "2017.1.1.rc-2"

libraryDependencies += "com.ctre" % "ctrlib" % "4.4.1.9"

libraryDependencies += "org.opencv" % "opencv-java" % "3.1.0"

libraryDependencies += "com.lynbrookrobotics" % "potassium" % "0.1-SNAPSHOT"

libraryDependencies += "com.lynbrookrobotics" % "funky-dashboard_2.11" % "0.1.0-SNAPSHOT"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.19" % Test

libraryDependencies += "junit" % "junit" % "4.12"

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

import com.etsy.sbt.checkstyle._
Checkstyle.xsltTransformations := {
  Some(Set(XSLTSettings(baseDirectory(_ / "checkstyle-noframes.xsl").value, target(_ / "checkstyle-report.html").value)))
}
