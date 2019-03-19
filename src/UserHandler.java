import java.util.*;
import java.io.*;

public class UserHandler {
  private ArrayList<User> userList;

  public UserHandler(String usersFilename) {
    userList = new ArrayList<>();
  }

  // this function will check user existance. if they exist,
  // then return true, if not then return false. this function
  // will be very useful for classes extending this class.
  public boolean find(String username) {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] sepLine = line.split("\n");
        if (sepLine[0].equals(username)) {
          return true;
        }

        else {
          return false;
        }

      }
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  // this function will create a user issued by admin
  // and update the user file
  public void add(User t) {
    // need to check existance of a user being created
    // another admin might have created same username
    find(t.getUsername());
    System.out.println("Add User handler");
    userList.add(t);
  }

  public void delete(User t) {
    // need to check existance of a user being deleted
    // another admin might have deleted it.
    find(t.getUsername());
    userList.remove(t);
  }

  public void write(String usersFilename) {
  }

}
