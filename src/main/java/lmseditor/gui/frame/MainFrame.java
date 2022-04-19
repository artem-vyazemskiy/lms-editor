package lmseditor.gui.frame;

import lmseditor.gui.panel.MainPanel;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;

    public MainFrame() {
        mainPanel = new MainPanel();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.add(mainPanel);

        this.setVisible(true);
    }

}