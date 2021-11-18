/*
Home.java | Digunakan sebagai beranda aplikasi untuk memilih file yang ingin dibuka atau membuat file baru
 */

package main.cruddatabuku.screen.gui;

import main.cruddatabuku.screen.gui.component.Button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

import static main.cruddatabuku.screen.gui.component.UIManagerMethod.setUI;
import static main.cruddatabuku.util.Library.appIcon;
import static main.cruddatabuku.util.Library.recentFile;

public class Home extends JFrame implements ActionListener {
    JFileChooser fileChooser;

    Button fileChooserButton = new Button();
    Button bukaButton = new Button("Buka");
    Button keluarButton = new Button("Keluar");
    Button buatFileButton = new Button("Buat File Baru");

    JPanel buttonContainer;
    JPanel container;
    JPanel folderPanel;
    JLabel label1;
    JLabel labelTitle;
    JTextField textField;
    JComboBox<String> recentFileField;
    File file;

    public Home() {
        labelTitle = new JLabel("APLIKASI PENGELOLA DAFTAR BUKU");
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelTitle.setFont(new Font("Calibri", Font.BOLD, 24));

        ImageIcon folderIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/folder-open.png")));
        Image image = folderIcon.getImage();
        Image newImg = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        folderIcon = new ImageIcon(newImg);

        fileChooserButton.setIcon(folderIcon);
        fileChooserButton.setBounds(0, 0, 16, 16);
        fileChooserButton.addActionListener(this);

        recentFileField = new JComboBox<>();
        recentFileField.addActionListener(this);
        recentFileField.setEditable(true);
        try {
            FileReader recentFileReader = new FileReader(recentFile);
            Scanner scan = new Scanner(recentFileReader);
            String recentFile;
            while (scan.hasNextLine()) {
                recentFile = scan.nextLine();
                recentFileField.addItem(recentFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        recentFileField.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));

        //file = new File("./data/dataBuku.txt");
        label1 = new JLabel("File : ");
        //textField = new JTextField();
        //textField.setBackground(Color.white);
        //textField.setPreferredSize(new Dimension(100, 16));
        //if (file.exists()) {
        //    textField.setText(file.getPath());
        //    textField.setCaretPosition(file.getPath().length());
        //}
        //textField.setMargin(new Insets(5, 10, 5, 10));

        bukaButton.addActionListener(this);
        keluarButton.addActionListener(this);
        buatFileButton.addActionListener(this);

        buttonContainer = new JPanel();
        buttonContainer.setBackground(Color.white);
        buttonContainer.setOpaque(true);
        buttonContainer.setLayout(new GridLayout(1, 3, 5, 5));
        buttonContainer.add(bukaButton);
        buttonContainer.add(buatFileButton);
        buttonContainer.add(keluarButton);

        folderPanel = new JPanel();
        folderPanel.setBackground(Color.WHITE);
        folderPanel.setOpaque(true);
        folderPanel.setLayout(new BorderLayout(5, 10));
        folderPanel.add(label1, BorderLayout.WEST);
        folderPanel.add(fileChooserButton, BorderLayout.EAST);
        folderPanel.add(recentFileField, BorderLayout.CENTER);
        folderPanel.add(buttonContainer, BorderLayout.SOUTH);

        container = new JPanel();
        container.setBorder(new EmptyBorder(20, 20, 15, 20));
        container.setBackground(Color.white);
        container.setOpaque(true);
        container.setLayout(new BorderLayout());
        container.add(labelTitle, BorderLayout.NORTH);
        container.add(folderPanel, BorderLayout.CENTER);

        JLabel createdBy = new JLabel("Dibuat Oleh : Ichsan Nulmuhlis", JLabel.CENTER);
        createdBy.setBorder(new EmptyBorder(0, 0, 5, 0));
        createdBy.setFont(new Font("Calibri", Font.PLAIN, createdBy.getFont().getSize()));
        createdBy.setBackground(Color.WHITE);
        createdBy.setOpaque(true);

        this.setIconImage(appIcon.getImage());
        this.setTitle("Aplikasi Pengelola Daftar Buku");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.add(container, BorderLayout.CENTER);
        this.add(createdBy, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if (e.getSource() == recentFileField) {
        //    file = new File(recentFileField.getSelectedItem().toString());
        //    System.out.println(file.getPath());
        //}

        if (e.getSource() == fileChooserButton) {
            setUI(UIManager.getSystemLookAndFeelClassName());

            fileChooser = new JFileChooser();
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
                file = fileChooser.getSelectedFile();
                recentFileField.setSelectedItem(file.getPath());
            }

            setUI(UIManager.getCrossPlatformLookAndFeelClassName());
        }

        if (e.getSource() == bukaButton) {
            File opennedFile = new File((String) Objects.requireNonNull(recentFileField.getSelectedItem()));
            if (opennedFile.exists()) {
                List<String> recentFileName = new ArrayList<>();
                for (int i = 0; i < recentFileField.getItemCount(); i++) {
                    recentFileName.add(recentFileField.getItemAt(i));
                }
                if (!recentFileName.contains(opennedFile.getPath())) {
                    try {
                        FileWriter recentFileWriter = new FileWriter(recentFile, true);
                        BufferedWriter bufferedWriter = new BufferedWriter(recentFileWriter);
                        bufferedWriter.write((recentFile.length() != 0 ? "\n" : "") + opennedFile.getPath());
                        bufferedWriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (recentFileField.getSelectedItem() != null) {
                    new Editor(opennedFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Pilih file terlebih dahulu");
                }
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan! File sepertinya telah dipindahkan, diganti nama atau dihapus\n(" + recentFileField.getSelectedItem() + ")", "Buka File", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getSource() == keluarButton) {
            this.dispose();
        }

        if (e.getSource() == buatFileButton) {
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
            createFile.setCurrentDirectory(new File("./data"));
            createFile.setDialogTitle("Crate New File");

            boolean notReapet = false;
            int respon;
            File newFile;
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
                                file = newFile;
                                setUI(UIManager.getCrossPlatformLookAndFeelClassName());
                                new Editor(file);
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
    }
}
