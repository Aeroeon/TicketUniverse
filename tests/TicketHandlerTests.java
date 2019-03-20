import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class TicketHandlerTests {

    @Test
    public void findTicketTest() {
        TicketHandler tHandler = new TicketHandler();
        tHandler.add(new Ticket("Toronto Maple Leafs vs. New York Rangers Ticketmaster    107    250.80"));
        assertNotNull(tHandler.find("Toronto Maple Leafs vs. New York Rangers", "Ticketmaster"));
    }

    @Test
    public void addTicketTest() {
        TicketHandler tHandler = new TicketHandler();
        assertTrue(tHandler.add(new Ticket("Toronto Maple Leafs vs. New York Rangers Ticketmaster    107    250.80")));
    }

    @Test
    public void deleteTicketTest() {
        TicketHandler tHandler = new TicketHandler();
        Ticket t = new Ticket("Toronto Maple Leafs vs. New York Rangers Ticketmaster    107    250.80");
        tHandler.add(t);
        assertTrue(tHandler.delete(t));
    }

    @Test
    public void writeTest() {
        TicketHandler tHandler = new TicketHandler();
        tHandler.add(new Ticket("Toronto Maple Leafs vs. New York Rangers Ticketmaster    107    250.80"));
        assertTrue(tHandler.write("ticket_out.txt"));
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TicketHandlerTests.class);
    }
}