package at.htl.photovoltaic.model;

public class Offer {
    private int id;
    private String name;
    private int numberOfPanels;
    private double powerPerPanel;
    private double totalPrice;

    public Offer(String name, int numberOfPanels, double powerPerPanel, double totalPrice) {
        this.name = name;
        this.numberOfPanels = numberOfPanels;
        this.powerPerPanel = powerPerPanel;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPanels() {
        return numberOfPanels;
    }

    public void setNumberOfPanels(int numberOfPanels) {
        this.numberOfPanels = numberOfPanels;
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
        return String.format("%s: %s, %.1f kWp, %.2f €", getId(), getName(), getPowerPerPanel() * getNumberOfPanels() / 1000.0, getTotalPrice());
    }
}
