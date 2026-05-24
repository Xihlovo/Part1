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
}