import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

public class UserTests {

    @Test
    public void noArgsConstructorTest() {
        User u = new User("mrbenson", "AA", 456767.09f);
        assertEquals(u.getuserName(), "");
        assertEquals(u.getType(), "");
        assertEquals(u.getCredit(), 456767.09f);
    }

    @Test
    public void simpleConstructorTest() {
        User u = new User("mrbenson", "AA", 456767.09f);
        assertEquals(u.getuserName(), "mrbenson");
        assertEquals(u.getType(), "AA");
        assertEquals(u.getCredit(), 456767.09f);
    }

    @Test
    public void constructorTest() {
        User u = new User("mrbenson", "AA", 456767.09f);
        assertEquals(u.getuserName(), "mrbenson");
        assertEquals(u.getType(), "AA");
        assertEquals(u.getCredit(), 456767.09f);
    }


    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserTests.class);
    }

}