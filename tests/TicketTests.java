import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class TicketTests {

    @Test
    public void noArgsConstructorTest() {
        Ticket t = new Ticket();

        assertEquals(t.getEvent(), "");
        assertEquals(t.getSellerName(), "");
        assertEquals(t.getTicketsAvailable(), 0);
        assertEquals(t.getPrice(), 0.0);
    }

    @Test
    public void simpleConstructorTest() {
        Ticket t = new Ticket("Toronto Maple Leafs vs. New York Rangers", "Ticketmaster", 107, 250.80);

        assertEquals(t.getEvent(), "Toronto Maple Leafs vs. New York Rangers");
        assertEquals(t.getSellerName(), "Ticketmaster");
        assertEquals(t.getTicketsAvailable(), 107);
        assertEquals(t.getPrice(), 250.80);
    }

    @Test
    public void constructorTest() {
        Ticket t = new Ticket("Toronto Maple Leafs vs. New York Rangers Ticketmaster    107    250.80");

        assertEquals(t.getEvent(), "Toronto Maple Leafs vs. New York Rangers");
        assertEquals(t.getSellerName(), "Ticketmaster");
        assertEquals(t.getTicketsAvailable(), 107);
        assertEquals(t.getPrice(), 250.80);
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TicketTests.class);
    }
}