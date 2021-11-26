/*
  Digunakan untuk merubah tampilan component gui menjadi bawaan sistem
 */

package main.cruddatabuku.screen.gui.component;

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
