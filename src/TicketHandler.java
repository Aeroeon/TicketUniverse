import java.io.*;
import java.util.*;

public class TicketHandler {
    private ArrayList<Ticket> ticketList;
    private final int NAMELENGTH = 15;
    private final int EVENTLENGTH = 25;
    private final int TICKETLENGTH = 3;
    private final int CREDITLENGTH = 6;

    //Padding for User
    private String paddUser(String username) {
        if (username.length() == NAMELENGTH) {
          return username;
        }
        String padded = username;
        for (int i = 0; i < NAMELENGTH - username.length(); i++) {
          padded += " ";
        }
        return padded;
      }

      private String paddEvent(String eventName) {
        if (eventName.length() == EVENTLENGTH) {
          return eventName;
        }
        String padded = eventName;
        for (int i = 0; i < EVENTLENGTH - eventName.length(); i++) {
          padded += " ";
        }
        return padded;
      }

      private String paddTicket(int inttickets) {
        String tickets = Integer.toString(inttickets);
        if (tickets.length() == TICKETLENGTH) {
          return tickets;
        }
        String padded = tickets;
        for (int i = 0; i < TICKETLENGTH - tickets.length(); i++) {
          padded += "0";
        }
        return padded;
      }

    //Padding for Credit
    private String paddCredit(float credit) {
        String padded = String.format("%.2f", credit);
        if (padded.length() == CREDITLENGTH) {
          return padded;
        }
        for (int i = 0; i < CREDITLENGTH - padded.length() + 2; i++) {
          padded = "0" + padded;
        }
        return padded;
      }

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

    // boolean write(String filename) {
    //     try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
    //         for (Ticket t : ticketList) {
    //             // EEEEEEEEEEEEEEEEEEEEEEEEE SSSSSSSSSSSSSSS TTT PPPPPP
    //             pw.println(t.getEvent() + '\t' + t.getSellerName() + '\t' + t.getTicketsAvailable() + '\t' + t.getPrice());
    //         }
    //         pw.println("END");
    //         pw.flush();
    //         return true;

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return false;
    // }

    //Writes to the userlist
    boolean write(String filename) {
        try {
          FileWriter fw = new FileWriter(filename, false); // false = overwrites
          BufferedWriter bw = new BufferedWriter(fw);
            for (Ticket t : ticketList) {
                //String line = user.getuserName() + " " + user.getType() + " " + user.getCredit() + "\n";
              String line = paddEvent(t.getEvent()) + " " + paddUser(t.getSellerName()) + " " + paddTicket(t.getTicketsAvailable()) + " " + paddCredit((float) t.getPrice()) + "\n";
              bw.write(line);
            }
              bw.write("END");
              bw.close();
              return true;
            } catch (IOException e) {
              e.printStackTrace();
              return false;
        }
      }

}