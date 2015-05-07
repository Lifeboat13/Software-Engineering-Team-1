
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Database {
    private DatabaseConnector db;
    private Connection connection;
    //Connection connection;    
    
    public Database(){
        db = new DatabaseConnector();        
        try{
            connection = db.getConnection();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
        
    /** 
     * Query wrapper methods 
     */
    
    /*
    *   Checks the user login information, based on the passed parameters
    *   Returns all of the user information, used to create the current_user
    */
    public ArrayList<String> checkLogin(String username, char[] password){
        Statement query = null;
        ArrayList<String> toReturn = new ArrayList<>();
        
        try{
            query = connection.createStatement();
            String sql = "SELECT * FROM Users WHERE username='" + username + "' AND password='" + String.valueOf(password) + "'";
            ResultSet results = query.executeQuery(sql);
           
            while(results.next()){
                
                toReturn.add(results.getString("eid"));
                toReturn.add(results.getString("username"));
                toReturn.add(results.getString("password"));
                toReturn.add(results.getString("firstname"));
                toReturn.add(results.getString("lastname"));
                toReturn.add(results.getString("address"));
                toReturn.add(results.getString("usertype"));              
            }
            results.close();
            query.close();
                        
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        
       return toReturn;
    }
    
    //db.updateLog(id, "Signed In", time);
    public boolean updateLog(String eid, String text){
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO log VALUES (eid='" + eid + "', action='" + text + "', timestamp='" + new Timestamp(System.currentTimeMillis()) + "')";
            query.executeUpdate(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
    //db.updateUserAddress(eid, address);
    public boolean updateUserAddress(String eid, String address){
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Users SET address='" + address + "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
    //db.getUserAddress(id);
    public String getUserAdress(String eid){
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT address FROM Users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            return results.getString("address");
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
        
    }
    
    //db.getUserPassword(eid);
    public String getUserPassword(String eid){
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT password FROM Users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            return results.getString("password");
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
        
    }
    
    //db.setUserPassword(eid, password);
    public boolean updateUserPassword(String eid, String password){
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Users SET password='" + password + "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
    //db.GetEmployeeName(eid);
    public String getEmployeeName(String eid){
         try{
            Statement query = connection.createStatement();
            String sql = "SELECT firstname FROM Users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            return results.getString("firstname");
        }catch(SQLException e){
            System.out.print("GetEmployeeName is broken");
            e.printStackTrace();
        }
        
        return "";
    }
    
    //db.updateEmployeeInfo(eid, fName, lName, address); 
    public boolean updateEmployeeInfo(String eid, String firstname, String lastname, String address){
        
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Users SET firstname='" + firstname + "', lastname='" + lastname + "', address='" + address + "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return true;
    }
    
    //db.addNewEmployee(eid, fName, lName, address, etc...);  
    public boolean addNewEmployee(String eid, String username, String password, String firstname, String lastname, String address){
        
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO Users (eid, username, password, firstname, lastname, address, usertype) VALUES ('" 
                    + eid + "','" + username + "','" + password + "','" + firstname + "','" + lastname + "','" + address + "'," + 1 + ")";
            query.executeUpdate(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return true;
    }
    
    //db.deleteEmployee(id); 
    public boolean deleteEmployee(String id){
         try{
            Statement query = connection.createStatement();
            String sql = "DELETE FROM Users WHERE eid='" + id + "'";
            query.executeUpdate(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return true;
    }
    
    
    public ResultSet getEmployees(){
        
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT firstname, lastname, address FROM Users";
            return query.executeQuery(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultSet getEmployeeLog(String id){
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT action, timestamp FROM log WHERE eid='" + id + "'";
            return query.executeQuery(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    /* Placeholders currently used in GUI */
    
    
    
    
    
    
    
    
   
    
    
     
    
    //db.getLessonName(lesson_id);    
    //db.updateLessonInfo(lessonName, goal1, goal2, goal3, etc...); 
    //db.addNewLesson(lessonName, goal1, goal2, goal3, etc...); 
    //db.deleteLesson(lesson_id); 
    
    //db.getGoalSimVar1(goal_id);
    //db.getGoalSimVar2(goal_id);
    //db.getGoalSimVar3(goal_id);
    //db.getGoalSimVarValue1(goal_id);
    //db.getGoalSimVarValue2(goal_id);
    //db.getGoalSimVarValue3(goal_id);
    
    //db.getGoalDescription(goal_id);
    //db.getGoalText(goal_id);
    
//    db.updateGoalInfo(goalName, goalType, simVar1, simVarValue1, 
//                        simVar2, simVarValue2, simVar3, simVarValue3,
//                        goalDescription, goalText);
    
//    db.addNewGoal(goalName, goalType, simVar1, simVarValue1, 
//                        simVar2, simVarValue2, simVar3, simVarValue3,
//                        goalDescription, goalText); 
    
    //db.deleteGoal(goal_id); 
    
    
}
