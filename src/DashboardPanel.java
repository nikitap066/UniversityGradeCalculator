import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class DashboardPanel extends JPanel {

    private HashMap<String, JTextField> textFields = new HashMap<>();

    public DashboardPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);
    }

    public JTextField addLabeledTextField(String id, String labelText) {
        //create a horizontal panel to hold label + field
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setOpaque(false);

        //create label
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 30)); //fixed width label
        row.add(label);

        //create text field
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 30));
        textField.setMaximumSize(new Dimension(300, 30));
        textFields.put(id, textField);
        row.add(textField);

        //add spacing below
        add(Box.createVerticalStrut(10));
        add(row);

        revalidate();
        repaint();

        return textField;
    }

    public void setText(String id, String text) {
        JTextField field = textFields.get(id);
        if (field != null) {
            field.setText(text);
        }
    }

    public Collection<JTextField> getAllTextFields() {
        return textFields.values();
    }

    public JTextField getTextField(String id) {
        return textFields.get(id);
    }
}
