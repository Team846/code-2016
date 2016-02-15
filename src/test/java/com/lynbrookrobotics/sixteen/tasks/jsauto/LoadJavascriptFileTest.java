package com.lynbrookrobotics.sixteen.tasks.jsauto;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;

/**
 * Created by Aubhro Sengupta on 2/13/16. This class is meant to test all the methods in LoadJavaScriptFile class.
 */
public class LoadJavascriptFileTest {

  @Test
  public void testLoadReader() throws Exception {

    LoadJavascriptFile loader = new LoadJavascriptFile();
    InputStream stream;
    InputStreamReader reader;
    BufferedReader readerFile = null;

    File jsFile = new File("correct.js");

    try{
      stream = new FileInputStream(jsFile.getAbsolutePath());
      reader = new InputStreamReader(stream);
      readerFile = new BufferedReader(reader);

    } catch(Exception err){
      System.out.println("File not found");
      err.printStackTrace();
    }

    String result = loader.loadReader(readerFile, null , null).toString().substring(0,57);
    assertEquals("Result:", "com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle", result );


  }

  @Test
  public void testLoadStringPath() throws Exception {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    File jsFile = new File("correct.js");

    String result = loader.loadStringPath(jsFile.getAbsolutePath(), null , null).toString().substring(0,57);
    assertEquals("Result:", "com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle", result );

  }

  @Test
  public void testLoadPath() throws Exception {
    LoadJavascriptFile loader = new LoadJavascriptFile();
    Path jsFile = Paths.get("correct.js");

    String result = loader.loadPath(jsFile, null , null).toString().substring(0,57);
    assertEquals("Result:", "com.lynbrookrobotics.sixteen.tasks.drivetrain.TurnByAngle", result );

  }

  @Test
  public void testLoadString() throws Exception {
    //TODO: Test LoadString()
  }

  @Test
  public void testLoadScript() throws Exception {
    //TODO: Test LoadScript()
  }
}