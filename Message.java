import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.regex.Pattern;

public class Message {
    // Message attributes required by the rubric specification tables
    private String messageID;
    private int numSentMessages;
    private String recipientNumber;
    private String messageBody;
    private String messageHash;
    private String actionStatus;

    // Static accumulated counter shared across all message instances
    private static int totalMessagesCounter = 0;

    // Constructor to auto-generate tracking items on creation
    public Message(int taskIndex, String recipientNumber, String messageBody) {
        this.numSentMessages = taskIndex;
        this.messageID = generateRandom10DigitID();
        this.recipientNumber = recipientNumber;
        this.messageBody = messageBody;
        this.messageHash = createMessageHash();
    }

    // Overloaded constructor used when loading messages from JSON or running deterministic tests
    public Message(String customID, int taskIndex, String recipientNumber, String messageBody) {
        this.numSentMessages = taskIndex;
        this.messageID = customID;
        this.recipientNumber = recipientNumber;
        this.messageBody = messageBody;
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit numeric string for the Message ID
    private String generateRandom10DigitID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Validates that the generated Message ID does not exceed 10 characters
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Validates that the message text does not exceed 250 characters
    public String validateMessageLength() {
        if (this.messageBody.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = this.messageBody.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce the size.";
        }
    }

    // Validates that the recipient cell number starts with +27 followed by 9 digits
    public String checkRecipientCell() {
        String regex = "^\\+27\\d{9}$";
        if (Pattern.matches(regex, this.recipientNumber)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an International code. Please correct the number and try again.";
        }
    }

    // Generates the Message Hash in the format: First2DigitsOfID:Index:FIRSTWORDLASTWORD
    public String createMessageHash() {
        if (this.messageID == null || this.messageID.length() < 2 || this.messageBody == null || this.messageBody.trim().isEmpty()) {
            return "00:0:EMPTY";
        }
        String firstTwoDigits = this.messageID.substring(0, 2);
        String cleanBody = this.messageBody.replaceAll("[^a-zA-Z\\s]", "");
        String[] words = cleanBody.trim().split("\\s+");

        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();

        return firstTwoDigits + ":" + this.numSentMessages + ":" + firstWord + lastWord;
    }

    // Processes the user's choice: 1 = Send, 2 = Disregard, 3 = Store
    public String SentMessage(int choice) {
        if (choice == 1) {
            this.actionStatus = "Send Message";
            totalMessagesCounter++;
            return "Message successfully sent";
        } else if (choice == 2) {
            this.actionStatus = "Disregard Message";
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            this.actionStatus = "Store Message";
            storeMessage();
            return "Message successfully stored";
        } else {
            this.actionStatus = "Invalid";
            return "Invalid choice.";
        }
    }

    // Formats full message details for the execution history display
    public String printMessages() {
        return "Message ID: " + this.messageID
                + " | Message Hash: " + this.messageHash
                + " | Recipient: " + this.recipientNumber
                + " | Status: " + this.actionStatus
                + " | Message: " + this.messageBody;
    }

    // Returns the total global tracked message objects counter
    public int returnTotalMessages() {
        return totalMessagesCounter;
    }

    // Saves stored message details in JSON format to storedMessages.json
    public void storeMessage() {
        String escapedMsg = this.messageBody.replace("\"", "\\\"");
        String jsonLine = "{\"messageID\":\"" + this.messageID + "\","
                + "\"messageHash\":\"" + this.messageHash + "\","
                + "\"recipient\":\"" + this.recipientNumber + "\","
                + "\"message\":\"" + escapedMsg + "\"}";
        try {
            Path path = Paths.get("storedMessages.json");
            Files.write(path, (jsonLine + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error saving message to JSON: " + e.getMessage());
        }
    }

    // Getter methods used by MessageManager and JUnit tests
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getActionStatus() {
        return actionStatus;
    }
}