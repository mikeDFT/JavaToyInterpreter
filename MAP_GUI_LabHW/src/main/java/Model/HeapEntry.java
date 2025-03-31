package Model;

import javafx.beans.property.SimpleStringProperty;

public class HeapEntry {
    public SimpleStringProperty  address;
    public SimpleStringProperty value;

    public HeapEntry(String address, String value) {
        this.address = new SimpleStringProperty(address);
        this.value = new SimpleStringProperty(value);
    }


    public SimpleStringProperty getAddress() {
        return address;
    }

    public SimpleStringProperty getValue() {
        return value;
    }
}
