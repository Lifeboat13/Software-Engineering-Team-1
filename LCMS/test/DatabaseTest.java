/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSet;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Greg
 */
public class DatabaseTest {
   
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
       
    /**
     * Test of checkLogin method, of class Database.
     */
    @Test
    public void testCheckLogin() {
        System.out.println("checkLogin");
        String username = "employee";
        char[] password = "".toCharArray();
        Database instance = new Database();
        ArrayList<String> expResult = new ArrayList<String>();
        expResult.add("2");
        expResult.add("employee");
        expResult.add("");
        expResult.add("firstname");
        expResult.add("lastname");
        expResult.add("some address");
        expResult.add("1");
        ArrayList<String> result = instance.checkLogin(username, password);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of updateLog method, of class Database.
     */
    @Test
    public void testUpdateLog() {
        System.out.println("updateLog");
        String eid = "1";
        String text = "testlog";
        Database instance = new Database();
        boolean expResult = true;
        boolean result = instance.updateLog(eid, text);
        assertEquals(expResult, result);
 
    }

    /**
     * Test of updateUserAddress method, of class Database.
     */
    @Test
    public void testUpdateUserAddress() {
        System.out.println("updateUserAddress");
        String eid = "1";
        String address = "thisaddress";
        Database instance = new Database();
        boolean expResult = true;
        boolean result = instance.updateUserAddress(eid, address);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getUserAdress method, of class Database.
     */
    @Test
    public void testGetUserAdress() {
        System.out.println("getUserAdress");
        String eid = "3";
        Database instance = new Database();
        String expResult = "address";
        String result = instance.getUserAdress(eid);
        assertNotNull(result);
  
    }

    /**
     * Test of getUserPassword method, of class Database.
     */
    @Test
    public void testGetUserPassword() {
        System.out.println("getUserPassword");
        String eid = "1";
        Database instance = new Database();
        String expResult = "";
        String result = instance.getUserPassword(eid);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of updateUserPassword method, of class Database.
     */
    @Test
    public void testUpdateUserPassword() {
        System.out.println("updateUserPassword");
        String eid = "4";
        String password = "";
        Database instance = new Database();
        boolean expResult = true;
        boolean result = instance.updateUserPassword(eid, password);
        assertEquals(expResult, result);
        
    }

   

    /**
     * Test of updateEmployeeInfo method, of class Database.
     */
    @Test
    public void testUpdateEmployeeInfo() {
        System.out.println("updateEmployeeInfo");
        String eid = "";
        String firstname = "";
        String lastname = "";
        String address = "";
        Database instance = new Database();
        boolean expResult = true;
        boolean result = instance.updateEmployeeInfo(eid, firstname, lastname, address);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addNewEmployee method, of class Database.
     */
    @Test
    public void testAddNewEmployee() {
        System.out.println("addNewEmployee");
        String eid = "7";
        String username = "";
        String password = "";
        String firstname = "";
        String lastname = "";
        String address = "";
        Database instance = new Database();
        boolean expResult = true;
        boolean result = instance.addNewEmployee(eid, username, password, firstname, lastname, address);
        instance.deleteEmployee(eid);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of deleteEmployee method, of class Database.
     */
    @Test
    public void testDeleteEmployee() {
        System.out.println("deleteEmployee");
        String eid = "6";
        Database instance = new Database();
        boolean expResult = true;
        instance.addNewEmployee("6","", "", "", "", "");
        boolean result = instance.deleteEmployee(eid);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getEmployees method, of class Database.
     */
    @Test
    public void testGetEmployees() {
        System.out.println("getEmployees");
        Database instance = new Database();
        ResultSet result = instance.getEmployees();
        assertNotNull(result);
        
    }

    /**
     * Test of getEmployeeLog method, of class Database.
     */
    @Test
    public void testGetEmployeeLog() {
        System.out.println("getEmployeeLog");
        String id = "";
        Database instance = new Database();
        ResultSet result = instance.getEmployeeLog(id);
        assertNotNull(result);
        
    }
    
}
