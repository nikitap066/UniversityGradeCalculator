import javax.swing.*;
import java.util.Collection;

public class TextBoxManager {

    private DashboardPanel panel;

    public TextBoxManager(DashboardPanel panel) {
        this.panel = panel;
    }

    //change return type so caller can get JTextField for listeners
    public JTextField addTextBox(String id, String labelText) {
        return panel.addLabeledTextField(id, labelText);
    }

    //return all text fields by asking the panel
    public Collection<JTextField> getAllFields() {
        return panel.getAllTextFields();
    }
}
