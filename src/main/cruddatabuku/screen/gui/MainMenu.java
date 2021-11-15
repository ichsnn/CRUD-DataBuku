package main.cruddatabuku.screen.gui;

import main.cruddatabuku.buku.DataBuku;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static main.cruddatabuku.util.Berkas.memuatDataBuku;

public class MainMenu extends JFrame {
    JButton btnBukaFile = new JButton("Buka File");
    JButton btnCariBuku = new JButton("Cari Buku");
    JButton btnTambahBuku = new JButton("Tambah Buku");
    JButton btnUpdateBuku = new JButton("Update Buku");
    JButton btnHapusBuku = new JButton("Hapus Buku");

    JPanel topPanel;
    JPanel buttonPanel;
    JPanel titlePanel;
    JPanel bottomPanel;
    JPanel containerPanel;

    JTable table;
    JScrollPane tablePane;

    List<DataBuku> listDataBuku = new ArrayList<>();

    public MainMenu() {
        btnCariBuku.setFocusable(false);
        btnTambahBuku.setFocusable(false);
        btnUpdateBuku.setFocusable(false);
        btnHapusBuku.setFocusable(false);

        File file = new File("data\\dataBuku.txt");
        boolean sukses = memuatDataBuku(file, listDataBuku);

        int row = listDataBuku.size();
        int col = 7;
        String[] tableHeader = {"ID", "Kode Buku", "Judul Buku", "Jenis Buku", "Penulis", "Penerbit", "Tahun Terbit"};
        String[][] tableData = new String[row][col];

        int indexTable = 0;
        for (DataBuku buku : listDataBuku) {
            tableData[indexTable][0] = String.valueOf(buku.getId());
            tableData[indexTable][1] = buku.getKodeBuku();
            tableData[indexTable][2] = buku.getJudulBuku();
            tableData[indexTable][3] = buku.getJenisBuku();
            tableData[indexTable][4] = buku.getPenulis();
            tableData[indexTable][5] = buku.getPenerbit();
            tableData[indexTable][6] = buku.getTahunTerbit();
            indexTable++;
        }
        table = new JTable(tableData, tableHeader) {
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
        };
        table.setPreferredScrollableViewportSize(new Dimension(400, 400));
        table.setFillsViewportHeight(true);
        table.setAutoscrolls(true);
        table.setAutoCreateRowSorter(true);
        table.setShowGrid(false);

        table.setBackground(Color.white);
        table.setOpaque(true);
        table.setFont(new Font("Calibri", Font.PLAIN, 16));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 18));
        table.getTableHeader().setBackground(Color.decode("#1A81CF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(Color.decode("#cce9ff"));

        tablePane = new JScrollPane(table);
        tablePane.setBackground(Color.white);
        tablePane.setOpaque(true);
        tablePane.getViewport().setBackground(Color.white);

        topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setOpaque(true);

        titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("DAFTAR DATA BUKU");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setOpaque(true);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(true);
        buttonPanel.add(btnCariBuku);
        buttonPanel.add(btnTambahBuku);
        buttonPanel.add(btnUpdateBuku);
        buttonPanel.add(btnHapusBuku);

        topPanel.setLayout(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        System.out.println(bottomPanel.getBorder());
        bottomPanel.add(tablePane, BorderLayout.CENTER);

        containerPanel = new JPanel();
        containerPanel.setBorder(new EmptyBorder(10, 25, 10, 25));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setOpaque(true);
        containerPanel.setLayout(new BorderLayout(0, 25));

        containerPanel.add(topPanel, BorderLayout.NORTH);
        containerPanel.add(bottomPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(700, 500));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.getContentPane().setBackground(Color.WHITE);
        this.add(containerPanel, BorderLayout.CENTER);
        this.add(new Label("Dibuat oleh : Ichsan Nulmuhlis", Label.CENTER), BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
