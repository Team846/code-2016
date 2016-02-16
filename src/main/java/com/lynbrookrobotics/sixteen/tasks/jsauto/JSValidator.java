package com.lynbrookrobotics.sixteen.tasks.jsauto;

import com.lynbrookrobotics.potassium.tasks.FiniteTask;
import com.lynbrookrobotics.sixteen.components.drivetrain.Drivetrain;
import com.lynbrookrobotics.sixteen.components.shooter.spinners.ShooterSpinners;
import com.lynbrookrobotics.sixteen.config.DrivetrainHardware;
import com.lynbrookrobotics.sixteen.config.IntakeHardware;
import com.lynbrookrobotics.sixteen.config.RobotHardware;
import com.lynbrookrobotics.sixteen.config.ShooterSpinnersHardware;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * A swing-based interface for validating JavaScript routines.
 */
public class JSValidator extends JFrame {
  private DrivetrainHardware drivetrainHardware = new DrivetrainHardware(
      null, null, null, null, null, null, null, null, null, null
  );

  private ShooterSpinnersHardware shooterSpinnersHardware = new ShooterSpinnersHardware(null, null, null, null, null, null);
  private IntakeHardware intakeHardware = new IntakeHardware(null, null);
  private RobotHardware robotHardware = new RobotHardware(
      drivetrainHardware,
      shooterSpinnersHardware,
      intakeHardware
  );

  private Drivetrain drivetrain = new Drivetrain(robotHardware, null);
  private ShooterSpinners spinners = new ShooterSpinners(robotHardware, null);

  private FileSelector fileSelector = new FileSelector();
  private JTextArea validationOutput;

  private void evaluateJSRoutine(File routineFile) {
    try {
      FileReader fileReader = new FileReader(routineFile);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      FiniteTask task = LoadJavascriptFile.loadReader(bufferedReader, robotHardware, drivetrain, spinners);

      validationOutput.setText("Success \n" + task + "\n");
    } catch (Exception loadException) {
      validationOutput.setText("Loading Routine threw exception: " + loadException.toString());
    }
  }

  /**
   * Launches the validation application.
   */
  public static void main(String[] args) {
    new JSValidator();
  }

  private JSValidator() {
    super("Routine Validator");
    initialize();
  }

  private void initialize() {
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

    JButton runValidateButton = new JButton("Validate Routine");
    getContentPane().add(runValidateButton);
    runValidateButton.addActionListener(e -> buttonPressed());

    validationOutput = new JTextArea();
    validationOutput.setEditable(false);
    validationOutput.setLineWrap(true);
    getContentPane().add(validationOutput);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
  }

  private void buttonPressed() {
    fileSelector.loadFile().ifPresent(this::evaluateJSRoutine);
  }
}
