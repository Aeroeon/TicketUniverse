import java.io.*;
import java.util.*;

public class User {
  private String userName;
  private String userType;
  private float credit;

  // first constructor
  public User(String userName, String userType, float credit) {
    this.userName = userName;
    this.userType = userType;
    this.credit = credit;
  }

  public String getuserName() {
    return this.userName;
  }

  public String getType() {
    return this.userType;
  }

  public float getCredit() {
    return this.credit;
  }

  public void setuserName(String userName) {
    this.userName = userName;
  }

  public void setuserType(String userType) {
    this.userType = userType;
  }

  public void setCredit(float credit) {
    this.credit = credit;
  }

}
