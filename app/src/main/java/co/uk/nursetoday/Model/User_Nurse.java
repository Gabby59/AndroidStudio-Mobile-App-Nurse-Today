package co.uk.nursetoday.Model;

public class User_Nurse extends Users {

  //  String lname; //fname, lname, email, password;


   // private   String fname, email, password,lname;

    public User_Nurse(String username, String fname, String lname, String email, String password) {
        super(username,fname, lname, email, password);
    }


    public User_Nurse(){
    }

    //create a constructor to save the data to database as objects

    //public User_Nurse(String fname, String lname, String email, String pw) {
        //this.fname = fname;
      //  this.lname = lname;
        //this.email = email;
        //this.password = password;
   // }
    //create an empty default constructor to read from teh database
  // @Override
    //public User_Nurse() {
    //}

    //getters and setters
/*
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
/*
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        this.password = password;
    } */
}
