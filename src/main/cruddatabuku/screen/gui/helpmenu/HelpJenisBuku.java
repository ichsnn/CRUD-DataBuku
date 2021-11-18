package main.cruddatabuku.screen.gui.helpmenu;

import main.cruddatabuku.screen.gui.component.Button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static main.cruddatabuku.util.Library.appIcon;

public class HelpJenisBuku extends JDialog {
    public HelpJenisBuku() {
        JLabel titleLabel = new JLabel();
        JTextArea textArea = new JTextArea();
        JPanel container = new JPanel();
        JPanel buttonContainer = new JPanel();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("help-jenis-kode-buku.txt"))));
            String data;
            int i = 0;
            while ((data = bufferedReader.readLine()) != null) {
                if (i == 0) {
                    titleLabel.setText(data);
                } else
                    textArea.setText(textArea.getText() + data + "\n");
                i++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        main.cruddatabuku.screen.gui.component.Button confirmButton = new Button("Tutup");
        confirmButton.setUI(new BasicButtonUI());
        confirmButton.setFocusable(false);
        confirmButton.addActionListener(e -> this.dispose());

        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));

        textArea.setEditable(false);
        textArea.setFont(new Font("Dialog", Font.PLAIN, 14));

        container.setBorder(new EmptyBorder(10, 20, 0, 20));
        container.setBackground(Color.WHITE);
        container.setOpaque(true);
        container.setLayout(new BorderLayout(0, 10));
        container.add(titleLabel, BorderLayout.NORTH);
        container.add(textArea, BorderLayout.CENTER);

        buttonContainer.setBackground(Color.WHITE);
        buttonContainer.setBorder(new EmptyBorder(0, 0, 5, 0));
        buttonContainer.setOpaque(true);
        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.add(confirmButton);

        this.setIconImage(appIcon.getImage());
        this.setTitle("Bantuan - Jenis Buku");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setBackground(Color.WHITE);
        this.add(container, BorderLayout.CENTER);
        this.add(buttonContainer, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getRootPane().setDefaultButton(confirmButton);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.pack();
        this.setLocationRelativeTo(getParent());
        this.setVisible(true);
    }
}
