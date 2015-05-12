
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
    
    public void closeConnection(){
        db.closeConnection();
    }
    
    /*
    *   Checks the user login information, based on the passed parameters
    *   Returns all of the user information, used to create the current_user
    */
    public ArrayList<String> checkLogin(String username, char[] password){
        Statement query;
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
            String sql = "INSERT INTO log VALUES ('" + eid + "', '" + text + "', '" + new Timestamp(System.currentTimeMillis()) + "')";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //db.updateUserAddress(eid, address);
    public boolean updateUserAddress(String eid, String address){
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Users SET address='" + address + "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //db.getUserAddress(id);
    public String getUserAddress(String eid){
        try{
            String toReturn = "";
            Statement query = connection.createStatement();
            String sql = "SELECT address FROM users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            while(results.next())
                toReturn = results.getString("address");
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
        
    }
    
    //db.getUserPassword(eid);
    public String getUserPassword(String eid){
        try{
            String toReturn = "";
            Statement query = connection.createStatement();
            String sql = "SELECT password FROM Users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            while(results.next())
                toReturn = results.getString("password");
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
        
    }
    
     //db.getUserPassword(eid);
    public String getUserUsername(String eid){
        try{
            String toReturn = "";
            Statement query = connection.createStatement();
            String sql = "SELECT username FROM Users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            while(results.next())
                toReturn = results.getString("username");
            query.close();
            return toReturn;
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
            query.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
    //db.GetUserName(eid);
    public String getUserName(String eid){
         try{
            String toReturn = "";
            Statement query = connection.createStatement();
            String sql = "SELECT firstname, lastname FROM Users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            while(results.next())
                toReturn = results.getString("firstname") + " " + results.getString("lastname");
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
    }
    
    //db.updateUserInfo(eid, username, password, fName, lName, address); 
    public boolean updateUserInfo(String eid, String username, String password, String firstname, String lastname, String address){
        
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Users SET "
                    + "username='" + username + 
                    "', password='" + password + 
                    "', firstname='" + firstname + 
                    "', lastname='" + lastname + 
                    "', address='" + address + 
                    "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }
    
    //db.addNewEmployee(eid, fName, lName, address, etc...);  
    public boolean addNewUser(String eid, String username, String password, String firstname, String lastname, String address, int userType){
        
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO Users VALUES ('" + 
                    eid + "','" + 
                    username + "','" + 
                    password + "','" + 
                    firstname + "','" + 
                    lastname + "','" + 
                    address + "'," + 
                    userType + ")";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }
    
    //db.deleteEmployee(id); 
    public boolean deleteUser(String id){
         try{
            Statement query = connection.createStatement();
            String sql = "DELETE FROM Users WHERE eid='" + id + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }
    
    
    public ArrayList<String> getEmployees(){
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT eid, firstname, lastname, address FROM Users WHERE usertype=1";
            
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn.add(set.getString("eid"));
                toReturn.add(set.getString("firstname"));
                toReturn.add(set.getString("lastname"));
                toReturn.add(set.getString("address"));
            }
            set.close();
            query.close();
            return toReturn;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<String> getUserLog(String id){
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT action, timestamp FROM log WHERE eid='" + id + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn.add(set.getString("action"));
                toReturn.add(set.getString("timestamp"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    //db.deleteLesson(lesson_id); 
    public boolean deleteLesson(String lesson_id){
        
        try{
            Statement query = connection.createStatement();
            String sql = "DELETE FROM lesson WHERE lesson_id='" + lesson_id + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
        
    }
    
   public ArrayList<String> getUserReport(String id){
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT * FROM history WHERE eid='" + id + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn.add(set.getString("LESSON_ID"));
                toReturn.add(set.getString("SCORE"));
                toReturn.add(set.getString("TIME_STARTED"));
                toReturn.add(set.getString("TIME_FINISHED"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<String> getLessons(){
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT LESSON_ID, LESSON_NAME FROM lesson";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn.add(set.getString("LESSON_ID"));
                toReturn.add(set.getString("LESSON_NAME"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
   
    public String getLessonName(String lesson_id){
       String toReturn = "";
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT LESSON_NAME FROM lesson where LESSON_ID='" + lesson_id + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn = set.getString("LESSON_NAME");               
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }
    
    public String getLessonText(String lesson_id){
        String toReturn = "";
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT LESSON_TEXT FROM lesson where LESSON_ID='" + lesson_id + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn = set.getString("LESSON_TEXT");               
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }
   
    /* Placeholders currently used in GUI */
    
    
    
    
    
    
    
    
   
    
    
     
    
       
    //db.updateLessonInfo(lessonName, goal1, goal2, goal3, etc...); 
    //db.addNewLesson(lessonName, goal1, goal2, goal3, etc...); 
    
    
    //db.getGoalSimVar1(goal_id);
    //db.getGoalSimVar2(goal_id);
    //db.getGoalSimVar3(goal_id);
    //db.getGoalSimVarValue1(goal_id);
    //db.getGoalSimVarValue2(goal_id);
    //db.getGoalSimVarValue3(goal_id);
    
    
    
//    db.updateGoalInfo(goalName, goalType, simVar1, simVarValue1, 
//                        simVar2, simVarValue2, simVar3, simVarValue3,
//                        goalDescription, goalText);
    
//    db.addNewGoal(goalName, goalType, simVar1, simVarValue1, 
//                        simVar2, simVarValue2, simVar3, simVarValue3,
//                        goalDescription, goalText); 
    
    //db.deleteGoal(goal_id); 

   
    
    
}
