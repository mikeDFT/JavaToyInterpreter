package Model;

import javafx.beans.property.SimpleStringProperty;

public class SymbolEntry {
    public SimpleStringProperty var;
    public SimpleStringProperty value;

    public SymbolEntry(String var, String value) {
        this.var = new SimpleStringProperty(var);
        this.value = new SimpleStringProperty(value);
    }

    public SimpleStringProperty getVar() {
        return var;
    }

    public SimpleStringProperty getValue() {
        return value;
    }
}
