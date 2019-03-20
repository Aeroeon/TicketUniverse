import java.io.*;

/*
Storage classes - Ticket, User
Handler classes - UserHandler, TicketHandler

The UserHandler and TicketHandler classes read in their respective files and parses each line into a Ticket or User
storage class. These storage classes are put into a list where they can be used later. The handler classes are used
when the Processor class edits their storage lists based on the transaction file. Once the transaction file is fully
read and all edits are made the handler classes write out the edited storage lists back into a text format.

 */

// Processes the transactions file and edits the users and tickets files as needed in chronological order
public class Processor {
    private UserHandler userHandler;
    private TicketHandler ticketHandler;

    private String removeTrailingSpaces(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                return line.substring(0, i);
            }
        }

        System.out.println("ERROR: Trailing space removal failed");
        System.exit(1);
        return "";
    }

    private void parseAccountTransaction(String line) {
        String transactionType = line.substring(0, 2);
        String username = removeTrailingSpaces(line.substring(3, 18));
        String userType = line.substring(19, 21);
        float credit = Float.parseFloat(line.substring(22, 31));

        // System.out.println(transactionType + " " + username + " " + userType + " " + credit);

        if (transactionType.equals("01")) {
            User user = new User(username, userType, credit);
            userHandler.add(user);
            userHandler.write();
        } else if (transactionType.equals("02")) {
            // delete
        } else if (transactionType.equals("06")) {
            // addcredit
        } else if (transactionType.equals("00")) {
            // logout
        } else {
            System.out.println("ERROR: Unrecognized transaction code: " + transactionType);
            System.exit(1);
        }
    }

    private void parseTicketTransaction(String line) {
        String transactionType = line.substring(0, 2);
        String eventName = removeTrailingSpaces(line.substring(3, 28));
        String sellerName = removeTrailingSpaces(line.substring(29, 44));
        int tickets = Integer.parseInt(line.substring(45, 48));
        float price = Float.parseFloat(line.substring(49, 55));

       // System.out.println(transactionType + " " + eventName + " " + sellerName + " " + tickets + " " + price);

        if (transactionType.equals("03")) {
            if (ticketHandler.find(eventName, sellerName) == null) {
                Ticket newTicket = new Ticket(eventName, sellerName, tickets, price);
                ticketHandler.add(newTicket);
            } else {
                System.out.println("ERROR: Ticket already exists");
            }
        } else if (transactionType.equals("04")) {
            Ticket t = ticketHandler.find(eventName, sellerName);
            if (t == null) {
                System.out.println("ERROR: Ticket does not exist");
                return;
            }

            if (t.getTicketsAvailable() >= tickets) {
                t.setTicketsAvailable(t.getTicketsAvailable() - tickets);
            } else {
                System.out.println("ERROR: Not enough tickets available");
            }

        } else {
            System.out.println("ERROR: Unrecognized transaction code: " + transactionType);
            System.exit(1);
        }
    }

    private void parseRefundTransaction(String line) {
        String transactionType = line.substring(0, 2);
        String buyerName = removeTrailingSpaces(line.substring(3, 18));
        String sellerName = removeTrailingSpaces(line.substring(19, 34));
    }

    //Initialize userHandler and ticketHandler
    public Processor(String usersFilename, String ticketsFilename) {
       userHandler = new UserHandler(usersFilename);
       ticketHandler = new TicketHandler(ticketsFilename);
    }

    // Read transactions file and make changes to users and tickets as needed
    public void processTransactions(String filename) {
        try (BufferedReader transactionsReader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = transactionsReader.readLine()) != null) {
                if (line.length() == 55) {
                    parseTicketTransaction(line);
                } else if (line.length() == 44) {
                    parseRefundTransaction(line);
                } else if (line.length() == 31) {
                    parseAccountTransaction(line);
                } else if (line.length() == 2) {
                    break;
                } else {
                    System.out.println("ERROR: Line formatted incorrectly");
                    System.exit(1);
                }
            }

            ticketHandler.write("NewTickets.txt");
            userHandler.write();
            //userHandler.write("NewUsers.txt");
            transactionsReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not read " + filename);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Processor processor = new Processor("users.txt", "tickets.txt");
        
        processor.processTransactions("transactions.txt");
    }
}
