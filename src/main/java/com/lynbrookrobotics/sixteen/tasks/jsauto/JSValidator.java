package com.lynbrookrobotics.sixteen.tasks.jsauto;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;


/**
 * Creates an Interface and Validates the JS
 */
public class JSValidator {
    public JTextArea textArea;
    public JButton jbutton;
    private RobotHardware robotHardware  =null;
    private DrivetrainHardware drivetrainHardware=null;
    private BufferedReader bufferedReader= null; //TODO Add a Script for the LOADED JS
    private Drivetrain drivetrain=null;

    /**
     *   This creates the objects, and loads the JS.//TODO add the JS to it
     *   Also get's the Error and set's the error
     */
    public void createObjects() {
        LoadJavascriptFile loadJavascriptFile=new LoadJavascriptFile();
        loadJavascriptFile.loadReader(bufferedReader,robotHardware,drivetrain);
        //TODO get the ERROR and SET the ERROR to the Console
        String Error="error";
        setConsoleText(Error);

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
            }
        });

        textArea = new JTextArea();
        textArea.setBounds(374, 65, 243, 196);
        frame.getContentPane().add(textArea);

        JTextPane Console = new JTextPane();
        Console.setText("Console(In Case of Errors)");
        Console.setBounds(374, 38, 160, 20);

        frame.getContentPane().add(Console);
        frame.setBounds(100, 100, 662, 310);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Creates the Objects
     */
    private void buttonPressed() {
        createObjects();
    }

    /**
     *  Sets the error text area
     * @param error
     */
    public void setConsoleText(String error)
    {
        textArea.setText(error);
    }


}
