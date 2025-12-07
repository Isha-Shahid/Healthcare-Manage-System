package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JTextArea textArea;

    public MainView() {
        setTitle("Healthcare System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);
    }

    public void showMessage(String message) {
        textArea.append(message + "\n");
    }
}
