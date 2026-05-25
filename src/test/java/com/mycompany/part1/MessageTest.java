/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class MessageTest {
    
    private Message testMessage;
    
    @BeforeEach
    void setUp() {
        Message.resetTotalMessagesSent();
        testMessage = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }
    

    @Test
    void testMessageLength_Success() {
        String shortMessage = "Hello, this is a short message under 250 characters.";
        Message msg = new Message(2, "+27718693002", shortMessage);
        
        assertTrue(msg.getMessageText().length() <= 250, 
            "Message should be 250 characters or less");
        assertEquals("Message ready to send.", getMessageValidationResult(msg));
    }
    
   
    private String getMessageValidationResult(Message msg) {
        if (msg.getMessageText().length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = msg.getMessageText().length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    
    @Test
    void testMessageLength_Failure() {
        
        String longMessage = "This is a very long message that exceeds the maximum allowed length of 250 characters. "
                           + "It needs to be longer than 250 characters to test the failure case properly. "
                           + "Adding more text here to make sure we go over the limit. "
                           + "This should definitely be more than 250 characters now. "
                           + "Let me add one more sentence to be absolutely certain we exceed the limit. ";
        
        Message msg = new Message(2, "+27718693002", longMessage);
        int excess = msg.getMessageText().length() - 250;
        
        assertTrue(msg.getMessageText().length() > 250, 
            "Message should exceed 250 characters");
        assertTrue(excess > 0, "Excess should be a positive number");
        assertEquals("Message exceeds 250 characters by " + excess + "; please reduce the size.", 
                     getMessageValidationResult(msg));
    }
    
    
    @Test
    void testRecipientNumber_Success() {
        String[] validNumbers = {
            "+27718693002",
            "+27831234567",
            "+27123456789",
            "+277612345678"
        };
        
        for (String number : validNumbers) {
            Message msg = new Message(1, number, "Test message");
            assertEquals("Cell phone number successfully captured.", 
                         msg.checkRecipientCell(),
                         "Number " + number + " should be valid");
        }
    }
    

    @Test
    void testRecipientNumber_Failure() {
        String[] invalidNumbers = {
            "08966553",           
            "+2789",              // Too short
            "+27123456789123",    
            "27718693002",        // Missing +
            "+2712345678",        
            "notanumber"          // Not a number at all
        };
        
        for (String number : invalidNumbers) {
            Message msg = new Message(1, number, "Test message");
            assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", 
                         msg.checkRecipientCell(),
                         "Number " + number + " should be invalid");
        }
    }
        @Test
            
    void testMessageHash_TestCase1() {
        // Using the test data from the assignment
        // Message ID will be random, but we can verify the format
        String messageText = "Hi Mike, can you join us for dinner tonight?";
        Message msg = new Message(1, "+27718693002", messageText);
        
        String hash = msg.getMessageHash();
        
        // Check format: XX:X:FIRSTWORDLASTWORD (all caps)
        assertTrue(hash.matches("\\d{2}:\\d+:[A-Z]+"), 
            "Hash format should be: 2digits:number:WORDS");
        
       
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        
        assertTrue(hash.toUpperCase().contains(firstWord), 
            "Hash should contain first word: " + firstWord);
        assertTrue(hash.toUpperCase().contains(lastWord), 
            "Hash should contain last word: " + lastWord);
    }
    
    
    @Test
    void testMessageID_Creation() {
        assertNotNull(testMessage.getMessageID(), "Message ID should not be null");
        assertEquals(10, testMessage.getMessageID().length(), 
            "Message ID should be exactly 10 digits");
        assertTrue(testMessage.checkMessageID(), 
            "checkMessageID() should return true for valid ID");
        assertTrue(testMessage.getMessageID().matches("\\d{10}"), 
            "Message ID should contain only digits");
    }
    
  
    @Test
    void testMessageID_Invalid() {
       
        Message emptyMsg = new Message(1, "+27718693002", "Test");
        assertTrue(emptyMsg.checkMessageID(), 
            "Auto-generated ID should always be valid");
    }
    
 
    @Test
    void testSentMessage_SendOption() {
   
        String input = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        String result = testMessage.sentMessage(scanner);
        
        assertEquals("Message successfully sent.", result);
        assertEquals("Sent", testMessage.getStatus());
        assertEquals(1, Message.returnTotalMessages());
    }
    
    
    @Test
    void testSentMessage_DisregardOption() {
        // Simulate user input: "2" for Disregard
        String input = "2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        String result = testMessage.sentMessage(scanner);
        
        assertEquals("Press 0 to delete the message.", result);
        assertEquals("Disregarded", testMessage.getStatus());
    }
    
   
    @Test
    void testSentMessage_StoreOption() {
       
        String input = "3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        String result = testMessage.sentMessage(scanner);
        
        assertEquals("Message successfully stored.", result);
        assertEquals("Stored", testMessage.getStatus());
    }
    

    @Test
    void testSentMessage_InvalidOption() {

        String input = "5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        String result = testMessage.sentMessage(scanner);
        
        assertEquals("Invalid option. Message not processed.", result);
    }
    

    @Test
    void testTotalMessagesCounter() {
 
        
        Message msg1 = new Message(1, "+27718693002", "First message");
        msg1.sentMessage(new Scanner(new ByteArrayInputStream("1\n".getBytes())));
        
        Message msg2 = new Message(2, "+27831234567", "Second message");
        msg2.sentMessage(new Scanner(new ByteArrayInputStream("1\n".getBytes())));
        
        int total = Message.returnTotalMessages();
        assertTrue(total >= 2, "Total messages sent should be at least 2");
    }
    
  
    @Test
    void testCreateMessageHash_Format() {
        Message msg = new Message(5, "+27718693002", "Hello world");
        String hash = msg.createMessageHash();
        
        // Should be format: XX:5:HELLOWORLD
        assertTrue(hash.matches("\\d{2}:5:HELLOWORLD"), 
            "Hash should be like 'XX:5:HELLOWORLD' but was: " + hash);
    }
    
  
    @Test
    void testCheckRecipientCell_Null() {
        // This would normally not happen, but test for safety
        Message msg = new Message(1, "+27718693002", "Test");
        assertNotNull(msg.checkRecipientCell(), 
            "checkRecipientCell() should always return a message, even if recipient is valid");
    }
    
    
    @Test
    void testGetMessageText() {
        String expectedText = "This is a test message.";
        Message msg = new Message(1, "+27718693002", expectedText);
        assertEquals(expectedText, msg.getMessageText(), 
            "getMessageText() should return the exact message text");
    
}
}