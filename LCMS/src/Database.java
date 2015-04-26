
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
                toReturn.add(results.getString("usertype"));
                toReturn.add(results.getString("eid"));
                toReturn.add(results.getString("username"));
                toReturn.add(results.getString("password"));
                toReturn.add(results.getString("firstname"));
                toReturn.add(results.getString("lastname"));
                toReturn.add(results.getString("address"));
                toReturn.add(results.getString("log"));                
            }
            results.close();
            query.close();
                        
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        
       return toReturn;
    }
    
    //db.updateLog(id, "Signed In", time);
    public boolean updateLog(String eid, String text, long time){
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT log FROM Users WHERE eid='" + eid + "'";
            ResultSet results = query.executeQuery(sql);
            if(!results.first())
                return false;
            String oldLog = results.getString("log");
            String newLog = oldLog + '\n';
            newLog += time + " " + text;
            
            sql = "UPDATE Users SET log='" + newLog + "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
    /* Placeholders currently used in GUI */
    
    
    
    //db.updateUserAddress(address);
    //db.getUserAddress(id);
    //db.getUserPassword(id);
    //db.setUserPassword(id, password);
    
    //db.GetEmployeeName(id);
    //db.updateEmployeeInfo(id, fName, lName, address, etc...); 
    //db.addNewEmployee(id, fName, lName, address, etc...);  
    //db.deleteEmployee(id);  
    
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
