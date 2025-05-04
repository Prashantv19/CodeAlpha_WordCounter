import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class wordcounter extends JFrame {

    private JTextArea textArea;
    private JLabel resultLabel;

    public wordcounter() {
        setTitle("Word Counter App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(178, 214, 255));

        JLabel titleLabel = new JLabel("WordCounter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Main text area
        textArea = new JTextArea("Enter your text here...");
        textArea.setForeground(Color.GRAY);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        textArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals("Enter your text here...")) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setForeground(Color.GRAY);
                    textArea.setText("Enter your text here...");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with buttons and word count
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(178, 214, 255));

        JButton countButton = new JButton("Count Words");
        JButton saveButton = new JButton("Save");

        resultLabel = new JLabel("Words: 0");
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        bottomPanel.add(countButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(resultLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        countButton.addActionListener(e -> countWords());
        saveButton.addActionListener(e -> saveToCSV());
        setVisible(true);
    }

    private void countWords() {
        String text = textArea.getText().trim();
        if (text.equals("Enter your text here...") || text.isEmpty()) {
            resultLabel.setText("Words: 0");
        } else {
            String[] words = text.split("\\s+");
            resultLabel.setText("Words: " + words.length);
        }
    }

    private void saveToCSV() {
        String text = textArea.getText().trim();
        if (text.equals("Enter your text here...") || text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some text before saving.");
            return;
        }

        String[] words = text.split("\\s+");
        int wordCount = words.length;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Word Count CSV");
        fileChooser.setSelectedFile(new java.io.File("wordcounter_log.csv"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file, true)) {
                String escapedText = text.replace("\"", "\"\"");
                writer.write("\"" + timestamp + "\"," + wordCount + ",\"" + escapedText + "\"\n");
                JOptionPane.showMessageDialog(this, "Saved successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new wordcounter();
    }
}
