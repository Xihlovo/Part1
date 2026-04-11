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
        //checking length for at least 8 characters
      if (password.length() <8 )  
          return false;
      //checking if password contains capital letters
      if (!password.matches(".*[A-Z].*")) {
    return false; }
    
   if (!password.contains(".*\\d.*")) {
       return false;
       //checking to see if password has any numbers
   }
       if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
    return false;
    //checking for special characters
       }
    return true;
    
    // Password regex patterns learned from https://www.regular-expressions.info/
    }
}
       
   
   
  
    



