package co.uk.nursetoday.Model;

public class User_Agency extends Users {

    //private String agency_name;//   fname, email, password;
    String id;

    //create a constructor to save the data to database as objects


    public User_Agency(String username, String fname, String lname, String email, String password) {
        super(username, fname, lname, email, password);
    }

    public User_Agency(){
        super();
    }

   /* public User_Agency(String agency_name, String fname, String email, String password) {
        this.agency_name = agency_name;
       // this.fname = fname;
        //this.email = email;
        //this.password = password;
        //this.id = id;
    }

    //build a second empty default constructor to read the database
   @Override
    public User_Agency() {
    } */


   // public User_Agency(){
    //}

    //getters and setters
  /*  public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
      this.agency_name = agency_name;
    }
*/
   /* public String getFirst_name() {
        return fname;
    }

    public void setFirst_name(String agency_name) {
        this.fname = fname;
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

    public void setPassword(String pw) {
        this.password = password;
    } */
}
