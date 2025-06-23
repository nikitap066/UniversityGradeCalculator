import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {

    private final DashboardPanel panel;
    private final Map<String, JTextField> valueFields = new HashMap<>();
    private final Map<String, JTextField> worthFields = new HashMap<>();
    private final JLabel bottomLabel = new JLabel("Weighted Average or Remaining Mark will show here.");
    private final JTextField targetField = new JTextField("70", 5); // default target
    private int textBoxCounter = 1;

    public Main() {
        setTitle("University Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        //top panel for target mark input
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Target Mark (%):"));
        topPanel.add(targetField);
        add(topPanel, BorderLayout.NORTH);

        //main panel for dynamic text fields
        panel = new DashboardPanel();
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        //button to add new fields
        JButton addButton = new JButton("Add Grade");
        add(addButton, BorderLayout.EAST);

        //bottom label
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(bottomLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        //grade button logic
        addButton.addActionListener(e -> {
            String id = "txt" + textBoxCounter;

            JTextField gradeField = panel.addLabeledTextField(id + "_val", "Grade " + textBoxCounter + ":");
            JTextField worthField = panel.addLabeledTextField(id + "_worth", "Worth (%) " + textBoxCounter + ":");

            valueFields.put(id, gradeField);
            worthFields.put(id, worthField);

            DocumentListener listener = new DocumentListener() {
                void update() {
                    updateAverage();
                }

                public void insertUpdate(DocumentEvent e) { update(); }
                public void removeUpdate(DocumentEvent e) { update(); }
                public void changedUpdate(DocumentEvent e) { update(); }
            };

            gradeField.getDocument().addDocumentListener(listener);
            worthField.getDocument().addDocumentListener(listener);
            targetField.getDocument().addDocumentListener(listener);

            textBoxCounter++;
        });

        setVisible(true);
    }

    private void updateAverage() {
        double total = 0;
        double weightSum = 0;
        double targetMark = 70;

        try {
            targetMark = Double.parseDouble(targetField.getText().trim());
        } catch (NumberFormatException ignored) {}

        for (String id : valueFields.keySet()) {
            JTextField gradeField = valueFields.get(id);
            JTextField worthField = worthFields.get(id);

            try {
                double grade = Double.parseDouble(gradeField.getText().trim());
                double worth = Double.parseDouble(worthField.getText().trim());
                total += grade * (worth / 100.0);
                weightSum += worth;
            } catch (NumberFormatException ignored) {}
        }

        double average = Math.round((total / weightSum) * 100);
        if (weightSum < 100) {
            double remaining = Math.round((100 - weightSum) * 100.0) / 100.0;
            double required = Math.round(((targetMark - total) / remaining) * 100);
            if  (required > 100) {
                double maxPotentiallyAchieved = total + ((remaining / 100.0) * 100);
                bottomLabel.setText("Cannot reach " + targetMark + "% overall. " +
                        "Maximum overall grade that can be achieved is " + maxPotentiallyAchieved);
            }
            else if (required < 0) {
                required = 0;
            }
            bottomLabel.setText("Need " + required + "/100 in remaining "
                    + remaining + "% to reach " + targetMark + "% overall. Current average is " + average + "%");
        }
        else if (weightSum == 100) {
            bottomLabel.setText("Module Total: " + average + "/100");
        }
        else {
            bottomLabel.setText("Error: Total weight exceeds 100%");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    //panel that stacks rows vertically
    static class DashboardPanel extends JPanel {
        public DashboardPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);
        }

        public JTextField addLabeledTextField(String id, String labelText) {
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setOpaque(false);

            JLabel label = new JLabel(labelText);
            label.setPreferredSize(new Dimension(150, 30));
            row.add(label);

            JTextField field = new JTextField();
            field.setName(id);
            field.setPreferredSize(new Dimension(100, 30));
            field.setMaximumSize(new Dimension(100, 30));
            row.add(field);

            add(Box.createVerticalStrut(5));
            add(row);

            revalidate();
            repaint();

            return field;
        }
    }
}
