package model;

public class JamSlot {
    private String label;
    private String value;

    public JamSlot(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }
}
