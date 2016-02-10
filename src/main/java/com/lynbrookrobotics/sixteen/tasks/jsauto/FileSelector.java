package com.lynbrookrobotics.sixteen.tasks.jsauto;

import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

class FileSelector extends JPanel {
  private JFileChooser chooser = new JFileChooser();

  Optional<File> loadFile() {
    int choice = chooser.showOpenDialog(this);

    if (choice != JFileChooser.APPROVE_OPTION
        || !chooser.getSelectedFile().getAbsolutePath().endsWith(".js")) {
      return Optional.empty();
    } else {
      return Optional.of(chooser.getSelectedFile());
    }
  }
}
