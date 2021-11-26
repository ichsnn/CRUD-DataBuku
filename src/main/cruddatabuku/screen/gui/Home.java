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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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
        if (recentFile.exists()) {
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
        }
        recentFileField.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        recentFileField.setPreferredSize(new Dimension(129, 25));

        label1 = new JLabel("File : ");

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

        // button memilih file yang ingin dibuka
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
                recentFileField.setSelectedItem(file.getAbsolutePath());
            }

            setUI(UIManager.getCrossPlatformLookAndFeelClassName());
        }

        // button untuk membuka file atau membuka editor
        if (e.getSource() == bukaButton) {
            if (recentFileField.getSelectedItem() != null) {
                File opennedFile = new File((String) Objects.requireNonNull(recentFileField.getSelectedItem()));
                List<String> recentFileName = new ArrayList<>();
                for (int i = 0; i < recentFileField.getItemCount(); i++) {
                    recentFileName.add(recentFileField.getItemAt(i));
                }
                if (opennedFile.exists()) {
                    if (!recentFileName.contains(opennedFile.getPath())) {
                        recentFileName.add(0, (String) recentFileField.getSelectedItem());
                        try {
                            if (!recentFile.exists()) {
                                boolean created = recentFile.createNewFile();
                                System.out.println(created);
                            }
                            FileWriter recentFileWriter = new FileWriter(recentFile);
                            BufferedWriter bufferedWriter = new BufferedWriter(recentFileWriter);
                            for (int i = 0; i < recentFileName.size(); i++) {
                                if (i >= 5) break;
                                bufferedWriter.write((i != 0 ? "\n" : "") + recentFileName.get(i));
                            }
                            bufferedWriter.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        for (int i = 0; i < recentFileName.size(); i++) {
                            if (recentFileName.get(i).equals(recentFileField.getSelectedItem())) {
                                recentFileName.remove(i);
                                recentFileName.add(0, (String) recentFileField.getSelectedItem());
                                try {
                                    FileWriter recentFileWriter = new FileWriter(recentFile);
                                    BufferedWriter bufferedWriter = new BufferedWriter(recentFileWriter);
                                    for (int j = 0; j < recentFileName.size(); j++) {
                                        bufferedWriter.write((j != 0 ? "\n" : "") + recentFileName.get(j));
                                    }
                                    bufferedWriter.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                break;
                            }
                        }
                    }

                    if (recentFileField.getSelectedItem() != null) {
                        this.dispose();
                        new Editor(opennedFile);
                    } else {
                        JOptionPane.showMessageDialog(this, "Pilih file terlebih dahulu");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "File tidak ditemukan! File sepertinya telah dipindahkan, diganti nama atau dihapus\n(" + recentFileField.getSelectedItem() + ")", "Buka File", JOptionPane.WARNING_MESSAGE);
                    for (int i = 0; i < recentFileName.size(); i++) {
                        if (recentFileName.get(i).equals(recentFileField.getSelectedItem())) {
                            recentFileName.remove(i);
                            try {
                                FileWriter recentFileWriter = new FileWriter(recentFile);
                                BufferedWriter bufferedWriter = new BufferedWriter(recentFileWriter);
                                for (int j = 0; j < recentFileName.size(); j++) {
                                    bufferedWriter.write((j != 0 ? "\n" : "") + recentFileName.get(j));
                                }
                                bufferedWriter.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            recentFileField.removeAllItems();
                            for (String s : recentFileName) {
                                recentFileField.addItem(s);
                            }
                            break;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih terlebih dahulu file yang ingin dibuka atau buat file baru");
            }
        }

        // button keluar program atau mengakhiri program
        if (e.getSource() == keluarButton) {
            this.dispose();
        }

        // button membuat file baru
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
                                if (!recentFile.exists()) {
                                    boolean created = recentFile.createNewFile();
                                    System.out.println(created);
                                }
                                List<String> recentFileName = new ArrayList<>();
                                if (recentFileField.getSelectedItem() != null) {
                                    for (int i = 0; i < recentFileField.getItemCount(); i++) {
                                        recentFileName.add(recentFileField.getItemAt(i));
                                    }
                                }
                                recentFileName.add(0, String.valueOf(file));
                                FileWriter fileWriter = new FileWriter(recentFile);
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                for (int i = 0; i < recentFileName.size(); i++) {
                                    bufferedWriter.write((i != 0 ? "\n" : "") + recentFileName.get(i));
                                }
                                bufferedWriter.close();
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
