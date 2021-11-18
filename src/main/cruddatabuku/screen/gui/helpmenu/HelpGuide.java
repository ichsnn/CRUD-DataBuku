package main.cruddatabuku.screen.gui.helpmenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static main.cruddatabuku.util.Library.appIcon;

public class HelpGuide extends JDialog {
    public HelpGuide() {
        JLabel titleLabel = new JLabel();
        JTextArea textArea = new JTextArea();
        JPanel buttonContainer = new JPanel();
        JPanel container = new JPanel();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("help-guide.txt"))));
            String data;
            int i = 0;
            while ((data = bufferedReader.readLine()) != null) {
                if (i == 0) {
                    titleLabel.setText(data);
                } else {
                    textArea.setText(textArea.getText() + data + "\n");
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton confirmButton = new JButton("Tutup");
        confirmButton.setUI(new BasicButtonUI());
        confirmButton.setFocusable(false);
        confirmButton.addActionListener(e -> this.dispose());

        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        textArea.setEditable(false);

        container.setBorder(new EmptyBorder(10, 20, 0, 20));
        container.setBackground(Color.white);
        container.setOpaque(true);
        container.setLayout(new BorderLayout(0, 10));
        container.add(titleLabel, BorderLayout.NORTH);
        container.add(textArea, BorderLayout.CENTER);

        buttonContainer.setLayout(new FlowLayout());
        buttonContainer.setBackground(Color.white);
        buttonContainer.setOpaque(true);
        buttonContainer.setBorder(new EmptyBorder(0, 0, 5, 0));
        buttonContainer.add(confirmButton);

        this.setIconImage(appIcon.getImage());
        this.setTitle("Bantuan - Petunjuk");
        this.setResizable(false);
        this.getRootPane().setDefaultButton(confirmButton);
        this.setLayout(new BorderLayout());
        this.add(container, BorderLayout.CENTER);
        this.add(buttonContainer, BorderLayout.SOUTH);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.pack();
        this.setLocationRelativeTo(getParent());
        this.setVisible(true);
    }
}
