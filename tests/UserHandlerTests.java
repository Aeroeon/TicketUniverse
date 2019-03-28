import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import java.util.ArrayList;


public class UserHandlerTests {

    @Test
    public void findUserTest() {
        UserHandler uHandler = new UserHandler("mrbenson      AA 456767.09");
        uHandler.add(new User("mrbenson","AA", 456767.09f));
        assertNotNull(uHandler.find("mrbenson"));
    }

    @Test
    public void addUserTest() {
        UserHandler uHandler = new UserHandler("users.txt");
        User u = new User("mrbenson", "AA", 456767.09f);
        uHandler.add(u);

        ArrayList<User> expected = new ArrayList<>();
        expected.add(u);

        assertEquals(uHandler.getList(), expected);
    }

    @Test
    public void deleteUserTest() {
    UserHandler uHandler = new UserHandler("users.txt");
    User u = new User("mrbenson", "AA", 45676.09f);
    User u2 = new User("potato", "FS", 12.3f);
    uHandler.add(u);
    uHandler.add(u2);

    uHandler.delete(u);
    ArrayList<User> expected = new ArrayList<>();
    expected.add(u2);

    assertEquals(uHandler.getList(), expected );
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserHandlerTests.class);
    }

}