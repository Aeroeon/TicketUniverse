import java.util.*;

public class Ticket {
    private String event;
    private String sellerName;
    private int ticketsAvailable;
    private double price;

    public Ticket(String event, String sellerName, int ticketsAvailable, double price) {
        setEvent(event);
        setSellerName(sellerName);
        setAvailable(ticketsAvailable);
        setPrice(price); 
    }

    public String getEvent() {
        return this.event;
    }

    public String getSellerName() {
        return this.sellerName;
    }

    public int getTicketsAvailable() {
        return this.ticketsAvailable;
    }

    public double getPrice() {
        return this.price;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}