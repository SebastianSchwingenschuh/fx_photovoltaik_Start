package at.htl.photovoltaic.model;

public class Offer {
    private final int id;
    private String name;
    private int NumberOfPanels;
    private double powerPerPanel;
    private double totalPrice;

    public Offer(String name, int numberOfPanels, int powerPerPanel, int totalPrice) {
        this.id = 0;
        this.name = name;
        NumberOfPanels = numberOfPanels;
        this.powerPerPanel = powerPerPanel;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPanels() {
        return NumberOfPanels;
    }

    public void setNumberOfPanels(int numberOfPanels) {
        NumberOfPanels = numberOfPanels;
    }

    public double getPowerPerPanel() {
        return powerPerPanel;
    }

    public void setPowerPerPanel(double powerPerPanel) {
        this.powerPerPanel = powerPerPanel;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("%d: %s, %.1f kWp, %.2f €", this.getId(), getName(), getPowerPerPanel()*getNumberOfPanels(), getTotalPrice());
    }
}
