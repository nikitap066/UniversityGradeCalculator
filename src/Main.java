import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private DashboardPanel panel;
    private TextBoxManager textBoxManager;
    private int textBoxCounter = 1;

    public Main() {
        setTitle("Uni Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //top bar with button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Assignment/Test");
        topPanel.add(addButton);
        add(topPanel, BorderLayout.NORTH);

        //panel that holds text fields
        panel = new DashboardPanel();
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        //bottom label to show combined text
        JLabel bottomLabel = new JLabel("Combined input: ");
        add(bottomLabel, BorderLayout.SOUTH);

        textBoxManager = new TextBoxManager(panel);

        //add text fields dynamically
        addButton.addActionListener(e -> {
            String id = "txt" + textBoxCounter;
            String label = "Assignment/Test " + textBoxCounter + ":";

            //add labeled text box via manager, which returns the JTextField
            JTextField field = textBoxManager.addTextBox(id, label);

            //add document listener to update average on input change
            field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                void update() {
                    double sum = 0;
                    int count = 0;
                    for (JTextField f : textBoxManager.getAllFields()) {
                        try {
                            String text = f.getText().trim();
                            if (!text.isEmpty()) {
                                sum += Double.parseDouble(text);
                                count++;
                            }
                        } catch (NumberFormatException ex) {
                            //textBoxManager.writeToTextBox(id, "");
                            //throw new NumberFormatException(label + ex.getMessage());
                        }
                    }
                    double average = count > 0 ? Math.round((sum / count) * 100) / 100.0 : 0;
                    bottomLabel.setText("Average: " + average);
                }
                public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
            });

            textBoxCounter++;
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
