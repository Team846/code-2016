javaOptions in Test += s"-Djava.library.path=${baseDirectory.value / "lib"}"

fork in Test := true