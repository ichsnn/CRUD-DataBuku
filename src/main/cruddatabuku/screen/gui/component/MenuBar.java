package main.cruddatabuku.screen.gui.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;

public class MenuBar extends JMenuBar {
    public JMenu fileMenu = new JMenu("File");
    public JMenu editMenu = new JMenu("Edit");
    public JMenu helpMenu = new JMenu("Bantuan");

    // for fileMenu
    public JMenuItem createItem = new JMenuItem("Buat");
    public JMenuItem loadItem = new JMenuItem("Buka");
    public JMenuItem closeItem = new JMenuItem("Tutup");
    public JMenuItem exitItem = new JMenuItem("Keluar");

    // for editMenu
    public JMenuItem selectAll = new JMenuItem("Seleksi Semua");
    public JMenuItem refresTable = new JMenuItem("Refresh");

    // for helpHemu
    public JMenuItem helpGuide = new JMenuItem("Petunjuk");
    public JMenuItem jenisBuku = new JMenuItem("Jenis Buku");

    public MenuBar() {
        this.setUI(new BasicMenuBarUI());
        this.setBackground(Color.WHITE);
        this.setOpaque(true);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);

        fileMenu.add(loadItem);
        fileMenu.add(createItem);
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);

        editMenu.add(selectAll);
        editMenu.add(refresTable);

        helpMenu.add(helpGuide);
        helpMenu.add(jenisBuku);

        for (int i = 0; i < this.getMenuCount(); i++) {
            this.getMenu(i).setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        for (int i = 0; i < fileMenu.getItemCount(); i++) {
            fileMenu.getItem(i).setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        for (int i = 0; i < editMenu.getItemCount(); i++) {
            editMenu.getItem(i).setFont(new Font("Dialog", Font.PLAIN, 12));
        }

        for (int i = 0; i < helpMenu.getItemCount(); i++) {
            helpMenu.getItem(i).setFont(new Font("Dialog", Font.PLAIN, 12));
        }

    }
}
