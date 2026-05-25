/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author nkhwa
 */
public class Part1 {

    public static void main(String[] args) {
        Login user = new Login();
        Scanner sc = new Scanner(System.in);
        
        // Getting all key information
        System.out.println("*********** REGISTRATION***********");
        System.out.print("Enter your Name: ");
        String name = sc.nextLine();
        
        System.out.print("Enter your Surname: ");
        String surname = sc.nextLine();
        
        System.out.print("Create your Username (must have _ and 5 characters or less): ");
        String username = sc.nextLine();
        
        System.out.print("Create a password (must be at least 8 characters long, 1 capital, 1 special character, 1 number): ");
        String password = sc.nextLine();
        
        System.out.print("Enter your Phone number (in +27 form): ");
        String phone = sc.nextLine();
        
        // Attempting to Register user
        String registrationMessage = user.registerUser(name, surname, username, password, phone);
        System.out.println(registrationMessage);
        
        // Continue only if Registered
        if (registrationMessage.equals("User registered successfully.")) {
            System.out.println("\n*** LOGIN ***");
            
            System.out.print("Enter username: ");
            String loginUsername = sc.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = sc.nextLine();
            
            String loginMessage = user.returnLoginStatus(loginUsername, loginPassword);
            System.out.println(loginMessage);
            
            // ===== CHAT MENU =====
            if (loginMessage.contains("Welcome")) {
                
                System.out.println("\n========================================");
                System.out.println("     Welcome to QuickChat");
                System.out.println("========================================\n");
                
                boolean running = true;
                
                while (running) {
                    System.out.println("Please choose an option:");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Show recently sent messages (Coming Soon)");
                    System.out.println("3. Quit");
                    System.out.print("Enter your choice: ");
                    
                    int choice = sc.nextInt();
                    sc.nextLine();
                    
                    switch (choice) {
                        case 1:
                            sendMessages(sc, user);
                            break;
                        case 2:
                            System.out.println("\nComing Soon. This feature is still in development.\n");
                            break;
                        case 3:
                            System.out.println("\nThank you for using QuickChat. Goodbye!\n");
                            running = false;
                            break;
                        default:
                            System.out.println("\nInvalid choice. Please try again.\n");
                    }
                }
            }
            
        } else {
            System.out.println("Registration failed. Please restart the application.");
        }
        
        sc.close();
    } 
    
    // SEND MESSAGES METHOD 
    public static void sendMessages(Scanner sc, Login user) {
        System.out.print("\nHow many messages do you want to send? ");
        int numMessages = sc.nextInt();
        sc.nextLine();
        
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Message " + i + " of " + numMessages + " ---");
            
            // Get recipient
            String recipient;
            boolean validPhone = false;
            do {
                System.out.print("Enter recipient's cell number (+27 format): ");
                recipient = sc.nextLine();
                if (recipient.matches("^\\+27[0-9]{9,10}$")) {
                    validPhone = true;
                } else {
                    System.out.println("Invalid number. Please use +27 followed by 9-10 digits.");
                }
            } while (!validPhone);
            
            String messageText;
            boolean validLength = false;
            do {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = sc.nextLine();
                if (messageText.length() <= 250) {
                    validLength = true;
                } else {
                    int excess = messageText.length() - 250;
                    System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                }
            } while (!validLength);
            
            Message msg = new Message(i, recipient, messageText);
            
            System.out.println("\n--- Message Details ---");
            System.out.println("Message ID: " + msg.getMessageID());
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            
            String result = msg.sentMessage(sc);
            System.out.println(result);
            
            messages.add(msg);
            System.out.println();
        }
        
        System.out.println("\n========== SUMMARY ==========");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
        System.out.println("==============================\n");
    }
    
} 