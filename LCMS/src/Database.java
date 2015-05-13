
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
    public boolean addNewUser(String username, String password, String firstname, String lastname, String address, int userType){
        
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO Users VALUES ('" + 
                    getNextAvailableID("Employee") + "','" + 
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
            String sql = "DELETE FROM lessons WHERE lesson_id='" + lesson_id + "'";
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
   
    public ArrayList<String> getGoals() {
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT * FROM goals";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn.add(set.getString("GOAL_ID"));
                toReturn.add(set.getString("GOAL_NAME"));
                toReturn.add(set.getString("TYPE"));
                toReturn.add(set.getString("DESCRIPTION"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<String> getGoalsByType(String type) {
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT GOAL_ID, GOAL_NAME FROM goals WHERE type = '" + type + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn.add(set.getString("GOAL_ID"));
                toReturn.add(set.getString("GOAL_NAME"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
     public ArrayList<String> getGoalsByLesson(String lesson_id) {
     
          ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT * FROM lessons WHERE LESSON_ID = '" + lesson_id + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
               
                toReturn.add(set.getString("GOAL1_ID"));
                toReturn.add(set.getString("GOAL2_ID"));
                toReturn.add(set.getString("GOAL3_ID"));
                        
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
         
       
    }
     
             
    public String getGoalText(String lesson_id, String goalNumber) {
     
          String toReturn = "";
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT FULL_TEXT FROM lessons, goals WHERE lessons.LESSON_ID = '" + lesson_id
                    + "' and lessons.GOAL" + goalNumber + "_ID = goals.GOAL_ID";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
               
                toReturn = set.getString("FULL_TEXT");
                
                        
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
         
       
     }
    
     public String getDescriptionByGoalID(String goal_id) {
     
          String toReturn = "";
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT DESCRIPTION FROM goals WHERE GOAL_ID = '" + goal_id + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
               
                toReturn = set.getString("DESCRIPTION");
                
                        
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
         
       
     }
     
     public String getFullTextByGoalID(String goal_id) {
     
          String toReturn = "";
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT FULL_TEXT FROM goals WHERE GOAL_ID = '" + goal_id + "'";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
               
                toReturn = set.getString("FULL_TEXT");
                
                        
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
         
       
     }
     
     
    public ArrayList<String> getLessons() {
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT * FROM lessons";
            //String sql = "SELECT LESSON_ID, LESSON_NAME, GOAL_NAME as GOAL1_NAME, GOAL_NAME as GOAL2_NAME FROM lessons, goals WHERE GOAL1_ID = goals.GOAL_ID ";
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
                toReturn.add(set.getString("LESSON_ID"));
                toReturn.add(set.getString("LESSON_NAME"));
                toReturn.add(set.getString("GOAL1_ID"));
                toReturn.add(set.getString("GOAL2_ID"));
                toReturn.add(set.getString("GOAL3_ID"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    /* 
    This is literally the most inefficient way to do this, but I don't know SQL
    well enough to make it better (clearly shouldn't be making a query for every 
    single lesson
    */
    public ArrayList<String> getEmployeeLessons(String eid) {
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            Statement innerQuery = connection.createStatement();
            String sql = "SELECT LESSON_ID, LESSON_NAME FROM Lessons";
            String subquery = "";
            ResultSet set = query.executeQuery(sql);
            ResultSet subset;
            while(set.next()){
                String lesson_id = set.getString("LESSON_ID");
                toReturn.add(lesson_id);
                toReturn.add(set.getString("LESSON_NAME"));
                subquery = "SELECT SCORE FROM history WHERE LESSON_ID='" + lesson_id + "' AND eid='" + eid + "'";
                subset = innerQuery.executeQuery(subquery);
                if(subset.next()){
                    toReturn.add(subset.getString("SCORE"));
                }else{
                    toReturn.add("Not Taken");
                }
                
            }
            set.close();
            
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
   
    public String getLessonName(String lesson_id) {
       String toReturn = "";
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT LESSON_NAME FROM lessons where LESSON_ID='" + lesson_id + "'";
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
            String sql = "SELECT LESSON_TEXT FROM lessons where LESSON_ID='" + lesson_id + "'";
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
   
    //db.updateLessonInfo(lessonName, goal1, goal2, goal3, etc...); 
    public boolean updateLessonInfo(String lessonID, String lessonName, String goal1_id, String goal2_id, String goal3_id, String lessonText){
        
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Lessons SET "
                    + "LESSON_NAME='" + lessonName + 
                    "', GOAL1_ID='" + goal1_id + 
                    "', GOAL2_ID='" + goal2_id + 
                    "', GOAL3_ID='" + goal3_id + 
                    "', LESSON_TEXT='" + lessonText + 
                    "' WHERE LESSON_ID='" + lessonID + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;    
        
    }
    
    public boolean addNewLesson(String lessonID, String lessonName, String goal1_id, String goal2_id, String goal3_id, String lessonText){
        System.out.println(getNextAvailableID("Lesson"));
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO Lessons VALUES ('" + 
                    lessonID + "', '" + 
                    lessonName + "', '" + 
                    goal1_id + "', '" + 
                    goal2_id + "', '" + 
                    goal3_id + "', '" + 
                    lessonText + "')"; 
                    
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }
    
    public String getNextAvailableID(String table){
        
        String toReturn = "";
        
        if(table.equals("Employee")){
            try{
            Statement query = connection.createStatement();
            
            String sql = "Select eid from Users ORDER BY eid * 1 ASC";
            ResultSet result = query.executeQuery(sql);
            while(result.next()){
                String r = result.getString("eid");
                int t = Integer.parseInt(r);
                t++;
                toReturn = "" + t;
            }
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        }else if(table.equals("Lesson")){
            
            try{
            Statement query = connection.createStatement();
            //String sql = "Select MAX(LESSON_ID) as total from lessons";
            String sql = "Select LESSON_ID from lessons ORDER BY LESSON_ID * 1 ASC";
            ResultSet result = query.executeQuery(sql);
            while(result.next()){
                String r = result.getString("LESSON_ID");
                int t = Integer.parseInt(r);
                t++;
                toReturn = "" + t;
            }
            query.close();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
           
        return toReturn;
            
            
        }
        
        
        
        
        return toReturn;
        
        
        
    }
    
    public boolean takeLesson(String lesson_ID, double score, String eid, String started, String finished){
        
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO history VALUES ('" + 
                    lesson_ID + "','" + 
                    score + "','" + 
                    eid + "','" + 
                    started + "','" + 
                    finished + "')";
            query.executeUpdate(sql);
            query.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<String> getSimVarNames() {
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT VAR_NAME FROM sim_vars";
            
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
             
                toReturn.add(set.getString("VAR_NAME"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<String> getSimVarIDs() {
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT VAR_ID FROM sim_vars";
            
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
             
                toReturn.add(set.getString("VAR_ID"));
            }
            set.close();
            query.close();
            return toReturn;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    /* Placeholders currently used in GUI */
    
    
  
      
    
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
