package main.cruddatabuku.screen.gui;

import main.cruddatabuku.buku.Buku;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.cruddatabuku.util.Library.*;

public class TambahBuku extends JDialog implements ActionListener {
    private final JButton submitButton = new JButton("Submit");
    private final JButton cancelButton = new JButton("Batal");
    private final File file;
    private final JTextField textFieldKodeBuku;
    private final JTextField textFieldJudulBuku;
    private final JTextField textFieldPenulis;
    private final JTextField textFieldPenerbit;
    private final JTextField textFieldTahunTerbit;
    JPanel container;
    JPanel inputContainer;
    JPanel buttonContainer;
    private final List<Buku> listBuku;
    private boolean submit;
    private Object[] bukuBaru;

    public TambahBuku(List<Buku> listBuku, String fileName) {
        submit = false;
        this.listBuku = listBuku;
        this.file = new File(fileName);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setIconImage(appIcon.getImage());
        this.setTitle("Tambah Buku");

        textFieldKodeBuku = new JTextField();
        textFieldKodeBuku.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldKodeBuku.setPreferredSize(new Dimension(200, 30));
        textFieldKodeBuku.setMargin(new Insets(0, 5, 0, 5));

        textFieldJudulBuku = new JTextField();
        textFieldJudulBuku.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldJudulBuku.setMargin(new Insets(0, 5, 0, 5));

        textFieldPenulis = new JTextField();
        textFieldPenulis.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldPenulis.setMargin(new Insets(0, 5, 0, 5));

        textFieldPenerbit = new JTextField();
        textFieldPenerbit.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldPenerbit.setMargin(new Insets(0, 5, 0, 5));

        textFieldTahunTerbit = new JTextField();
        textFieldTahunTerbit.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldTahunTerbit.setMargin(new Insets(0, 5, 0, 5));
        textFieldTahunTerbit.setPreferredSize(new Dimension(400, textFieldTahunTerbit.getHeight()));

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setBackground(Color.WHITE);
        textFieldPanel.setOpaque(true);
        textFieldPanel.setLayout(new GridLayout(5, 1, 0, 10));
        textFieldPanel.add(textFieldKodeBuku);
        textFieldPanel.add(textFieldJudulBuku);
        textFieldPanel.add(textFieldPenulis);
        textFieldPanel.add(textFieldPenerbit);
        textFieldPanel.add(textFieldTahunTerbit);

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.white);
        labelPanel.setOpaque(true);
        labelPanel.setLayout(new GridLayout(5, 1, 0, 10));

        JLabel label1 = new JLabel("Kode Buku");
        label1.setFont(new Font("Calibri", Font.PLAIN, 16));
        JLabel label2 = new JLabel("Judul Buku");
        label2.setFont(new Font("Calibri", Font.PLAIN, 16));
        JLabel label3 = new JLabel("Penulis");
        label3.setFont(new Font("Calibri", Font.PLAIN, 16));
        JLabel label4 = new JLabel("Penerbit");
        label4.setFont(new Font("Calibri", Font.PLAIN, 16));
        JLabel label5 = new JLabel("Tahun Terbit");
        label5.setFont(new Font("Calibri", Font.PLAIN, 16));

        labelPanel.add(label1);
        labelPanel.add(label2);
        labelPanel.add(label3);
        labelPanel.add(label4);
        labelPanel.add(label5);

        submitButton.setUI(new BasicButtonUI());
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);
        cancelButton.setUI(new BasicButtonUI());
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);

        inputContainer = new JPanel();
        inputContainer.setBackground(Color.white);
        inputContainer.setOpaque(true);
        inputContainer.setLayout(new BorderLayout(10, 0));
        inputContainer.add(labelPanel, BorderLayout.WEST);
        inputContainer.add(textFieldPanel, BorderLayout.CENTER);

        buttonContainer = new JPanel();
        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.setOpaque(true);
        buttonContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.add(submitButton);
        buttonContainer.add(cancelButton);

        container = new JPanel();
        container.setMinimumSize(new Dimension(400, 400));
        container.setBorder(new EmptyBorder(10, 10, 10, 10));
        container.setBackground(Color.WHITE);
        container.setOpaque(true);
        container.setLayout(new BorderLayout(0, 10));
        container.add(inputContainer, BorderLayout.CENTER);
        container.add(buttonContainer, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(submitButton);
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.add(container);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.pack();
        this.setLocationRelativeTo(this.getParent());
        this.setVisible(true);
    }

    public boolean isSubmit() {
        return submit;
    }

    public boolean isInputValid(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public boolean isKodeBukuExist(List<Buku> listBuku) {
        for (Buku buku : listBuku) {
            if (buku.getKodeBuku().contentEquals(textFieldKodeBuku.getText())) {
                return true;
            }
        }
        return false;
    }

    public boolean isStringValid(String string) {
        return !string.contains(String.valueOf(PEMBATAS));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            int id;
            String kodeBuku, judulBuku, penulis, penerbit, tahunTerbit;
            if (listBuku.isEmpty()) {
                id = 1;
            } else {
                id = listBuku.get(listBuku.size() - 1).getId() + 1;
            }

            kodeBuku = textFieldKodeBuku.getText();
            judulBuku = textFieldJudulBuku.getText();
            penulis = textFieldPenulis.getText();
            penerbit = textFieldPenerbit.getText();
            tahunTerbit = textFieldTahunTerbit.getText();

            boolean validasiKB, validasiThn, validasiBuku, validasiJudul, validasiPenulis, validasiPenerbit;
            validasiKB = isInputValid(textFieldKodeBuku.getText(), P_KODEBUKU);
            validasiThn = isInputValid(textFieldTahunTerbit.getText(), P_TAHUNTERBIT);
            validasiBuku = !isKodeBukuExist(listBuku);
            validasiJudul = isStringValid(textFieldJudulBuku.getText());
            validasiPenulis = isStringValid(textFieldPenulis.getText());
            validasiPenerbit = isStringValid(textFieldPenerbit.getText());

            boolean sukses = ((validasiKB && validasiThn && validasiJudul && validasiPenerbit && validasiPenulis) && validasiBuku);

            if (sukses) {
                Buku buku = new Buku(id, kodeBuku, judulBuku, penulis, penerbit, tahunTerbit);
                bukuBaru = new Object[]{id, kodeBuku, judulBuku, buku.getJenisBuku(), penulis, penerbit, tahunTerbit};

                listBuku.add(buku);

                try {
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write((file.length() != 0 ? "\n" : "") + buku.getTxtFormat());
                    bufferedWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.dispose();
                submit = true;
            } else {
                if (kodeBuku.isEmpty() || judulBuku.isEmpty() || penulis.isEmpty() || penerbit.isEmpty() || tahunTerbit.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Harap isi semua field!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else if (!validasiBuku) {
                    JOptionPane.showMessageDialog(this, "Kode buku sudah ada", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else if (!validasiKB) {
                    JOptionPane.showMessageDialog(this, "Kode buku tidak valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else if (!validasiThn) {
                    JOptionPane.showMessageDialog(this, "Tahun salah! Gunakan format (YYYY)", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Jangan gunakan karakter " + PEMBATAS, "Peringatan", JOptionPane.WARNING_MESSAGE);
                }

            }
        }

        if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }

    public Object[] getBukuBaru() {
        return bukuBaru;
    }
}
