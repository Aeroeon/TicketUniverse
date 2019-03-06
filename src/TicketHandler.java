import java.util.*;

public class TicketHandler {
    private ArrayList<Ticket> ticketList;

    public TicketHandler(String filename) {
        ticketList = new ArrayList<Ticket>();
    }

    Ticket find(String event, String sellerName) {
        // Use streams to find a ticket based on the two given criteria.
        return ticketList.stream()
                .filter(ticket -> event.equals(ticket.getEvent()) && sellerName.equals(ticket.getSellerName()))
                .findAny().orElse(null);
    }

    void add(Ticket t) {
        ticketList.add(t);
    }

    void delete(Ticket t) {
        ticketList.remove(t);
    }

    void write(String filename) {
        // TODO
    }

}