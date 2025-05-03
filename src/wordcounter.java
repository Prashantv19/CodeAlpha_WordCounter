import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class wordcounter extends JFrame {

    private JTextArea textArea;
    private JButton countButton;
    private JLabel resultLabel;

    public wordcounter() {
        // Frame settings
        setTitle("Word Counter App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text area
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button and result panel
        JPanel bottomPanel = new JPanel();
        countButton = new JButton("Count Words");
        resultLabel = new JLabel("Words: 0");
        bottomPanel.add(countButton);
        bottomPanel.add(resultLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button action
        countButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText().trim();
                if (text.isEmpty()) {
                    resultLabel.setText("Words: 0");
                } else {
                    String[] words = text.split("\\s+");
                    resultLabel.setText("Words: " + words.length);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new wordcounter();
    }
}
