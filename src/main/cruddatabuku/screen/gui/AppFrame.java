package main.cruddatabuku.screen.gui;

import main.cruddatabuku.buku.DataBuku;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static main.cruddatabuku.util.Berkas.*;

public class AppFrame extends JFrame {
    JPanel panel = new JPanel();
    JTable table;
    List<DataBuku> listDataBuku = new ArrayList<>();
    public AppFrame() {
        memuatDataBuku(new File("dataBuku.txt"), listDataBuku);
        String[] tableHeader = {"ID", "Kode Buku", "Judul Buku", "Jenis Buku", "Penulis", "Penerbit", "Tahun Terbit"};

        int row = listDataBuku.size();
        int col = tableHeader.length;

        String[][] tableContent = new String[row][col];

        int i = 0;
        for(DataBuku buku : listDataBuku) {
            tableContent[i][0] = String.valueOf(buku.getId());
            tableContent[i][1] = buku.getKodeBuku();
            tableContent[i][2] = buku.getJudulBuku();
            tableContent[i][3] = buku.getJenisBuku();
            tableContent[i][4] = buku.getPenulis();
            tableContent[i][5] = buku.getPenerbit();
            tableContent[i][6] = buku.getTahunTerbit();
            i++;
        }

        table = new JTable(tableContent, tableHeader);
        table.setVisible(true);
        panel.add(table);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout());

        this.add(panel, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
