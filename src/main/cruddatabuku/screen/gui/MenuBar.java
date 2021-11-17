package main.cruddatabuku.screen.gui;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class MenuBar extends JMenuBar {
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");

    // for fileMenu
    JMenuItem createItem = new JMenuItem("Create");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem closeItem = new JMenuItem("Close");
    JMenuItem exitItem = new JMenuItem("Exit");

    // for editMenu
    JMenuItem selectAll = new JMenuItem("Select All");
    JMenuItem refresTable = new JMenuItem("Refresh");

    public MenuBar() {
        this.setBackground(Color.WHITE);
        this.setOpaque(true);

        fileMenu.add(loadItem);
        fileMenu.add(createItem);
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);

        editMenu.add(selectAll);
        editMenu.add(refresTable);

        this.add(fileMenu);
        this.add(editMenu);
    }
}
