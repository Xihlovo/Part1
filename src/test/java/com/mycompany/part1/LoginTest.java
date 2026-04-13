/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit Test for Login Class
 * Test all 6 methods with correct and incorrect inputs
 * 
 * @author nkhwa
 */
public class LoginTest {
    
    private Login login;
    
    @BeforeEach
    public void setUp() {
        // Create a fresh Login object before each test
        login = new Login();
    }
    
    // ========== checkUserName TESTS ==========
    
    @Test
    public void testCheckUserName_Valid() {
        System.out.println("Testing valid username: _m1ke");
        boolean result = login.checkUserName("_m1ke");
        assertTrue(result, "Username '_m1ke' should be valid");
    }
    
    @Test
    public void testCheckUserName_Invalid() {
        System.out.println("Testing invalid username: mike !!!!!!!");
        boolean result = login.checkUserName("mike !!!!!!!");
        assertFalse(result, "Username 'mike !!!!!!!' should be invalid");
    }
    
    // ========== checkPasswordComplexity TESTS ==========
    
    @Test
    public void testCheckPasswordComplexity_Valid() {
        System.out.println("Testing valid password: Ch&&sec@ke99!");
        boolean result = login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result, "Password 'Ch&&sec@ke99!' should be valid");
    }
    
    @Test
    public void testCheckPasswordComplexity_Invalid() {
        System.out.println("Testing invalid password: password");
        boolean result = login.checkPasswordComplexity("password");
        assertFalse(result, "Password 'password' should be invalid");
    }
    
    // ========== checkCellPhoneNumber TESTS ==========
    
    @Test
    public void testCheckCellPhoneNumber_Valid() {
        System.out.println("Testing valid phone: +27838968976");
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue(result, "Phone '+27838968976' should be valid");
    }
    
    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        System.out.println("Testing invalid phone: 08966553");
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse(result, "Phone '08966553' should be invalid");
    }
    
    // ========== registerUser TESTS ==========
    
    @Test
    public void testRegisterUser_AllValid() {
        System.out.println("Testing registration with all valid data");
        String result = login.registerUser("John", "Doe", "j_doe", "Password1!", "+27838968976");
        assertEquals("User registered successfully.", result, "Valid registration should return success message");
    }
    
    @Test
    public void testRegisterUser_InvalidUsername() {
        System.out.println("Testing registration with invalid username");
        String result = login.registerUser("John", "Doe", "janesmith", "Password1!", "+27838968976");
        assertTrue(result.contains("Username is not correctly formatted"), 
            "Invalid username should return username error message");
    }
    
    @Test
    public void testRegisterUser_InvalidPassword() {
        System.out.println("Testing registration with invalid password");
        String result = login.registerUser("John", "Doe", "j_doe", "weak", "+27838968976");
        assertTrue(result.contains("Password is not correctly formatted"), 
            "Invalid password should return password error message");
    }
    
    @Test
    public void testRegisterUser_InvalidPhone() {
        System.out.println("Testing registration with invalid phone");
        String result = login.registerUser("John", "Doe", "j_doe", "Password1!", "08966553");
        assertTrue(result.contains("Cell phone number incorrectly formatted"), 
            "Invalid phone should return phone error message");
    }
    
    // ========== loginUser TESTS ==========
    
    @Test
    public void testLoginUser_Successful() {
        System.out.println("Testing successful login");
        // First register a user
        login.registerUser("Long", "John", "L_jon", "Password1!", "+27838968976");
        // Then test login
        boolean result = login.loginUser("j_doe", "Password1!");
        assertTrue(result, "Correct credentials should return true");
    }
    
    @Test
    public void testLoginUser_Failed_WrongPassword() {
        System.out.println("Testing failed login with wrong password");
        // First register a user
        login.registerUser("John", "Long", "j_loo", "Password1!", "+27838968976");
        // Then test login with wrong password
        boolean result = login.loginUser("j_doe", "wrongpass");
        assertFalse(result, "Wrong password should return false");
    }
    
    @Test
    public void testLoginUser_Failed_WrongUsername() {
        System.out.println("Testing failed login with wrong username");
        // First register a user
        login.registerUser("Justice", "Duu", "j_duu", "Password1!", "+27838968976");
        // Then test login with wrong username
        boolean result = login.loginUser("wronguser", "Password1!");
        assertFalse(result, "Wrong username should return false");
    }
    
    // ========== returnLoginStatus TESTS ==========
    
    @Test
    public void testReturnLoginStatus_Successful() {
        System.out.println("Testing successful login status message");
        // First register a user
        login.registerUser("Joe", "Doe", "j_doe", "Password1!", "+27838968976");
        // Then test login status
        String result = login.returnLoginStatus("j_doe", "Password1!");
        assertEquals("Welcome John Doe it is great to see you again.", result, 
            "Successful login should return welcome message");
    }
    
    @Test
    public void testReturnLoginStatus_Failed() {
        System.out.println("Testing failed login status message");
        // First register a user
        login.registerUser("Doe", "Doe", "D_doe", "Password1!", "+27838968976");
        // Then test login status with wrong password
        String result = login.returnLoginStatus("j_doe", "wrongpass");
        assertEquals("Username or password incorrect, please try again.", result, 
            "Failed login should return error message");
    }
}