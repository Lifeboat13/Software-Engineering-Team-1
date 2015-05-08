
public class Auditor extends User{
   public Auditor(String eid, String username, String password, String firstname, String lastname, String address){
        this.setEID(eid);
        this.setUsername(username);
        this.setPassword(password);
        this.setfName(firstname);
        this.setlName(lastname);
        this.setAddress(address);
        this.setUserType("3");
        
    }
}
