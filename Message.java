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

    // Generates a random 10-digit number formatted as a string for tracking
    private String generateRandom10DigitID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Checks that the generated message ID layout does not exceed 10 digits
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Validates that the body message size does not exceed the 250 character limit
    public String validateMessageLength() {
        if (this.messageBody.length() <= 250) {
            return "Message ready to send.";
        } else {
            return "Please enter a message of less than 250 characters.";
        }
    }

    // Validates South African phone format matching the strict international code layout
    public String checkRecipientCell() {
        // Regex patterns for a mandatory +27 prefix followed by exactly 9 digits
        String regex = "^\\+27\\d{9}$";
        if (Pattern.matches(regex, this.recipientNumber)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an International code. Please correct the number and try again.";
        }
    }

    // Formats hash key -> [First 2 digits of ID]:[Index Counter]:[FIRSTLAST WORDS IN ALL CAPS]
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

    // Processes the action choices and manages the accumulated execution total counts
    public String SentMessage(int choice) {
        if (choice == 1) {
            this.actionStatus = "Send Message";
            totalMessagesCounter++;
            return "Message successfully sent";
        } else if (choice == 3) {
            this.actionStatus = "Store Message";
            totalMessagesCounter++;
            return "Message successfully stored";
        } else {
            this.actionStatus = "Disregard Message";
            return "Press 0 to delete the message.";
        }
    }

    // Returns a formatted log tracking string line item for the execution history display
    public String printMessages() {
        return "Message ID: " + this.messageID +
                " | Message Hash: " + this.messageHash +
                " | Recipient: " + this.recipientNumber +
                " | Status: " + this.actionStatus +
                " | Message: " + this.messageBody;
    }

    // Returns the total global tracked message objects counter
    public int returnTotalMessages() {
        return totalMessagesCounter;
    }

    // Research target placeholder mapping outputs directly into mock JSON layout notation
    public void storeMessage() {
        String json = "{\n" +
                "  \"messageID\": \"" + this.messageID + "\",\n" +
                "  \"recipient\": \"" + this.recipientNumber + "\",\n" +
                "  \"messageHash\": \"" + this.messageHash + "\"\n" +
                "}";
        System.out.println(json);
    }
}