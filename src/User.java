import java.io.*;
import java.util.*;

public class User {
  private String username;
  private String userType;
  private double credit;

  // first constructor
  public User(String username, String userType, double credit) {
    this.username = username;
    this.userType = userType;
    this.credit = credit;
  }

  public String getUsername() {
    return this.username;
  }

  public String getType() {
    return this.userType;
  }

  public double getCredit() {
    return this.credit;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setuserType(String userType) {
    this.userType = userType;
  }

  public void setCredit(double credit) {
    this.credit = credit;
  }

}

