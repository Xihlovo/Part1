/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1;

/**
 *
 * @author nkhwa
 */
public class Login {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String phone;
    
    
    public boolean checkUserName(String username) {
        
         return username.contains("_") && username.length() <= 5;
         //username.contains checks if username has an underscore
         //username.length checks to see if username is at least 5 characters long
    }
    
   public boolean checkPasswordComplexity(String password) {
    // checking length for at least 8 characters
    if (password.length() < 8)  
        return false;
    
    // checking if password contains capital letters
    if (!password.matches(".*[A-Z].*")) {
        return false; 
    }
    
    // checking to see if password has any numbers (FIXED LINE)
    if (!password.matches(".*\\d.*")) {  // Changed from .contains to .matches
        return false;
    }
    
    // checking for special characters
    if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
        return false;
    }
    
    return true;
    
    // Password regex patterns learned from https://www.regular-expressions.info/
}
    public boolean checkCellPhoneNumber(String phone) {
    return phone.matches("^\\+27[0-9]{9,10}$");  
         //learent regex from regular-expressions.info
        //methods checks if number starts with +27 and has 0-10 digits
    }
  
    public String registerUser (String name,String surname,String username,String password,String phone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
         if    (!checkPasswordComplexity(password)) {
             
         
             return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
         }
          if (!checkCellPhoneNumber(phone)) {
              return "Cell phone number incorrectly formatted or does not contain international code.";
                      }
    
         this.name = name;      
         this.surname = surname;    
         this.username = username;
         this.password = password;
         this.phone = phone;
    
        return "User succesfully Registered";
    }
    public boolean loginUser(String username,String password) {
         if (username.equals(this.username) && password.equals(this.password)) {
        return true;  
    } else {
        return false; 
    } 
}
    public String returnLoginStatus(String username, String password) { 
        if (loginUser(username, password)) {
            return "Welcome " + name + " " + surname + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }                                      // Closes Brace 7
    
}   
      
   
            
        
        
    

       
   
   
  
    



