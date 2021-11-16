package main.cruddatabuku.screen.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static main.cruddatabuku.util.Kamus.appIcon;

public class GUIApp extends JFrame implements ActionListener {
    JFileChooser fileChooser;
    JButton fileChooserButton;
    JButton bukaButton;
    JButton keluarButton;
    JButton buatFileButton;
    JPanel buttonContainer;
    JPanel container;
    JPanel folderPanel;
    JLabel label1;
    JLabel labelTitle;
    JTextField textField;
    File file;

    public GUIApp() {
        labelTitle = new JLabel("APLIKASI PENGELOLA DAFTAR BUKU");
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelTitle.setFont(new Font("Calibri", Font.BOLD, 24));

        ImageIcon folderIcon = new ImageIcon("./assets/folder-open.png");
        Image image = folderIcon.getImage();
        Image newImg = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        folderIcon = new ImageIcon(newImg);

        fileChooserButton = new JButton();
        fileChooserButton.setFocusable(false);
        fileChooserButton.setIcon(folderIcon);
        fileChooserButton.setBounds(0, 0, 16, 16);
        fileChooserButton.addActionListener(this);

        file = new File("./data/dataBuku.txt");
        label1 = new JLabel("File : ");
        textField = new JTextField();
        textField.setBackground(Color.white);
        textField.setPreferredSize(new Dimension(100, textField.getHeight()));
        textField.setText(file.getPath());
        textField.setMargin(new Insets(5, 10, 5, 10));
        textField.setCaretPosition(file.getPath().length());

        bukaButton = new JButton("Buka");
        keluarButton = new JButton("Keluar");
        buatFileButton = new JButton("Buat File Baru");

        bukaButton.setFocusable(false);
        bukaButton.addActionListener(this);
        keluarButton.setFocusable(false);
        keluarButton.addActionListener(this);
        buatFileButton.setFocusable(false);
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
        folderPanel.add(textField, BorderLayout.CENTER);
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

        //new MainMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileChooserButton) {
            //getSystemUI(UIManager.getSystemLookAndFeelClassName());

            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./data"));
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
                textField.setText(file.getPath());
            }

            //getSystemUI(UIManager.getCrossPlatformLookAndFeelClassName());
        }

        if (e.getSource() == bukaButton) {
            Editor editor = new Editor(file);
            this.dispose();
        }

        if (e.getSource() == keluarButton) {
            this.dispose();
        }

        if (e.getSource() == buatFileButton) {
            //getSystemUI(UIManager.getSystemLookAndFeelClassName());

            JFileChooser createFile = new JFileChooser();
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
            do {
                respon = createFile.showSaveDialog(this);
                if (respon == JFileChooser.APPROVE_OPTION) {
                    File newFile = new File(createFile.getSelectedFile().getPath() + ".txt");
                    if (!newFile.exists()) {
                        try {
                            boolean success = newFile.createNewFile();
                            if (success) {
                                file = newFile;
                                getSystemUI(UIManager.getCrossPlatformLookAndFeelClassName());
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
                System.out.println(respon);
            } while (!notReapet);


            //getSystemUI(UIManager.getCrossPlatformLookAndFeelClassName());
        }
    }

    private void getSystemUI(String systemLookAndFeelClassName) {
        try {
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }
}
