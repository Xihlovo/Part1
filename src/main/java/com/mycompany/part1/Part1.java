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
    
    //Arrays 
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();
    

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
                            case 4:   // ← NEW OPTION for Part 3
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
    public static void handleStoredMessages(Scanner sc) {
    //load stored messages from JSON file
    loadStoredMessagesFromFile();
    
    if (storedMessages.isEmpty()) {
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
    storedMessages.clear(); // Clear existing data
    messageHashes.clear();
    messageIDs.clear();
    
    try {
        java.io.File file = new java.io.File("stored_messages.json");
        if (!file.exists()) {
            return;
        }
        
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
        String line;
        
        while ((line = reader.readLine()) != null) {
            // Parse JSON line (simplified - assuming format: {"messageID":"xxx","messageCount":1,"recipient":"xxx","messageText":"xxx","messageHash":"xxx","status":"Stored"})
            String id = extractValue(line, "messageID");
            String hash = extractValue(line, "messageHash");
            String recipient = extractValue(line, "recipient");
            String text = extractValue(line, "messageText");
            int count = Integer.parseInt(extractValue(line, "messageCount"));
            
            // Create message object
            Message msg = new Message(count, recipient, text);
            // Manually set the fields since we're loading from file
            java.lang.reflect.Field fieldId = msg.getClass().getDeclaredField("messageID");
            fieldId.setAccessible(true);
            fieldId.set(msg, id);
            
            java.lang.reflect.Field fieldHash = msg.getClass().getDeclaredField("messageHash");
            fieldHash.setAccessible(true);
            fieldHash.set(msg, hash);
            
            msg.setStatus("Stored");
            
            storedMessages.add(msg);
            messageHashes.add(hash);
            messageIDs.add(id);
        }
        reader.close();
        System.out.println("Loaded " + storedMessages.size() + " stored messages.");
    } catch (Exception e) {
        System.out.println("Error loading stored messages: " + e.getMessage());
    }
}

// Helper to extract values from JSON line
private static String extractValue(String json, String key) {
    String search = "\"" + key + "\":\"";
    int start = json.indexOf(search) + search.length();
    int end = json.indexOf("\"", start);
    if (start >= search.length() && end > start) {
        return json.substring(start, end);
    }
    // Handle numeric values 
    search = "\"" + key + "\":";
    start = json.indexOf(search) + search.length();
    end = json.indexOf(",", start);
    if (end == -1) end = json.indexOf("}", start);
    if (start >= search.length() && end > start) {
        return json.substring(start, end);
    }
    return "";
}

public static void displayAllStoredMessages() {
    if (storedMessages.isEmpty()) {
        System.out.println("\nNo stored messages found.\n");
        return;
    }
    
    System.out.println("\n========== ALL STORED MESSAGES ==========");
    for (Message msg : storedMessages) {
        System.out.println("Recipient: " + msg.getRecipient());
        System.out.println("Message: " + msg.getMessageText());
        System.out.println("----------------------------------------");
    }
}

public static void displayLongestStoredMessage() {
    if (storedMessages.isEmpty()) {
        System.out.println("\nNo stored messages found.\n");
        return;
    }
    
    Message longest = storedMessages.get(0);
    for (Message msg : storedMessages) {
        if (msg.getMessageText().length() > longest.getMessageText().length()) {
            longest = msg;
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
    
    for (int i = 0; i < messageIDs.size(); i++) {
        if (messageIDs.get(i).equals(searchId)) {
            Message msg = storedMessages.get(i);
            System.out.println("\n========== MESSAGE FOUND ==========");
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            System.out.println("Message Hash: " + msg.getMessageHash());
            return;
        }
    }
    System.out.println("\nMessage ID " + searchId + " not found.");
}

public static void searchByRecipient(Scanner sc) {
    System.out.print("\nEnter recipient phone number to search: ");
    String searchRecipient = sc.nextLine();
    
    boolean found = false;
    System.out.println("\n========== MESSAGES FOR " + searchRecipient + " ==========");
    for (Message msg : storedMessages) {
        if (msg.getRecipient().equals(searchRecipient)) {
            System.out.println("Message: " + msg.getMessageText());
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
    
    for (int i = 0; i < messageHashes.size(); i++) {
        if (messageHashes.get(i).equals(searchHash)) {
            Message msg = storedMessages.get(i);
            System.out.println("\n========== MESSAGE DELETED ==========");
            System.out.println("Message: " + msg.getMessageText());
            
            // Remove from arrays
            storedMessages.remove(i);
            messageHashes.remove(i);
            messageIDs.remove(i);
            
            // Update the JSON file
            saveStoredMessagesToFile();
            System.out.println("Message successfully deleted.");
            return;
        }
    }
    System.out.println("\nMessage Hash " + searchHash + " not found.");
}

public static void saveStoredMessagesToFile() {
    try {
        java.io.FileWriter writer = new java.io.FileWriter("stored_messages.json");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            String json = "{";
            json += "\"messageID\":\"" + messageIDs.get(i) + "\",";
            json += "\"messageCount\":" + msg.getMessageCount() + ",";
            json += "\"recipient\":\"" + msg.getRecipient() + "\",";
            json += "\"messageText\":\"" + msg.getMessageText().replace("\"", "\\\"") + "\",";
            json += "\"messageHash\":\"" + messageHashes.get(i) + "\",";
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
    if (storedMessages.isEmpty()) {
        System.out.println("\nNo stored messages found.\n");
        return;
    }
    
    System.out.println("***** FULL STORED MESSAGES REPORT ******");
    System.out.printf("%-15s %-20s %-30s %s\n", "Message Hash", "Recipient", "Message", "ID");
    System.out.println("--------------------------------------------------------------------------------");
    
    for (int i = 0; i < storedMessages.size(); i++) {
        Message msg = storedMessages.get(i);
        String hash = messageHashes.get(i);
        String id = messageIDs.get(i);
        String recipient = msg.getRecipient();
        String text = msg.getMessageText();
        
        // Truncate long text for display
        if (text.length() > 27) {
            text = text.substring(0, 24) + "...";
        }
        
        System.out.printf("%-15s %-20s %-30s %s\n", hash, recipient, text, id);
    }
    System.out.println("================================================================================\n");
}
} 