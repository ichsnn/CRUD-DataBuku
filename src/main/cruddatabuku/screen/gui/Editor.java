package main.cruddatabuku.screen.gui;

import main.cruddatabuku.buku.DataBuku;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static main.cruddatabuku.util.Berkas.deleteData;
import static main.cruddatabuku.util.Berkas.memuatDataBuku;
import static main.cruddatabuku.util.Kamus.appIcon;

public class Editor extends JFrame implements ActionListener, DocumentListener, WindowListener {
    private String fileName;

    JButton btnTambahBuku = new JButton("Tambah Buku");
    JButton btnUpdateBuku = new JButton("Update Buku");
    JButton btnHapusBuku = new JButton("Hapus Buku");

    JPanel topPanel;
    JPanel titlePanel;
    JPanel bottomPanel;
    JPanel containerPanel;

    TableRowSorter<?> tableRowSorter;
    DefaultTableModel defaultTableModel;
    JTable table = new JTable() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JScrollPane tablePane;

    List<DataBuku> listDataBuku = new ArrayList<>();

    Object[] tableHeader = {"ID", "Kode Buku", "Judul Buku", "Jenis Buku", "Penulis", "Penerbit", "Tahun Terbit"};

    JTextField pencarian;

    TambahBuku tambahBukuDialog;

