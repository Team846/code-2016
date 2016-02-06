resolvers += "Funky-Repo" at "http://team846.github.io/repo"

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")

addSbtPlugin("com.lynbrookrobotics" % "sbt-frc" % "0.2.0.1-SNAPSHOT")

addSbtPlugin("com.etsy" % "sbt-checkstyle-plugin" % "2.0.0")

dependencyOverrides += "com.puppycrawl.tools" % "checkstyle" % "6.15"
