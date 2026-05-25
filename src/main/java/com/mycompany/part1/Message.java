/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1;

import java.util.Random;

public class Message {
    
    // Instance variables that every message will have
    private String messageID;      // Random 10-digit number
    private int messageCount;      
    private String recipient;      // Cell phone number of recipient
    private String messageText;    // The actual message (max 250 chars)
    private String messageHash;    
    private String status;         // "Sent"
    
   
    private static int totalMessagesSent = 0;  // Counts only Sent messages
    
    // Constructor 
       public Message(int messageCount, String recipient, String messageText) {
        
        this.messageCount = messageCount;
        this.recipient = recipient;
        this.messageText = messageText;
        
        //create 10-digit message ID
        this.messageID = generateMessageID();
        
        this.status = "Pending";
        
      this.messageHash = createMessageHash();
    }
    
    // method to generate 10-digit ID
    private String generateMessageID() {
        Random rand = new Random();
        // Generate a number between 1,000,000,000 and 9,999,999,999
        long randomNumber = 1_000_000_000L + (long)(rand.nextDouble() * 9_000_000_000L);
        return String.valueOf(randomNumber);
    }
    public String createMessageHash() {
    String firstTwo = messageID.substring(0, 2);
    
    String[] words = messageText.trim().split("\\s+");
    
      // Get first word and remove any non-letters 
    String firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
    
    // Get last word and remove any non-letters
    String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
    
    
    // Combine them
    String hash = firstTwo + ":" + messageCount + ":" + firstWord + lastWord;
    
    return hash.toUpperCase();
}
    public String sentMessage(java.util.Scanner scanner) {
    System.out.println("\n--- Message Options ---");
    System.out.println("1. Send Message");
    System.out.println("2. Disregard Message");
    System.out.println("3. Store Message to send later");
    System.out.print("Choose an option: ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    switch (choice) {
        case 1:
            this.status = "Sent";
            totalMessagesSent++;
            return "Message successfully sent.";
        case 2:
            this.status = "Disregarded";
            return "Press 0 to delete the message.";
        case 3:
            this.status = "Stored";
             storeMessageToFile();
            return "Message successfully stored.";
        default:
            return "Invalid option. Message not processed.";
    }
}
    
        //Check if message ID is valid )
    public boolean checkMessageID() {
        if (messageID == null) {
            return false;
        }
        return messageID.length() == 10 && messageID.matches("\\d{10}");
    }
    
    //Check if recipient cell number is valid
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27[0-9]{9,10}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
        // Getter methods
    public String getMessageID() { return messageID; }
    public int getMessageCount() { return messageCount; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
    
    // Setter for status so we can update it when user chooses
    public void setStatus(String status) { this.status = status; }
    
    
    public static int returnTotalMessages() { 
        return totalMessagesSent; 
    }
    public static void resetTotalMessagesSent() {
    totalMessagesSent = 0;
}
        public void storeMessageToFile() {
    try {
        String json = "{";
        json += "\"messageID\":\"" + messageID + "\",";
        json += "\"messageCount\":" + messageCount + ",";
        json += "\"recipient\":\"" + recipient + "\",";
        json += "\"messageText\":\"" + messageText.replace("\"", "\\\"") + "\",";
        json += "\"messageHash\":\"" + messageHash + "\",";
        json += "\"status\":\"" + status + "\"";
        json += "}";
        
        // Write to file
        java.io.FileWriter writer = new java.io.FileWriter("stored_messages.json", true);
        writer.write(json + "\n");
        writer.close();
        
        System.out.println("Message stored to file.");
    } catch (java.io.IOException e) {
        System.out.println("Error storing message: " + e.getMessage());
    }
}
    }