    public Editor(File file) {
        this.fileName = file.getPath();
        this.setIconImage(appIcon.getImage());
        this.setTitle("Aplikasi Pengelola Daftar Buku (" + fileName + ")");
        // Button
        btnTambahBuku.setFocusable(false);
        btnTambahBuku.addActionListener(this);
        btnUpdateBuku.setFocusable(false);
        btnUpdateBuku.addActionListener(this);
        btnHapusBuku.setFocusable(false);
        btnHapusBuku.addActionListener(this);

        // Buat tabel
        defaultTableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                    case 6:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        defaultTableModel.setColumnIdentifiers(tableHeader);
        table.setModel(defaultTableModel);

        // Buka data dari berkas dan tambahkan kedalam tabel
        loadData();

        // Digunakan untuk mengubah sebuah text align cell value
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // text align center

        // rubah text align semua data pada setiap kolom
        for (int i = 0; i < tableHeader.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // set maximum width untuk kolom ID dan kolom Tahun Terbit
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        table.getColumnModel().getColumn(6).setMaxWidth(110);
        // set table sorter
        tableRowSorter = new TableRowSorter<>(defaultTableModel);
        table.setRowSorter(tableRowSorter);
        // styling table
        table.setFillsViewportHeight(true);
        table.setAutoscrolls(true);
        table.setShowGrid(false);
        table.setBackground(Color.white);
        table.setOpaque(true);
        table.setFont(new Font("Calibri", Font.PLAIN, 16));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 18));
        table.getTableHeader().setBackground(Color.decode("#1A81CF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(Color.decode("#cce9ff"));

        // styling tablePane
        tablePane = new JScrollPane(table);
        tablePane.setBackground(Color.white);
        tablePane.setOpaque(true);
        tablePane.getViewport().setBackground(Color.white);

        // styling topPanel
        topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setOpaque(true);

        titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("DAFTAR DATA BUKU");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setOpaque(true);

        topPanel.setLayout(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);

        pencarian = new JTextField();
        pencarian.setFont(new Font("Calibri", Font.PLAIN, 16));
        pencarian.setPreferredSize(new Dimension(200, 30));
        pencarian.setMargin(new Insets(0, 5, 0, 5));
        pencarian.getDocument().addDocumentListener(this);

        JLabel labelPencarian = new JLabel("Pencarian : ");
        labelPencarian.setFont(new Font("Calibri", Font.PLAIN, 16));
        labelPencarian.setLabelFor(pencarian);

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(1, 3, 5, 0));
        panelButton.add(btnTambahBuku);
        panelButton.add(btnUpdateBuku);
        panelButton.add(btnHapusBuku);
        panelButton.setBackground(Color.WHITE);

        JPanel containerPanelTool = new JPanel();
        containerPanelTool.setLayout(null);
        containerPanelTool.setBackground(Color.WHITE);
        containerPanelTool.setOpaque(true);
        containerPanelTool.setLayout(new BorderLayout(5, 0));
        //containerPanelTool.add(panelPencarian, BorderLayout.CENTER);
        containerPanelTool.add(labelPencarian, BorderLayout.WEST);
        containerPanelTool.add(pencarian, BorderLayout.CENTER);
        containerPanelTool.add(panelButton, BorderLayout.EAST);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(0, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setOpaque(true);
        bottomPanel.add(tablePane, BorderLayout.CENTER);
        bottomPanel.add(containerPanelTool, BorderLayout.NORTH);

        containerPanel = new JPanel();
        containerPanel.setBorder(new EmptyBorder(10, 25, 10, 25));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setOpaque(true);
        containerPanel.setLayout(new BorderLayout(0, 25));

        //containerPanel.add(topPanel, BorderLayout.NORTH);
        containerPanel.add(bottomPanel, BorderLayout.CENTER);

        JLabel createdBy = new JLabel("Dibuat Oleh : Ichsan Nulmuhlis", JLabel.CENTER);
        createdBy.setBorder(new EmptyBorder(0, 0, 5, 0));
        createdBy.setFont(new Font("Calibri", Font.PLAIN, createdBy.getFont().getSize()));
        createdBy.setBackground(Color.WHITE);
        createdBy.setOpaque(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(800, 500));
        this.setBackground(Color.WHITE);
        this.getContentPane().setBackground(Color.WHITE);
        this.add(containerPanel, BorderLayout.CENTER);
        this.add(createdBy, BorderLayout.SOUTH);
        this.addWindowListener(this);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTambahBuku) {
            tambahBukuDialog = new TambahBuku(listDataBuku, fileName);
            tambahBukuDialog.setLocationRelativeTo(this);
        }

        if (e.getSource() == btnHapusBuku) {
            if (table.getSelectedRows().length != 0) {
                int respon = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menghapus data ini ?", "Hapus Data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respon == 0) {
                    String[] index = new String[table.getSelectedRows().length];
                    int[] rowIndexToRemove = new int[table.getSelectedRows().length];
                    int i = 0;
                    for (int data : table.getSelectedRows()) {
                        index[i] = String.valueOf(table.getValueAt(data, 0));
                        rowIndexToRemove[i] = data;
                        i++;
                    }

                    for (int row : rowIndexToRemove) {
                        System.out.print(row + " ");
                    }

                    System.out.println();
                    System.out.println();

                    for (int k = defaultTableModel.getRowCount() - 1; k >= 0; k--) {
                        /* Salah karena hanya mengapus baris berdasarkan seleksi yang terurut
                        for (int l = rowIndexToRemove.length - 1; l >= 0; l--) {
                            if (k == l) {
                                defaultTableModel.removeRow(k);
                            }
                        }
                         */
                        for (int row : rowIndexToRemove) {
                            if (k == row) {
                                defaultTableModel.removeRow(k);
                            }
                        }
                    }

                    for (String str : index) {
                        deleteData(fileName, listDataBuku, Integer.parseInt(str));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleksi / Pilih terlebih dahulu data yang ingin dihapus", "Hapus Data", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument().equals(pencarian.getDocument())) {
            search(pencarian.getText());
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument().equals(pencarian.getDocument())) {
            search(pencarian.getText());
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void loadData() {
        File file = new File(fileName);
        boolean sukses = memuatDataBuku(file, listDataBuku);
        if (sukses) {
            for (DataBuku buku : listDataBuku) {
                defaultTableModel.addRow(buku.geetArrayFormat());
            }
        }
    }

    public void search(String str) {
        if (str.isEmpty()) {
            tableRowSorter.setRowFilter(null);
        } else {
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

        if (e.getOppositeWindow() != null) {
            try {
                if (e.getOppositeWindow().getClass().getName().contains("TambahBuku")) {
                    if (tambahBukuDialog.isSubmit()) {
                        defaultTableModel.addRow(tambahBukuDialog.getBukuBaru());
                    }
                }
            } catch (NullPointerException exception) {
                System.out.println("Null Pointer Exception");
            }
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
