package server.server.models;

public class DonationData {
    
    private String name;
    private int amount;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public DonationData() {
    }
    public DonationData(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "DonationRequest [name=" + name + ", amount=" + amount + "]";
    }
}
