package main.cruddatabuku.screen.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    JMenu fileMenu = new JMenu("File");

    JMenuItem createItem = new JMenuItem("Create");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem closeItem = new JMenuItem("Close");
    JMenuItem exitItem = new JMenuItem("Exit");

    public MenuBar() {
        this.setBackground(Color.WHITE);
        this.setOpaque(true);

        fileMenu.add(loadItem);
        fileMenu.add(createItem);
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);

        this.add(fileMenu);
    }
}
