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
   private static Database instance;
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new Database();
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
        
        String expResult = "address";
        String result = instance.getUserAdress(eid);
        assertEquals(expResult, result);
  
    }

    /**
     * Test of getUserPassword method, of class Database.
     */
    @Test
    public void testGetUserPassword() {
        System.out.println("getUserPassword");
        String eid = "1";        
        String expResult = "password";
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
        String password = "pass";
        instance.updateUserPassword(eid, password);
        String expResult = "pass";
        
        assertEquals(expResult, instance.getUserPassword("4"));
        
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
        
        boolean expResult = true;
        boolean result = instance.updateUserInfo(eid, firstname, lastname, address);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addNewEmployee method, of class Database.
     */
    @Test
    public void testAddNewUser() {
        System.out.println("addNewEmployee");
        String eid = "7";
        String username = "";
        String password = "";
        String firstname = "";
        String lastname = "";
        String address = "";
        int userType = 1;
        boolean expResult = true;
        boolean result = instance.addNewUser(eid, username, password, firstname, lastname, address, userType);
        instance.deleteUser(eid);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of deleteEmployee method, of class Database.
     */
    @Test
    public void testDeleteEmployee() {
        System.out.println("deleteEmployee");
        String eid = "6";
        
        boolean expResult = true;
        instance.addNewUser("6","", "", "", "", "", 1);
        boolean result = instance.deleteUser(eid);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getEmployees method, of class Database.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getEmployees");
        
        ArrayList<String> result = instance.getUsers();
        assertNotNull(result);
        
    }

    /**
     * Test of getEmployeeLog method, of class Database.
     */
    @Test
    public void testGetUserLog() {
        System.out.println("getEmployeeLog");
        String id = "";
        
        ArrayList<String> result = instance.getUserLog(id);
        assertNotNull(result);
        
    }
    
}
