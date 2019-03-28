import java.io.*;
import java.util.ArrayList;

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
    private String line;
    private ArrayList<String> buyTransactions;

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

        if (transactionType.equals("01")) {
            User user = new User(username, userType, credit);
            userHandler.add(user);
        } else if (transactionType.equals("02")) {
            User user = userHandler.find(username);
            userHandler.delete(user);
        } else if (transactionType.equals("06")) {
            User user = userHandler.find(username);
            user.setCredit(user.getCredit() + credit);
        } else if (transactionType.equals("00")) {
            for (String buyTransaction : buyTransactions) {
                parseBuyTransaction(buyTransaction, username);
            }
            buyTransactions.clear();
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

        if (transactionType.equals("03")) {
            if (userHandler.find(sellerName) == null) {
                System.out.println("ERROR: Seller does not exist");
                System.out.println("Transaction: " + line);
                return;
            } else if (ticketHandler.find(eventName, sellerName) != null) {
                System.out.println("ERROR: Ticket already exists");
                System.out.println("Transaction: " + line);
            }

            Ticket newTicket = new Ticket(eventName, sellerName, tickets, price);
            ticketHandler.add(newTicket);
                
        } else if (transactionType.equals("04")) {
            buyTransactions.add(line);
        } else {
            System.out.println("ERROR: Unrecognized transaction code: " + transactionType);
            System.out.println("Transaction: " + line);
            System.exit(1);
        }
    }

    private void parseBuyTransaction(String line, String buyerName) {
        String eventName = removeTrailingSpaces(line.substring(3, 28));
        String sellerName = removeTrailingSpaces(line.substring(29, 44));
        int tickets = Integer.parseInt(line.substring(45, 48));
        float price = Float.parseFloat(line.substring(49, 55));

        Ticket ticket = ticketHandler.find(eventName, sellerName);
        User seller = userHandler.find(sellerName);
        User buyer = userHandler.find(buyerName);

        float totalPrice = tickets * price;

        if (ticket == null) {
            System.out.println("ERROR: Ticket does not exist");
            System.out.println("Transaction: " + line);
            return;
        } else if (seller == null) {
            System.out.println("ERROR: Seller does not exist");
            System.out.println("Transaction: " + line);
            return;
        } else if (buyer == null) {
            System.out.println("ERROR: Buyer does not exist");
            System.out.println("Transaction: " + line);
            return;
        } else if (ticket.getTicketsAvailable() < tickets) {
            System.out.println("ERROR: Not enough tickets available");
            System.out.println("Transaction: " + line);
            return;
        } else if (totalPrice > buyer.getCredit()) {
            System.out.println("ERROR: Buyer cannot afford this transaction");
            System.out.println("Transaction: " + line);
            return;
        } else if (seller.getCredit() + totalPrice > 999999.99f) {
            System.out.println("ERROR: Seller has reached account credit limit");
            System.out.println("Transaction: " + line);
            return;
        }
        
        ticket.setTicketsAvailable(ticket.getTicketsAvailable() - tickets);
        buyer.setCredit(buyer.getCredit() - totalPrice);
        seller.setCredit(seller.getCredit() + totalPrice);

        if (ticket.getTicketsAvailable() <= 0) {
            ticketHandler.delete(ticket);
        }
    }

    private void parseRefundTransaction(String line) {
        String buyerName = removeTrailingSpaces(line.substring(3, 18));
        String sellerName = removeTrailingSpaces(line.substring(19, 34));
        float price = Float.parseFloat(line.substring(35, 44));

        User buyer = userHandler.find(buyerName);
        User seller = userHandler.find(sellerName);

        if (seller.getCredit() < price) {
            price = seller.getCredit();
            System.out.println("ERROR: Seller cannot afford refund");
            System.out.println("Transaction: " + line);
            return;
        }
        if (buyer.getCredit() + price > 999999.99f) {
            price = 999999.99f - buyer.getCredit();
            System.out.println("ERROR: Buyer cannot accept refund, account full");
            System.out.println("Transaction: " + line);
            return;
        }

        buyer.setCredit(buyer.getCredit() + price);
        seller.setCredit(seller.getCredit() - price);
    }

    //Initialize userHandler and ticketHandler
    public Processor(String usersFilename, String ticketsFilename) {
       userHandler = new UserHandler(usersFilename);
       ticketHandler = new TicketHandler(ticketsFilename);
       buyTransactions = new ArrayList<String>();
    }

    // Read transactions file and make changes to users and tickets as needed
    public void processTransactions(String filename, String newUsersFilename, String newTicketsFilename) {
        try (BufferedReader transactionsReader = new BufferedReader(new FileReader(filename))) {
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
                    System.out.println("Transaction: " + line);
                    System.exit(1);
                }
            }

            transactionsReader.close();
            ticketHandler.write(newTicketsFilename);
            userHandler.write(newUsersFilename);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not read " + filename);
            System.exit(1);
        }
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    public TicketHandler getTicketHandler() {
        return ticketHandler;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Format to run program is users tickets transactions newusers newtickets");
        }

        Processor processor = new Processor(args[0], args[1]);
        
        processor.processTransactions(args[2], args[3], args[4]);
    }
}
