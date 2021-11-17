package main.cruddatabuku.screen.gui;

import javax.swing.*;

public interface UIManagerMethod {
    static void setUI(String systemLookAndFeelClassName) {
        try {
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }
}
