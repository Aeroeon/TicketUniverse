import java.io.*;
import java.util.*;

public class TicketHandler {
    private ArrayList<Ticket> ticketList;

    public TicketHandler() {
        ticketList = new ArrayList<Ticket>();
    }

    public TicketHandler(String filename) {
        ticketList = new ArrayList<Ticket>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String ticketTransaction;

            while ((ticketTransaction = br.readLine()) != null && !ticketTransaction.equals("END"))  {
                ticketList.add(new Ticket(ticketTransaction));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Ticket find(String event, String sellerName) {
        // Use streams to find a ticket based on the two given criteria.
        return ticketList.stream()
                .filter(ticket -> event.equals(ticket.getEvent()) && sellerName.equals(ticket.getSellerName()))
                .findAny().orElse(null);
    }

    boolean add(Ticket t) {
        return ticketList.add(t);
    }

    boolean delete(Ticket t) {
        return ticketList.remove(t);
    }

    boolean write(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Ticket t : ticketList) {
                // EEEEEEEEEEEEEEEEEEEEEEEEE SSSSSSSSSSSSSSS TTT PPPPPP
                pw.println(t.getEvent() + '\t' + t.getSellerName() + '\t' + t.getTicketsAvailable() + '\t' + t.getPrice());
            }
            pw.flush();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}