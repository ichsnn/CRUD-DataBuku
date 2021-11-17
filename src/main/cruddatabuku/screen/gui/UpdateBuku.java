package main.cruddatabuku.screen.gui;

import main.cruddatabuku.buku.DataBuku;
import main.cruddatabuku.util.Berkas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.cruddatabuku.util.Kamus.*;

public class UpdateBuku extends JDialog implements ActionListener {
    private final int id;
    private final int row;
    private String kodeBuku, judulBuku, penulis, penerbit, tahunTerbit;

    JPanel container;
    JPanel inputContainer;
    JPanel buttonContainer;

    JTextField textFieldKodeBuku;
    JTextField textFieldJudulBuku;
    JTextField textFieldPenulis;
    JTextField textFieldPenerbit;
    JTextField textFieldTahunTerbit;

    private final JButton updateButton = new JButton("Update");
    private final JButton cancelButton = new JButton("Batal");

    private List<DataBuku> listDataBuku;
    private final File file;

    private boolean submit;
    private Object[] bukuBaru;

    public UpdateBuku(Object[] objectsSelected, List<DataBuku> listDataBuku, String fileName, int row) {
        this.row = row;
        id = (int) objectsSelected[0];
        kodeBuku = (String) objectsSelected[1];
        judulBuku = (String) objectsSelected[2];
        penulis = (String) objectsSelected[4];
        penerbit = (String) objectsSelected[5];
        tahunTerbit = (String) objectsSelected[6];

        this.listDataBuku = listDataBuku;
        file = new File(fileName);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setIconImage(appIcon.getImage());
        this.setTitle("Update Buku");

        textFieldKodeBuku = new JTextField();
        textFieldKodeBuku.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldKodeBuku.setPreferredSize(new Dimension(200, 30));
        textFieldKodeBuku.setMargin(new Insets(0, 5, 0, 5));
        textFieldKodeBuku.setText(kodeBuku);

        textFieldJudulBuku = new JTextField();
        textFieldJudulBuku.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldJudulBuku.setMargin(new Insets(0, 5, 0, 5));
        textFieldJudulBuku.setText(judulBuku);

        textFieldPenulis = new JTextField();
        textFieldPenulis.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldPenulis.setMargin(new Insets(0, 5, 0, 5));
        textFieldPenulis.setText(penulis);

        textFieldPenerbit = new JTextField();
        textFieldPenerbit.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldPenerbit.setMargin(new Insets(0, 5, 0, 5));
        textFieldPenerbit.setText(penerbit);

        textFieldTahunTerbit = new JTextField();
        textFieldTahunTerbit.setFont(new Font("Calibri", Font.PLAIN, 16));
        textFieldTahunTerbit.setMargin(new Insets(0, 5, 0, 5));
        textFieldTahunTerbit.setPreferredSize(new Dimension(400, textFieldTahunTerbit.getHeight()));
        textFieldTahunTerbit.setText(tahunTerbit);

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

        updateButton.setFocusable(false);
        updateButton.addActionListener(this);
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
        buttonContainer.add(updateButton);
        buttonContainer.add(cancelButton);

        container = new JPanel();
        container.setMinimumSize(new Dimension(400, 400));
        container.setBorder(new EmptyBorder(10, 10, 10, 10));
        container.setBackground(Color.WHITE);
        container.setOpaque(true);
        container.setLayout(new BorderLayout(0, 10));
        container.add(inputContainer, BorderLayout.CENTER);
        container.add(buttonContainer, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(updateButton);
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.add(container);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.pack();
        this.setLocationRelativeTo(this.getParent());
        this.setVisible(true);
    }

    public void setListDataBuku(List<DataBuku> listDataBuku) {
        this.listDataBuku = listDataBuku;
    }

    public List<DataBuku> getListDataBuku() {
        return listDataBuku;
    }

    public boolean isSubmit() {
        return submit;
    }

    public boolean isInputValid(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public boolean isKodeBukuExist(List<DataBuku> listDataBuku) {
        if (textFieldKodeBuku.getText().contentEquals(kodeBuku)) {
            return false;
        } else {
            for (DataBuku buku : listDataBuku) {
                if (buku.getKodeBuku().contentEquals(textFieldKodeBuku.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isStringValid(String string) {
        return !string.contains(String.valueOf(PEMBATAS));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            String kodeBukuTemp;
            kodeBukuTemp = textFieldKodeBuku.getText();
            judulBuku = textFieldJudulBuku.getText();
            penulis = textFieldPenulis.getText();
            penerbit = textFieldPenerbit.getText();
            tahunTerbit = textFieldTahunTerbit.getText();

            boolean validasiKB, validasiThn, validasiBuku, validasiJudul, validasiPenulis, validasiPenerbit;
            validasiKB = isInputValid(textFieldKodeBuku.getText(), P_KODEBUKU);
            validasiThn = isInputValid(textFieldTahunTerbit.getText(), P_TAHUNTERBIT);
            validasiBuku = !isKodeBukuExist(listDataBuku);
            validasiJudul = isStringValid(textFieldJudulBuku.getText());
            validasiPenulis = isStringValid(textFieldPenulis.getText());
            validasiPenerbit = isStringValid(textFieldPenerbit.getText());

            boolean sukses = ((validasiKB && validasiThn && validasiJudul && validasiPenerbit && validasiPenulis) && validasiBuku);

            if (sukses) {
                kodeBuku = kodeBukuTemp;
                for (DataBuku buku : listDataBuku) {
                    if (buku.getId() == id) {
                        buku.setKodeBuku(kodeBuku);
                        buku.setJudulBuku(judulBuku);
                        buku.setPenulis(penulis);
                        buku.setPenerbit(penerbit);
                        buku.setTahunTerbit(tahunTerbit);
                        bukuBaru = new Object[] {
                                id,
                                kodeBuku,
                                judulBuku,
                                buku.getJenisBuku(),
                                penulis,
                                penerbit,
                                tahunTerbit
                        };
                        break;
                    }
                }

                Berkas.update(file.getPath(), listDataBuku);

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

    public int getRow() {
        return row;
    }
}
