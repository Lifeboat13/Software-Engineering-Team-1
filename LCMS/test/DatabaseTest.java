/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        instance.closeConnection();
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
        String username = "username";
        char[] password = "password".toCharArray();
        
        ArrayList<String> expResult = new ArrayList<String>();
        expResult.add("1");
        expResult.add("username");
        expResult.add("password");
        expResult.add("John");
        expResult.add("Oxy");
        expResult.add("Johns Address");
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
       
       String eid = "test";
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
        String oldAddress = instance.getUserAddress(eid);
        String newAddress = "thisaddress";

        instance.updateUserAddress(eid, newAddress);
        
        assertEquals(newAddress, instance.getUserAddress(eid));
        instance.updateUserAddress(eid, oldAddress);
    }

    /**
     * Test of getUserAdress method, of class Database.
     */
    @Test
    public void testGetUserAdress() {
        
        System.out.println("getUserAdress");
        String eid = "1";
        
        String expResult = "Johns Address";
        String result = instance.getUserAddress(eid);
        
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
        String eid = "1";
        String oldPassword = instance.getUserPassword("1");
        String newPassword = "newpass";
        instance.updateUserPassword(eid, newPassword);
        String expResult = "newpass";
        
        assertEquals(expResult, instance.getUserPassword("1"));
        instance.updateUserPassword(eid, oldPassword);
        
    }

   

    /**
     * Test of updateEmployeeInfo method, of class Database.
     */
    @Test
    public void testUpdateEmployeeInfo() {
        System.out.println("updateEmployeeInfo");
        
          
        
        //instance.updateUserInfo(eid, firstname, lastname, address);
        assertEquals("Johns Address", instance.getUserAddress("1"));
        assertEquals("John Oxy", instance.getUserName("1"));
      
        
    }

    /**
     * Test of addNewEmployee method, of class Database.
     */
    @Test
    public void testAddNewUser() {
        System.out.println("addNewEmployee");
        String eid = instance.getNextAvailableID("Employee");
        String username = "";
        String password = "";
        String firstname = "";
        String lastname = "";
        String address = "";
        int userType = 1;
        boolean expResult = true;
        boolean result = instance.addNewUser(username, password, firstname, lastname, address, userType);
        instance.deleteUser(eid);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of deleteEmployee method, of class Database.
     */
    @Test
    public void testDeleteEmployee() {
        System.out.println("deleteEmployee");
        String eid = instance.getNextAvailableID("Employee");
        
        boolean expResult = true;
        instance.addNewUser("", "", "", "", "", 1);
        boolean result = instance.deleteUser(eid);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getEmployees method, of class Database.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getEmployees");
        
        ArrayList<String> result = instance.getEmployees();
        assertNotNull(result);
        
    }

    /**
     * Test of getEmployeeLog method, of class Database.
     */
    @Test
    public void testGetUserLog() {
        System.out.println("getEmployeeLog");      
        int before = instance.getUserLog("1").size();
        
        instance.updateLog("1", "fakelog");
        int after = instance.getUserLog("1").size();
        
        assertEquals(before, (after - 2), .05);
        
    }
    
}
