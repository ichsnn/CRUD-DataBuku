package main.cruddatabuku.screen.gui.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class Button extends JButton {
    public Button() {
        this.setFocusable(false);
        this.setUI(new BasicButtonUI());
    }

    public Button(String text) {
        this.setFocusable(false);
        this.setUI(new BasicButtonUI());
        this.setText(text);
    }
}
