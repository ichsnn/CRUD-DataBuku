package main.cruddatabuku.screen.gui;

/*
  Import package yang digunakan.
  Untuk tampilan gui menggunakan library javax.swing
  Untuk event listener mengambil dari library java.awt
 */

import main.cruddatabuku.buku.Buku;
import main.cruddatabuku.screen.gui.component.Button;
import main.cruddatabuku.screen.gui.component.MenuBar;
import main.cruddatabuku.screen.gui.helpmenu.HelpGuide;
import main.cruddatabuku.screen.gui.helpmenu.HelpJenisBuku;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.cruddatabuku.screen.gui.component.UIManagerMethod.setUI;
import static main.cruddatabuku.util.Berkas.deleteData;
import static main.cruddatabuku.util.Berkas.memuatDataBuku;
import static main.cruddatabuku.util.Library.appIcon;

/**
 * Editor, class digunakan untuk memanage tampilan yang menyangkut editor utama
 * memiliki fitur utama yaitu melihat data berbentuk tabel, menambah data, mengupdate data, dan menghapus data
 */
public class Editor extends JFrame implements ActionListener, DocumentListener, WindowListener {
    private final String fileName;

    Button btnTambahBuku = new Button("Tambah Buku");
    Button btnUpdateBuku = new Button("Update Buku");
    Button btnHapusBuku = new Button("Hapus Buku");

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

    List<Buku> listBuku = new ArrayList<>();

    Object[] tableHeader = {"ID", "Kode Buku", "Judul Buku", "Jenis Buku", "Penulis", "Penerbit", "Tahun Terbit"};

    JTextField pencarian;

    TambahBuku tambahBukuDialog;
    UpdateBuku updateBukuDialog;

    MenuBar menuBar = new MenuBar();

    boolean sukses;

