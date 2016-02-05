package com.lynbrookrobotics.sixteen.tasks.jsauto;

import javax.swing.*;


public class FileSelector extends JPanel{
    private String filePath;

    public Object openFileBrowser()
    {
        JFileChooser chooser = new JFileChooser();
        int choice = chooser.showOpenDialog(FileSelector.this);
        if (choice != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return chooser.getSelectedFile();
    }

    public Object runJS(Object openFileBrowser) {
        if (openFileBrowser != null) {
            filePath = openFileBrowser.toString();
            int distanceToPeriod = 0;
            for (int i = filePath.length() - 1; filePath.charAt(i) != '.'; i--, distanceToPeriod++) {}
            String fileType = filePath.substring(filePath.length() - distanceToPeriod, filePath.length());
            if (fileType.equalsIgnoreCase("js"))
                return openFileBrowser;
        }
        return runJS(openFileBrowser());
    }


}
