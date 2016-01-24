package com.lynbrookrobotics.sixteen.tasks.jsauto;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadJavascriptFile {
    /**
     * Returns a FiniteTask that is constructed by executing a
     * specific JavaScript program. A {@link java.io.Reader}
     * that is the source of the JavaScript program is specified.
     *
     * @param script    a Reader associated with the source of the JavaScript program
     * @return          the FiniteTask constructed by the JavaScript program
     */
    public static FiniteTask loadReader(BufferedReader script) { // TODO: load task list and expose in JS scope
        try {
            Context ctx = Context.enter();
            Scriptable scope = ctx.initStandardObjects();
            Object task = ctx.evaluateReader(scope, script, "", 1, null); // TODO: find purpose of file name (arg 3)
            Context.exit();
            return (FiniteTask)task;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Loads a FiniteTask from a JavaScript source file specified
     * using the string path of the file.
     *
     * @param path  the string form of the
     * @return      the FiniteTask constructed by the JavaScript program
     */
    public static FiniteTask loadStringPath(String path) {
        return loadPath(Paths.get(path));
    }

    /**
     * Loads a FiniteTask from a JavaScript source file specified
     * using a {@link Path} corresponding to the source.
     *
     * @param path  the {@link Path} representing the location of the JavaScript source file
     * @return      the FiniteTask constructed by the JavaScript program
     */
    public static FiniteTask loadPath(Path path) {
        try {
            return loadReader(Files.newBufferedReader(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads a FiniteTask from a JavaScript source file specified
     * directly by the argument.
     *
     * @param script    the source of the JavaScript program
     * @return          the FiniteTask constructed by the JavaScript program
     */
    public static FiniteTask loadString(String script) {
        return loadReader(new BufferedReader(new StringReader(script)));
    }

    /**
     * Loads a FiniteTask from a JavaScript source file specified
     * by the name of the script in a designated scripting folder
     * on the system.
     *
     * @param scriptName    the name of the JavaScript script to be executed
     * @return              the FiniteTask constructed by the JavaScript program
     */
    public static FiniteTask loadScript(String scriptName) {
        return loadStringPath(scriptName); // TODO: prefix scripts location to scriptName
    }
}
