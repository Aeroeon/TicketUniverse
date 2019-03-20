import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.nio.file.Paths;

public class ProcessorTests {
  String testFilesPath = "./tests/Processor Test Files/";

  private Processor createProcessor(String folderPath) {
      Processor processor = new Processor(folderPath + "users.txt", folderPath + "tickets.txt");
      processor.processTransactions(folderPath + "transactions.txt", folderPath + "newusers.txt", folderPath + "newusers.txt");
      return processor;
  }

  @Test
  // Test adding new user
  public void addUserTest() {
    String folderPath = testFilesPath + "AddUser/";
    Processor processor = createProcessor(folderPath);
    UserHandler userHandler = processor.getUserHandler();
    assertNotNull(userHandler.find("user"));
  }

  @Test
  // Test deleting user
  public void deleteUserTest() {
    String folderPath = testFilesPath + "DeleteUser/";
    Processor processor = createProcessor(folderPath);
    UserHandler userHandler = processor.getUserHandler();
    assertNull(userHandler.find("deleteme"));
  }

  @Test
  // Test adding credit to a user
  public void addCreditTest() {
    String folderPath = testFilesPath + "AddCredit/";
    Processor processor = createProcessor(folderPath);
    UserHandler userHandler = processor.getUserHandler();
    User user = userHandler.find("user");
    assertEquals(10.0f, user.getCredit(), 0.1f);
  }

  @Test
  // Test refunding a user
  public void refundTest() {
    String folderPath = testFilesPath + "Refund/";
    Processor processor = createProcessor(folderPath);
    UserHandler userHandler = processor.getUserHandler();
    User buyer = userHandler.find("buyer");
    User seller = userHandler.find("seller");
    assertEquals(999899.99f, seller.getCredit(), 0.1f);
    assertEquals(110.0f, buyer.getCredit(), 0.1f);
  }

  public static junit.framework.Test suite() {
    return new JUnit4TestAdapter(ProcessorTests.class);
  }
}