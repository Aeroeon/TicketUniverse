import java.util.*;
import java.io.*;

public class UserHandler {
  private ArrayList<User> userList;
  private String usersFilename;
  private final int NAMELENGTH = 15;
  private final int TYPELENGTH = 2;
  private final int CREDITLENGTH = 9;

    public UserHandler(String usersFilename) {
        this.usersFilename = usersFilename;
        userList = new ArrayList<User>();

        try {
          FileReader fr = new FileReader(usersFilename);
          BufferedReader br = new BufferedReader(fr);
          String contentLine;
          while (((contentLine = br.readLine()) != null) && !contentLine.trim().equals("END")) {
            int offset = NAMELENGTH;
            String userName = contentLine.substring(0, offset++);
            String userType = contentLine.substring(offset, offset + TYPELENGTH);
            offset += TYPELENGTH + 1;
            float credit = Float.parseFloat(contentLine.substring(offset));
              User user = new User(userName, userType, credit);
              userList.add(user);
          } br.close();
        } catch(IOException e) {
              e.printStackTrace();
        }
    }

    //For debugging purposes only
    public static void main(String[] args) {
      User user = new User("hello", "TT", (float)123.45);
      UserHandler handler = new UserHandler("test.txt");
      handler.add(user);
      handler.write();
    }

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

    //Writes to the userlist
    public void write() {
      try {
        FileWriter fw = new FileWriter(usersFilename, false); // false = overwrites
        BufferedWriter bw = new BufferedWriter(fw);
          for (User user : userList) {
              //String line = user.getuserName() + " " + user.getType() + " " + user.getCredit() + "\n";
            String line = paddUser(user.getuserName()) + " " + user.getType() + " " + paddCredit(user.getCredit()) + "\n";
            bw.write(line);
          }
            bw.write("END                         \n");
            bw.close();
          } catch (IOException e) {
            e.printStackTrace();
      }
    }

    //This function would add a user and update the userList
    public void add(User t) {
      for (User user : userList) {
          if (user == t) {
            System.out.println("Error: User already exists.");
            return;
          }
      }
        userList.add(t);
    }


    //This function deletes users from the userList
    public void delete(User t) {
      for (int i = 0; i < userList.size(); i++) {
        if (t.getuserName().equals(userList.get(i).getuserName())) {
          userList.remove(i);
          return;
        }
      }
    }


}
