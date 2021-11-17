package main.cruddatabuku.screen.gui.component;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;

public class MenuBar extends JMenuBar {
    public JMenu fileMenu = new JMenu("File");
    public JMenu editMenu = new JMenu("Edit");

    // for fileMenu
    public JMenuItem createItem = new JMenuItem("Create");
    public JMenuItem loadItem = new JMenuItem("Load");
    public JMenuItem closeItem = new JMenuItem("Close");
    public JMenuItem exitItem = new JMenuItem("Exit");

    // for editMenu
    public JMenuItem selectAll = new JMenuItem("Select All");
    public JMenuItem refresTable = new JMenuItem("Refresh");

    public MenuBar() {
        this.setUI(new BasicMenuBarUI());
        this.setBackground(Color.WHITE);
        this.setOpaque(true);

        this.add(fileMenu);
        this.add(editMenu);

        fileMenu.add(loadItem);
        fileMenu.add(createItem);
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);

        editMenu.add(selectAll);
        editMenu.add(refresTable);

        for (int i = 0; i < this.getMenuCount(); i++) {
            this.getMenu(i).setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        for (int i = 0; i < fileMenu.getItemCount(); i++) {
            fileMenu.getItem(i).setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        for (int i = 0; i < editMenu.getItemCount(); i++) {
            editMenu.getItem(i).setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        this.add(fileMenu);
        this.add(editMenu);
    }
}
