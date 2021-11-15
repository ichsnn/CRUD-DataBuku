package main.cruddatabuku.screen.gui;

import main.cruddatabuku.Main;
import main.cruddatabuku.buku.DataBuku;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static main.cruddatabuku.util.Berkas.memuatDataBuku;

public class GUIApp extends JFrame {

    public GUIApp() {
        new MainMenu();
    }
}
