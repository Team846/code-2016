package com.lynbrookrobotics.sixteen.tasks.jsauto;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

import com.lynbrookrobotics.sixteen.config.RobotHardware;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.config.ShooterHardware;


/**
 * Creates an Interface and Validates the JS
 */
public class JSValidator {
    public JTextArea textArea;
    public JButton jbutton;
    private Object JavaScriptObject;
    private JTextPane fileChoosenTxt;
    /**
     *   This creates the objects, and loads the JS.
     *   Also get's the Error and set's the error
     */
    public void createObjects() {
        ShooterHardware shooterHardware=new ShooterHardware();
        RobotHardware robotHardware = new RobotHardware(null,shooterHardware);
        Drivetrain  drivetrain = new Drivetrain(robotHardware, null);

        LoadJavascriptFile loadJavascriptFile = new LoadJavascriptFile();
        try {
            File file = new File(JavaScriptObject.toString());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
           String x= loadJavascriptFile.loadReader(bufferedReader,robotHardware,drivetrain).toString();
            addTextToConsole("Success \n" + x + "\n");
        }
        catch (Exception e) {
            addTextToConsole("INVALID: Error" + "\n");
            addTextToConsole(e.toString() + "\n");
        }
        for(int i=0;i<46;i++)
            addTextToConsole("-");
        addTextToConsole("\n");
    }
    public static void main(String[]args)
    {
                  JSValidator jsValidator=new JSValidator();
        jsValidator.initialize();
    }


    /**
     * This creates a frame, titleTextBox, a Button, and a Text Area were the console will input.
     * The Button also calls the ButtonPressed method when it is pressed.
     */
    public void initialize() {
        JFrame frame = new JFrame("Validator");
        frame.getContentPane().setBackground(Color.CYAN);
        frame.getContentPane().setLayout(null);

        JTextPane titleText = new JTextPane();
        titleText.setFont(new Font("Tahoma", Font.PLAIN, 17));
        titleText.setBackground(new Color(255, 255, 204));
        titleText.setText("Please Click a Button To Check the Validation of the JS Code");
        titleText.setBounds(73, 22, 278, 48);
        frame.getContentPane().add(titleText);

        jbutton = new JButton("Check Validation");
        jbutton.setBounds(129, 106, 132, 79);
        frame.getContentPane().add(jbutton);
        jbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                buttonPressed();
            }});

        textArea = new JTextArea();
        textArea.setBounds(374, 65, 243, 196);
        frame.getContentPane().add(textArea);

        JTextPane Console = new JTextPane();
        Console.setText("Console(In Case of Errors)");
        Console.setBounds(374, 38, 160, 20);


        fileChoosenTxt = new JTextPane();
        fileChoosenTxt.setText("NO File Choosen");
        fileChoosenTxt.setBounds(263, 162, 101, 20);

        frame.getContentPane().add(Console);
        frame.setBounds(100, 100, 662, 310);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Creates the Objects
     */
    private void buttonPressed() {
        FileSelector fileSelector = new FileSelector();
        JavaScriptObject  = fileSelector.runJS(fileSelector.openFileBrowser());
        fileChoosenTxt.setText("Valid File");
         createObjects();
    }

    /**
     *  Sets the error text area
     * @param error
     */
    public void addTextToConsole(String error)
    {
        for(int i=0, positiononTextArea=0;i<error.length();i++,positiononTextArea++) {
            if(positiononTextArea==42) {
                addTextToConsole("\n"); positiononTextArea=0;}
            textArea.append(error.charAt(i)+"");
        }
    }


}
