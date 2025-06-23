import javax.swing.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TextBoxManager {

    private DashboardPanel panel;
    private HashMap<String, JTextField> valueFields = new HashMap<>();
    private HashMap<String, JTextField> worthFields = new HashMap<>();

    public TextBoxManager(DashboardPanel panel) {
        this.panel = panel;
    }

    //grade/value text field
    public JTextField addTextBox(String id, String labelText) {
        JTextField field = panel.addLabeledTextField(id, labelText);
        valueFields.put(id, field);
        return field;
    }

    //worth/weight text field
    public JTextField addWorthBox(String id, String labelText) {
        String fullId = id + "_worth";
        JTextField field = panel.addLabeledTextField(fullId, labelText);
        worthFields.put(id, field);
        return field;
    }

    public Collection<JTextField> getValueFields() {
        return valueFields.values();
    }

    public Collection<JTextField> getWorthFields() {
        return worthFields.values();
    }

    public JTextField getWorthField(String id) {
        return worthFields.get(id);
    }

    public JTextField getValueField(String id) {
        return valueFields.get(id);
    }

    public Map<String, JTextField> getValueFieldMap() {
        return valueFields;
    }

}

