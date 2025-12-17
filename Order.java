public class Order {
    private static int nextId = 1;
    private int id;
    private String client;
    private Deliverable deliverable;
    private double cost;
    private String urgency; 
    private String status; 

    public Order(String client, Deliverable deliverable, String urgency, double cost) {
        this.id = nextId++;
        this.client = client;
        this.deliverable = deliverable;
        this.urgency = urgency;
        this.cost = cost;
        this.status = "PENDING";
    }

    public int getId() { return id; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public Deliverable getDeliverable() { return deliverable; }
    public void setDeliverable(Deliverable deliverable) { this.deliverable = deliverable; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
