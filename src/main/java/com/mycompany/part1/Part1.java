/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1;
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author nkhwa
 */
public class Part1 {
    
    // Arrays (fixed size - max 100 messages)
    private static Message[] sentMessages = new Message[100];
    private static Message[] disregardedMessages = new Message[100];
    private static Message[] storedMessages = new Message[100];
    private static String[] messageHashes = new String[100];
    private static String[] messageIDs = new String[100];
    
    // Track how many items are actually in each array
    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;

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
                    System.out.println("4. Stored Messages");
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
                        case 4:
                            handleStoredMessages(sc);   
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
            
            // Add to arrays based on status
            if (msg.getStatus().equals("Sent") && sentCount < 100) {
                sentMessages[sentCount] = msg;
                sentCount++;
            } else if (msg.getStatus().equals("Disregarded") && disregardedCount < 100) {
                disregardedMessages[disregardedCount] = msg;
                disregardedCount++;
            } else if (msg.getStatus().equals("Stored") && storedCount < 100) {
                storedMessages[storedCount] = msg;
                messageHashes[storedCount] = msg.getMessageHash();
                messageIDs[storedCount] = msg.getMessageID();
                storedCount++;
            }
            
            System.out.println();
        }
        
        System.out.println("\n========== SUMMARY ==========");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
        System.out.println("==============================\n");
    }
    
    public static void handleStoredMessages(Scanner sc) {
        // Load stored messages from JSON file
        loadStoredMessagesFromFile();
        
        if (storedCount == 0) {
            System.out.println("\nNo stored messages found.\n");
            return;
        }
        
        boolean subRunning = true;
        while (subRunning) {
            System.out.println("\n========== STORED MESSAGES MENU ==========");
            System.out.println("a. Display all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message by Message ID");
            System.out.println("d. Search for messages by recipient");
            System.out.println("e. Delete a message using Message Hash");
            System.out.println("f. Display full report of stored messages");
            System.out.println("g. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            String subChoice = sc.nextLine().toLowerCase();
            
            switch (subChoice) {
                case "a":
                    displayAllStoredMessages();
                    break;
                case "b":
                    displayLongestStoredMessage();
                    break;
                case "c":
                    searchByMessageID(sc);
                    break;
                case "d":
                    searchByRecipient(sc);
                    break;
                case "e":
                    deleteByMessageHash(sc);
                    break;
                case "f":
                    displayFullReport();
                    break;
                case "g":
                    subRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public static void loadStoredMessagesFromFile() {
        // Reset arrays and counter
        storedMessages = new Message[100];
        messageHashes = new String[100];
        messageIDs = new String[100];
        storedCount = 0;
        
        try {
            File file = new File("stored_messages.json");
            if (!file.exists()) {
                return;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null && storedCount < 100) {
                String id = extractValue(line, "messageID");
                String hash = extractValue(line, "messageHash");
                String recipient = extractValue(line, "recipient");
                String text = extractValue(line, "messageText");
                int count = Integer.parseInt(extractValue(line, "messageCount"));
                
                Message msg = new Message(count, recipient, text);
                
                // Use reflection to set private fields
                try {
                    java.lang.reflect.Field fieldId = msg.getClass().getDeclaredField("messageID");
                    fieldId.setAccessible(true);
                    fieldId.set(msg, id);
                    
                    java.lang.reflect.Field fieldHash = msg.getClass().getDeclaredField("messageHash");
                    fieldHash.setAccessible(true);
                    fieldHash.set(msg, hash);
                } catch (Exception e) {}
                
                msg.setStatus("Stored");
                
                storedMessages[storedCount] = msg;
                messageHashes[storedCount] = hash;
                messageIDs[storedCount] = id;
                storedCount++;
            }
            reader.close();
            System.out.println("Loaded " + storedCount + " stored messages.");
        } catch (Exception e) {
            System.out.println("Error loading stored messages: " + e.getMessage());
        }
    }
    
    // Helper to extract values from JSON line
    private static String extractValue(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) {
            search = "\"" + key + "\":";
            start = json.indexOf(search);
            if (start == -1) return "";
            start += search.length();
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            if (end == -1) return "";
            return json.substring(start, end);
        }
        start += search.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return "";
        return json.substring(start, end);
    }
    
    public static void displayAllStoredMessages() {
        if (storedCount == 0) {
            System.out.println("\nNo stored messages found.\n");
            return;
        }
        
        System.out.println("\n========== ALL STORED MESSAGES ==========");
        for (int i = 0; i < storedCount; i++) {
            System.out.println("Recipient: " + storedMessages[i].getRecipient());
            System.out.println("Message: " + storedMessages[i].getMessageText());
            System.out.println("----------------------------------------");
        }
    }
    
    public static void displayLongestStoredMessage() {
        if (storedCount == 0) {
            System.out.println("\nNo stored messages found.\n");
            return;
        }
        
        Message longest = storedMessages[0];
        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i].getMessageText().length() > longest.getMessageText().length()) {
                longest = storedMessages[i];
            }
        }
        
        System.out.println("\n========== LONGEST STORED MESSAGE ==========");
        System.out.println("Message: " + longest.getMessageText());
        System.out.println("Length: " + longest.getMessageText().length() + " characters");
        System.out.println("Recipient: " + longest.getRecipient());
    }
    
    public static void searchByMessageID(Scanner sc) {
        System.out.print("\nEnter Message ID to search: ");
        String searchId = sc.nextLine();
        
        for (int i = 0; i < storedCount; i++) {
            if (messageIDs[i].equals(searchId)) {
                System.out.println("\n========== MESSAGE FOUND ==========");
                System.out.println("Recipient: " + storedMessages[i].getRecipient());
                System.out.println("Message: " + storedMessages[i].getMessageText());
                System.out.println("Message Hash: " + messageHashes[i]);
                return;
            }
        }
        System.out.println("\nMessage ID not found.");
    }
    
    public static void searchByRecipient(Scanner sc) {
        System.out.print("\nEnter recipient phone number to search: ");
        String searchRecipient = sc.nextLine();
        
        boolean found = false;
        System.out.println("\n========== MESSAGES FOR " + searchRecipient + " ==========");
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].getRecipient().equals(searchRecipient)) {
                System.out.println("Message: " + storedMessages[i].getMessageText());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No messages found for recipient: " + searchRecipient);
        }
    }
    
    public static void deleteByMessageHash(Scanner sc) {
        System.out.print("\nEnter Message Hash to delete: ");
        String searchHash = sc.nextLine();
        
        for (int i = 0; i < storedCount; i++) {
            if (messageHashes[i].equals(searchHash)) {
                System.out.println("\n========== MESSAGE DELETED ==========");
                System.out.println("Message: " + storedMessages[i].getMessageText());
                
                // Shift all elements left to fill the gap
                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j] = storedMessages[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                    messageIDs[j] = messageIDs[j + 1];
                }
                storedCount--;
                
                // Update the JSON file
                saveStoredMessagesToFile();
                System.out.println("Message successfully deleted.");
                return;
            }
        }
        System.out.println("\nMessage Hash not found.");
    }
    
    public static void saveStoredMessagesToFile() {
        try {
            FileWriter writer = new FileWriter("stored_messages.json");
            for (int i = 0; i < storedCount; i++) {
                Message msg = storedMessages[i];
                String json = "{";
                json += "\"messageID\":\"" + messageIDs[i] + "\",";
                json += "\"messageCount\":" + msg.getMessageCount() + ",";
                json += "\"recipient\":\"" + msg.getRecipient() + "\",";
                json += "\"messageText\":\"" + msg.getMessageText().replace("\"", "\\\"") + "\",";
                json += "\"messageHash\":\"" + messageHashes[i] + "\",";
                json += "\"status\":\"" + msg.getStatus() + "\"";
                json += "}";
                writer.write(json + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving stored messages: " + e.getMessage());
        }
    }
    
    public static void displayFullReport() {
        if (storedCount == 0) {
            System.out.println("\nNo stored messages found.\n");
            return;
        }
        
        System.out.println("\n***** FULL STORED MESSAGES REPORT ******");
        System.out.printf("%-15s %-20s %-30s %s\n", "Message Hash", "Recipient", "Message", "ID");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < storedCount; i++) {
            String text = storedMessages[i].getMessageText();
            if (text.length() > 27) {
                text = text.substring(0, 24) + "...";
            }
            System.out.printf("%-15s %-20s %-30s %s\n", 
                messageHashes[i], 
                storedMessages[i].getRecipient(), 
                text, 
                messageIDs[i]);
        }
        System.out.println("================================================================================\n");
    }
}