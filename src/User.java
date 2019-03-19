import java.io.*;
import java.util.*;

public class User {
  private String userName;
  private String userType;
  private double credit;

//first constructor
  public User(String userName, String userType, double credit) {
      userName = userName;
      userType = userType;
      credit = credit;
    }

  public String getuserName() {
    return this.userName;
  }

  public String getType() {
    return this.userType;
  }

  public double getCredit() {
    return this.credit;
  }

  public void setuserName(String userName) {
      this.userName = userName;
  }

  public void setuserType(String userType) {
      this.userType = userType;
  }

  public void setCredit(double credit) {
      this.credit = credit;
  }


}