    public Editor(File file) {
        this.fileName = file.getPath();
        this.setIconImage(appIcon.getImage());

        sukses = memuatDataBuku(file, listBuku);

        if (!sukses) {
            this.setTitle("Aplikasi Pengelola Daftar Buku - ERROR!");
        } else {
            this.setTitle("Aplikasi Pengelola Daftar Buku (" + fileName + ")");
        }

        // Button
        btnTambahBuku.addActionListener(this);
        btnUpdateBuku.addActionListener(this);
        btnHapusBuku.addActionListener(this);

        if (!sukses) {
            btnTambahBuku.setEnabled(false);
            btnUpdateBuku.setEnabled(false);
            btnHapusBuku.setEnabled(false);
        }

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
        table.setFont(new Font("Calibri", Font.PLAIN, 14));
        table.setRowHeight(14 + 16);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));

        /* Styling table header and table selection
        table.getTableHeader().setBackground(Color.decode("#1A81CF"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(Color.decode("#cce9ff"));
         */

        // styling tablePane
        tablePane = new JScrollPane(table);
        tablePane.setBackground(Color.white);
        tablePane.setOpaque(true);
        tablePane.getViewport().setBackground(Color.white);
        tablePane.setUI(new BasicScrollPaneUI());

        pencarian = new JTextField();
        pencarian.setFont(new Font("Dialog", Font.PLAIN, 14));
        pencarian.setPreferredSize(new Dimension(200, 30));
        pencarian.setMargin(new Insets(2, 5, 2, 5));
        pencarian.getDocument().addDocumentListener(this);

        JLabel labelPencarian = new JLabel("Cari : ");
        labelPencarian.setFont(new Font("Dialog", Font.PLAIN, 14));
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

        // file menu
        menuBar.loadItem.addActionListener(this);
        menuBar.createItem.addActionListener(this);
        menuBar.closeItem.addActionListener(this);
        menuBar.exitItem.addActionListener(this);
        // edit menu
        menuBar.selectAll.addActionListener(this);
        menuBar.deselect.addActionListener(this);
        menuBar.refresTable.addActionListener(this);
        // help menu
        menuBar.helpGuide.addActionListener(this);
        menuBar.jenisBuku.addActionListener(this);

        this.setJMenuBar(menuBar);
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

    /**
     * Digunakan untuk menghapus buku.
     * Caranya dengan menyeleksi 1 baris atau lebih lalu tekan tombol hapus buku
     */
    private void hapusBuku() {
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
                    deleteData(fileName, listBuku, Integer.parseInt(str));
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleksi / Pilih terlebih dahulu data yang ingin dihapus", "Hapus Data", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Digunakan untuk melakukan pencarian data dengan memasukkan data yang dicari di textfield cari
     * Pencarian dilakukan secara menyeluruh, jadi jika memasukkan data yang ingin dicari harus spesifik
     */
    public void search(String str) {
        if (str.isEmpty()) {
            tableRowSorter.setRowFilter(null);
        } else {
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // buka dialog tambah buku
        if (e.getSource() == btnTambahBuku) {
            tambahBukuDialog = new TambahBuku(listBuku, fileName);
        }

        // lakukan hapus buku
        if (e.getSource() == btnHapusBuku) {
            hapusBuku();
        }

        // buka dialog update buku
        if (e.getSource() == btnUpdateBuku) {
            if (table.getSelectedRows().length == 1) {
                int rS = table.getSelectedRow(); // row selected
                Object[] objectsSelected = new Object[7];
                for (int i = 0; i < objectsSelected.length; i++) {
                    objectsSelected[i] = defaultTableModel.getValueAt(rS, i);
                }

                updateBukuDialog = new UpdateBuku(objectsSelected, listBuku, fileName, rS);

            } else {
                JOptionPane.showMessageDialog(this, "Seleksi salah satu data pada tabel terlebih dahulu", "Update Data", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // event menu file > buka file
        if (e.getSource() == menuBar.loadItem) {
            setUI(UIManager.getSystemLookAndFeelClassName());
            JFileChooser fileChooser = new JFileChooser();
            if (new File("./data").exists()) {
                fileChooser.setCurrentDirectory(new File("./data"));
            } else {
                fileChooser.setCurrentDirectory(new File("."));
            }
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        return f.getName().toLowerCase().endsWith(".txt");
                    }
                }

                @Override
                public String getDescription() {
                    return "Flat File (*.txt)";
                }
            });
            int respon = fileChooser.showOpenDialog(this);
            if (respon == JFileChooser.APPROVE_OPTION) {
                setUI(UIManager.getCrossPlatformLookAndFeelClassName());
                new Editor(fileChooser.getSelectedFile());
                this.dispose();
            }
        }

        // event menu file > buat file
        if (e.getSource() == menuBar.createItem) {
            File newFile;
            setUI(UIManager.getSystemLookAndFeelClassName());
            JFileChooser createFile = new JFileChooser();
            if (new File("./data").exists()) {
                createFile.setCurrentDirectory(new File("./data"));
            } else {
                createFile.setCurrentDirectory(new File("."));
            }
            createFile.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        return f.getName().toLowerCase().endsWith(".txt");
                    }
                }

                @Override
                public String getDescription() {
                    return "Flat File (*.txt)";
                }

            });
            createFile.setDialogTitle("Crate New File");
            boolean notReapet = false;
            int respon;
            do {
                respon = createFile.showSaveDialog(this);
                if (respon == JFileChooser.APPROVE_OPTION) {
                    if (!createFile.getSelectedFile().getPath().contains(".txt")) {
                        newFile = new File(createFile.getSelectedFile().getPath() + ".txt");
                    } else {
                        newFile = new File(createFile.getSelectedFile().getPath());
                    }
                    if (!newFile.exists()) {
                        try {
                            boolean success = newFile.createNewFile();
                            if (success) {
                                setUI(UIManager.getCrossPlatformLookAndFeelClassName());
                                new Editor(newFile);
                                this.dispose();
                                notReapet = true;
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Nama file sudah ada!", "", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (respon == JFileChooser.CANCEL_OPTION) {
                    notReapet = true;
                }
            } while (!notReapet);
            setUI(UIManager.getCrossPlatformLookAndFeelClassName());
        }

        // event menu file > tutup editor
        if (e.getSource() == menuBar.closeItem) {
            new Home();
            this.dispose();
        }

        // event menu file > keluar aplikasi
        if (e.getSource() == menuBar.exitItem) {
            this.dispose();
        }

        // event menu edit > seleksi semua
        if (e.getSource() == menuBar.selectAll) {
            table.selectAll();
        }

        // event menu edit > deselect
        if (e.getSource() == menuBar.deselect) {
            defaultTableModel.fireTableDataChanged();
        }

        // event menu edit > refresh tabel
        if (e.getSource() == menuBar.refresTable) {
            listBuku.clear();
            sukses = memuatDataBuku(new File(fileName), listBuku);

            for (int i = defaultTableModel.getRowCount() - 1; i >= 0; i--) {
                defaultTableModel.removeRow(i);
            }

            for (Buku buku : listBuku) {
                if (buku == null) {
                    break;
                }
                defaultTableModel.addRow(buku.geetArrayFormat());
            }
            defaultTableModel.fireTableDataChanged();
        }

        // event menu bantuan > petunjuk
        if (e.getSource() == menuBar.helpGuide) {
            new HelpGuide();
        }

        // event menu bantuan > jenis buku
        if (e.getSource() == menuBar.jenisBuku) {
            new HelpJenisBuku();
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

    public void loadData() {
        if (sukses) {
            for (Buku buku : listBuku) {
                if (buku == null) {
                    break;
                }
                defaultTableModel.addRow(buku.geetArrayFormat());
            }
        } else {
            boolean nullBooks = false;
            for (Buku buku : listBuku) {
                if (buku == null) {
                    nullBooks = true;
                    break;
                }
            }
            if (nullBooks) {
                JOptionPane.showMessageDialog(this, "Terdapat kesalahan format dalam file!", "Error", JOptionPane.WARNING_MESSAGE);
            } else
                JOptionPane.showMessageDialog(this, "File / Folder tidak ditemukan!", "Error", JOptionPane.WARNING_MESSAGE);
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
            if (e.getOppositeWindow().getClass().getName().contains("TambahBuku")) {
                if (tambahBukuDialog.isSubmit()) {
                    defaultTableModel.addRow(tambahBukuDialog.getBukuBaru());
                }
            } else if (e.getOppositeWindow().getClass().getName().contains("UpdateBuku")) {
                if (updateBukuDialog.isSubmit()) {
                    Object id, kodeBuku, judulBuku, jenisBuku, penulis, penerbit, tahunTerbit;
                    int row;

                    row = updateBukuDialog.getRow();

                    id = updateBukuDialog.getBukuBaru()[0];
                    System.out.println(id);
                    kodeBuku = updateBukuDialog.getBukuBaru()[1];
                    judulBuku = updateBukuDialog.getBukuBaru()[2];
                    jenisBuku = updateBukuDialog.getBukuBaru()[3];
                    penulis = updateBukuDialog.getBukuBaru()[4];
                    penerbit = updateBukuDialog.getBukuBaru()[5];
                    tahunTerbit = updateBukuDialog.getBukuBaru()[6];

                    defaultTableModel.setValueAt(id, row, 0);
                    defaultTableModel.setValueAt(kodeBuku, row, 1);
                    defaultTableModel.setValueAt(judulBuku, row, 2);
                    defaultTableModel.setValueAt(jenisBuku, row, 3);
                    defaultTableModel.setValueAt(penulis, row, 4);
                    defaultTableModel.setValueAt(penerbit, row, 5);
                    defaultTableModel.setValueAt(tahunTerbit, row, 6);
                }
            }
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
