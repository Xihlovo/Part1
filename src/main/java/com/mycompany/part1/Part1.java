/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1;
import java.util.Scanner;

/**
 *
 * @author nkhwa
 */
public class Part1 {

    public static void main(String[] args) {
    Login user = new Login();
    Scanner sc = new Scanner(System.in);
    
    //Getting all key information
        System.out.println("*********** REGISTRATION***********");
        System.out.print("Enter your Name");
       String name = sc.nextLine();
       
        System.out.print("Enter your Surname");
        String surname = sc.nextLine();
        
        System.out.print("Create your Username(must have _ and 5 characters or less");
        String username = sc.nextLine();
        
        System.out.print("Create a password(must be at least 8 characters long,1 capital,1 special character,1 number)");
       String password = sc.nextLine();
       
        System.out.print("Enter your Phone number (in +27 form)");
        String phone = sc.nextLine();
        
        //Atempting to Register user
        String registrationMessage = user.registerUser(name,surname, username, password, phone);
                
        System.out.println(registrationMessage);
       
       //Continue only if Registered
       if (registrationMessage.equals("User registered successfully.")) {
            System.out.println("*** LOGIN ***");
            
            System.out.print("Enter username: ");
            String loginUsername = sc.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = sc.nextLine();
            
            String loginMessage = user.returnLoginStatus(loginUsername, loginPassword);
            System.out.println(loginMessage);
        } else {
            System.out.println("Registration failed. Please restart the application.");
        }

    
              sc.close();
    }
}


    

    
