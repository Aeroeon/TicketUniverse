import java.util.*;

public class UserHandler {
  private ArrayList<Users> userList;

    public UserHandler(String usersFilename) {
      UserList = new ArrayList<Users>();
    }

    //this function will check user existance. if they exist,
    //then return true, if not then return false. this function
    //will be very useful for classes extending this class.
    public static boolean find(String userName){
      try {
        FileReader fileReader = new FileReader("users.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
      	String line;
          while ((line = bufferedReader.readLine()) != null) {
          String[] sepLine = line.split("\n");
            if(sepLine[0].equals(userName)) {
              return true;
              break;
            }

            else if {
              return false;
            }

          }
            fileReader.close();
          }
            catch (IOException e) {
              e.printStackTrace();
            }

    //this function will create a user issued by admin
    //and update the user file
    public static void add(User t) {
      //need to check existance of a user being created
      //another admin might have created same username
        find(userName);
        System.out.println("Add User handler");
        usersList.add(t);
      }

    public static void delete(User t) {
      //need to check existance of a user being deleted
      //another admin might have deleted it.
        find(userName);
        usersList.remove(t);
    }

    public static void write(String usersFilename) {
    }

}
