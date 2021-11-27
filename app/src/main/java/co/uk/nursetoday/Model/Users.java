package co.uk.nursetoday.Model;

public abstract class Users {

  private   String username, fname, lname, email, password;


  public Users(String username, String fname, String lname, String email, String password) {
   this.username = username;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.password = password;
  }

  public Users(){
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirst_name() {
    return fname;
  }

  public void setFirst_name(String fname) {
    this.fname = fname;
  }

  public String getLast_name() {
    return lname;
  }

  public void setLast_name(String lname) {
    this.lname = lname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
