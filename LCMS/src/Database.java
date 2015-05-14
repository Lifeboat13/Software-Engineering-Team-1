
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Database {
    private DatabaseConnector db;
    private Connection connection;  
    
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
                        
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
       return toReturn;
    }
    
    public boolean updateLog(String eid, String text){
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO log VALUES ('" + eid + "', '" + text + "', '" + new Timestamp(System.currentTimeMillis()) + "')";
            query.executeUpdate(sql);
            query.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateUserAddress(String eid, String address){
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Users SET address='" + address + "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";   
    }
    
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return ""; 
    }
    
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return "";    
    }
    
    public boolean updateUserPassword(String eid, String password){
        try{
            Statement query = connection.createStatement();
            String sql = "UPDATE Users SET password='" + password + "' WHERE eid='" + eid + "'";
            query.executeUpdate(sql);
            query.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return "";
    }
    
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }
    
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }
    
    public boolean deleteUser(String id){
        try{
            Statement query = connection.createStatement();
            String sql = "DELETE FROM Users WHERE eid='" + id + "'";
            query.executeUpdate(sql);
            sql = "DELETE FROM history WHERE eid='" + id + "'";
            query.executeUpdate(sql);
            sql = "DELETE FROM log WHERE eid='" + id + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    public boolean deleteLesson(String lesson_id){
        
        try{
            Statement query = connection.createStatement();
            String sql = "DELETE FROM lessons WHERE lesson_id='" + lesson_id + "'";
            query.executeUpdate(sql);
            query.close();
            return true;
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
   
    public long getUserReportTotalTimeMilliSeconds(String id){
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT TIME_STARTED, TIME_FINISHED FROM history WHERE eid='" + id + "'";
            Timestamp finished;
            Timestamp started;
            long totalTime = 0;
            ResultSet set = query.executeQuery(sql);
            while(set.next()){                
                started = Timestamp.valueOf(set.getString("TIME_STARTED"));   
                finished = Timestamp.valueOf(set.getString("TIME_FINISHED"));
                totalTime += (finished.getTime() - started.getTime());
            }
            set.close();
            query.close();
            return totalTime;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return -1;
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
                subquery = "SELECT MAX(SCORE) AS SCORE FROM history WHERE LESSON_ID='" + lesson_id + "' AND eid='" + eid + "'";
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }
   
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;      
    }
    
    public boolean addNewLesson(String lessonName, String goal1_id, String goal2_id, String goal3_id, String lessonText){
        System.out.println(getNextAvailableID("Lesson"));
        try{
            Statement query = connection.createStatement();
            String sql = "INSERT INTO Lessons VALUES ('" + 
                    getNextAvailableID("Lesson") + "', '" + 
                    lessonName + "', '" + 
                    goal1_id + "', '" + 
                    goal2_id + "', '" + 
                    goal3_id + "', '" + 
                    lessonText + "')"; 
                    
            query.executeUpdate(sql);
            query.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }
    
    public String getNextAvailableID(String table){      
        String toReturn = "";

        if(table.equals("Employee")){
            try {
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
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else if(table.equals("Lesson")) {     
            try {
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
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
           
            return toReturn;     
        }
        else if(table.equals("Goal")){       
            try {
                Statement query = connection.createStatement();
                //String sql = "Select MAX(LESSON_ID) as total from lessons";
                String sql = "Select GOAL_ID from goals ORDER BY GOAL_ID * 1 ASC";
                ResultSet result = query.executeQuery(sql);
                while(result.next()){
                    String r = result.getString("GOAL_ID");
                    int t = Integer.parseInt(r);
                    t++;
                    toReturn = "" + t;
                }
                query.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }    
        }
 
        return toReturn;     
    }
    
    public boolean takeLesson(String lesson_ID, double score, String eid, String started, String finished){
        try{
    
            
            
            String sql = "INSERT INTO history VALUES (?,?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, lesson_ID);
            query.setDouble(2, score);
            query.setString(3, eid);
            query.setString(4, started);
            query.setString(5, finished);
            
            query.execute();
            query.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public ArrayList<String> getAllSimVarNames() {
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public ArrayList<String> getAllSimVarIDs() {
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    public ArrayList<String> getSimVarsByGoalID(String goal_id){
        
        ArrayList<String> toReturn = new ArrayList();
        try{
            
            String sql = "SELECT SIM_VAR_1, SIM_VAR_2, SIM_VAR_3 FROM goals WHERE GOAL_ID=?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, goal_id);
            ResultSet set = query.executeQuery();
            while(set.next()){
             
                toReturn.add(set.getString("SIM_VAR_1"));
                toReturn.add(set.getString("SIM_VAR_2"));
                toReturn.add(set.getString("SIM_VAR_3"));
            }
            set.close();
            query.close();
            return toReturn;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
     public ArrayList<String> getSimVarValsByGoalID(String goal_id){
        
        ArrayList<String> toReturn = new ArrayList();
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT SIM_VAR_1_VALUE, SIM_VAR_2_VALUE, SIM_VAR_3_VALUE FROM goals WHERE GOAL_ID='" + goal_id + "'";
            
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
             
                toReturn.add(set.getString("SIM_VAR_1_VALUE"));
                toReturn.add(set.getString("SIM_VAR_2_VALUE"));
                toReturn.add(set.getString("SIM_VAR_3_VALUE"));
            }
            set.close();
            query.close();
            return toReturn;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public String getGoalDescription(String goal_id){
        
        String toReturn =  "";
        try{
            Statement query = connection.createStatement();
            String sql = "SELECT DESCRIPTION FROM goals WHERE GOAL_ID='" + goal_id + "'";
            
            ResultSet set = query.executeQuery(sql);
            while(set.next()){
             
                toReturn = set.getString("DESCRIPTION");
               
            }
            set.close();
            query.close();
            return toReturn;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null; 
    }
     
     public String getGoalText(String goal_id){
        
        String toReturn =  "";
        try{
            String sql = "SELECT FULL_TEXT FROM goals WHERE GOAL_ID=?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, goal_id);
            
            ResultSet set = query.executeQuery();
            while(set.next()){
             
                toReturn = set.getString("FULL_TEXT");
               
            }
            set.close();
            query.close();
            return toReturn;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null; 
    }
     
     public String getGoalName(String goal_id){
        
        String toReturn =  "";
        try{
            
            String sql = "SELECT GOAL_NAME FROM goals WHERE GOAL_ID=?";
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, goal_id);
            ResultSet set = query.executeQuery();
            while(set.next()){
             
                toReturn = set.getString("GOAL_NAME");
               
            }
            set.close();
            query.close();
            return toReturn;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null; 
    }
     
     public String getGoalType(String goal_id){
        
        String toReturn =  "";
        try{
            
            String sql = "SELECT TYPE FROM goals WHERE GOAL_ID=?";
            PreparedStatement query = connection.prepareStatement(sql);
            
            query.setString(1, goal_id);
            
            ResultSet set = query.executeQuery();
            while(set.next()){
             
                toReturn = set.getString("TYPE");
               
            }
            set.close();
            query.close();
            return toReturn;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
     
     public boolean updateGoalInfo(String goalID, String goalName, String goalType, String simVar1, String simVarValue1,
        String simVar2, String simVarValue2, String simVar3, String simVarValue3, String goalDescription, String goalText){
         
        try{
            
            String q = "UPDATE goals SET GOAL_NAME=?, DESCRIPTION=?, FULL_TEXT=?, TYPE=?,"
                    + "SIM_VAR_1=?, SIM_VAR_1_VALUE=?,SIM_VAR_2=?, SIM_VAR_2_VALUE=?,"
                    + "SIM_VAR_3=?, SIM_VAR_3_VALUE=? WHERE GOAL_ID=?";
            PreparedStatement query = connection.prepareStatement(q);
            
            query.setString(1, goalName);
            query.setString(2, goalDescription);
            query.setString(3, goalText);
            query.setString(4, goalType);
            query.setString(5, simVar1);
            query.setString(6, simVarValue1);
            query.setString(7, simVar2);
            query.setString(8, simVarValue2);
            query.setString(9, simVar3);
            query.setString(10, simVarValue3);
            query.setString(11, goalID);
 
            query.execute();
            query.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;        
     }
     
    public boolean addNewGoal(String goalName,String goalType, String simVar1, String simVarValue1, 
                        String simVar2, String simVarValue2, String simVar3, String simVarValue3,
                        String goalDescription, String goalText){
         
        try{
            
            String q = "INSERT INTO goals VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement query = connection.prepareStatement(q);
            
            query.setString(1, getNextAvailableID("Goal"));
            query.setString(2, goalName);
            query.setString(3, goalDescription);
            query.setString(4, goalText);
            query.setString(5, goalType);
            query.setString(6, simVar1);
            query.setString(7, simVarValue1);
            query.setString(8, simVar2);
            query.setString(9, simVarValue2);
            query.setString(10, simVar3);
            query.setString(11, simVarValue3);
               
               
            query.execute();
            query.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
       
    public boolean deleteGoal(String goal_id){
        try{
            String sql = "DELETE FROM goals WHERE GOAL_ID = ?";
            PreparedStatement query = connection.prepareStatement(sql);
            
            query.setString(1, goal_id);
            
            query.execute();
            query.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
           
        return false;
    }    
}
