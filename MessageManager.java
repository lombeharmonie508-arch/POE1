import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {
    // Dynamic parallel arrays for Part 3 (no hard-coded values)
    private String[] sentMessages;
    private String[] disregardedMessages;
    private String[] storedMessages;
    private String[] messageHashes;
    private String[] messageIDs;
    private String[] recipients;
    private String[] senders;
    private String[] flags;

    private int totalCount = 0;
    private int sentCount = 0;
    private int disregardedCount = 0;
    private int storedCount = 0;

    public MessageManager(int capacity) {
        int initialSize = Math.max(capacity, 5);
        this.sentMessages = new String[initialSize];
        this.disregardedMessages = new String[initialSize];
        this.storedMessages = new String[initialSize];
        this.messageHashes = new String[initialSize];
        this.messageIDs = new String[initialSize];
        this.recipients = new String[initialSize];
        this.senders = new String[initialSize];
        this.flags = new String[initialSize];
    }

    // Dynamically populates parallel arrays as messages are created
    public void addMessage(String sender, Message msg, String flag) {
        if (totalCount >= messageIDs.length) {
            expandArrays();
        }

        messageIDs[totalCount] = msg.getMessageID();
        messageHashes[totalCount] = msg.getMessageHash();
        recipients[totalCount] = msg.getRecipientNumber();
        senders[totalCount] = sender;
        flags[totalCount] = flag;
        totalCount++;

        if ("Sent".equalsIgnoreCase(flag)) {
            sentMessages[sentCount++] = msg.getMessageBody();
        } else if ("Disregard".equalsIgnoreCase(flag)) {
            disregardedMessages[disregardedCount++] = msg.getMessageBody();
        } else if ("Stored".equalsIgnoreCase(flag)) {
            storedMessages[storedCount++] = msg.getMessageBody();
        }
    }

    // Expands all parallel arrays when capacity is reached
    private void expandArrays() {
        int newSize = messageIDs.length + 10;
        sentMessages = Arrays.copyOf(sentMessages, newSize);
        disregardedMessages = Arrays.copyOf(disregardedMessages, newSize);
        storedMessages = Arrays.copyOf(storedMessages, newSize);
        messageHashes = Arrays.copyOf(messageHashes, newSize);
        messageIDs = Arrays.copyOf(messageIDs, newSize);
        recipients = Arrays.copyOf(recipients, newSize);
        senders = Arrays.copyOf(senders, newSize);
        flags = Arrays.copyOf(flags, newSize);
    }

    // Reads stored messages from storedMessages.json into the storedMessages array
    public void loadStoredMessagesFromJSON(String filePath, String defaultSender) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) return;

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String id = extractJsonValue(line, "messageID");
                String rec = extractJsonValue(line, "recipient");
                String body = extractJsonValue(line, "message");

                if (!id.isEmpty() && !containsMessageID(id)) {
                    Message loaded = new Message(id, totalCount, rec, body);
                    addMessage(defaultSender, loaded, "Stored");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    // Extracts a string field value from a JSON line
    private String extractJsonValue(String jsonLine, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(jsonLine);
        if (matcher.find()) {
            return matcher.group(1).replace("\\\"", "\"");
        }
        return "";
    }

    // Checks if a Message ID is already loaded in memory
    private boolean containsMessageID(String id) {
        for (int i = 0; i < totalCount; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(id)) return true;
        }
        return false;
    }

    // 2a. Display sender and recipient of all stored messages
    public String displaySendersAndRecipients() {
        StringBuilder sb = new StringBuilder("--- Stored Messages: Senders & Recipients ---\n");
        boolean found = false;
        for (int i = 0; i < totalCount; i++) {
            if ("Stored".equalsIgnoreCase(flags[i])) {
                sb.append("Sender: ").append(senders[i])
                        .append(" | Recipient: ").append(recipients[i]).append("\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No stored messages found.";
    }

    // 2b. Display the longest message across sent and stored messages
    public String getLongestMessage() {
        String longest = "";
        for (int i = 0; i < totalCount; i++) {
            if ("Sent".equalsIgnoreCase(flags[i]) || "Stored".equalsIgnoreCase(flags[i])) {
                String currentText = getMessageTextAt(i);
                if (currentText.length() > longest.length()) {
                    longest = currentText;
                }
            }
        }
        return longest;
    }

    // 2c. Search for a Message ID and return the corresponding recipient and message
    public String searchByMessageID(String searchID) {
        for (int i = 0; i < totalCount; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(searchID)) {
                return recipients[i] + ", " + getMessageTextAt(i);
            }
        }
        return "Message ID not found.";
    }

    // 2d. Search all messages sent or stored regarding a particular recipient
    public String searchByRecipient(String targetRecipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalCount; i++) {
            if (recipients[i] != null && recipients[i].equals(targetRecipient)
                    && ("Sent".equalsIgnoreCase(flags[i]) || "Stored".equalsIgnoreCase(flags[i]))) {
                if (sb.length() > 0) sb.append(", ");
                sb.append("\"").append(getMessageTextAt(i)).append("\"");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No messages found for recipient.";
    }

    // 2e. Delete a message using its Message Hash
    public String deleteByMessageHash(String targetHash) {
        for (int i = 0; i < totalCount; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(targetHash)) {
                String deletedText = getMessageTextAt(i);
                String deletedFlag = flags[i];

                if ("Stored".equalsIgnoreCase(deletedFlag)) {
                    removeFromSubArray(storedMessages, deletedText, storedCount--);
                } else if ("Sent".equalsIgnoreCase(deletedFlag)) {
                    removeFromSubArray(sentMessages, deletedText, sentCount--);
                } else if ("Disregard".equalsIgnoreCase(deletedFlag)) {
                    removeFromSubArray(disregardedMessages, deletedText, disregardedCount--);
                }

                for (int j = i; j < totalCount - 1; j++) {
                    messageIDs[j] = messageIDs[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                    recipients[j] = recipients[j + 1];
                    senders[j] = senders[j + 1];
                    flags[j] = flags[j + 1];
                }
                totalCount--;
                return "Message \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // Removes an item from a flag-specific sub-array and shifts remaining elements
    private void removeFromSubArray(String[] arr, String text, int count) {
        for (int i = 0; i < count; i++) {
            if (arr[i] != null && arr[i].equals(text)) {
                for (int j = i; j < count - 1; j++) {
                    arr[j] = arr[j + 1];
                }
                arr[count - 1] = null;
                break;
            }
        }
    }

    // 2f. Display a full report listing all details of stored messages
    public String displayFullStoredReport() {
        StringBuilder sb = new StringBuilder("=== STORED MESSAGES FULL REPORT ===\n");
        boolean found = false;
        for (int i = 0; i < totalCount; i++) {
            if ("Stored".equalsIgnoreCase(flags[i])) {
                sb.append("Message Hash: ").append(messageHashes[i]).append("\n")
                        .append("Message ID: ").append(messageIDs[i]).append("\n")
                        .append("Sender: ").append(senders[i]).append("\n")
                        .append("Recipient: ").append(recipients[i]).append("\n")
                        .append("Message: ").append(getMessageTextAt(i)).append("\n")
                        .append("-----------------------------------\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No stored messages available.";
    }

    // Displays a report of all sent messages (Message Hash, Recipient, Message)
    public String displaySentReport() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalCount; i++) {
            if ("Sent".equalsIgnoreCase(flags[i])) {
                sb.append("Message Hash: ").append(messageHashes[i]).append("\n")
                        .append("Recipient: ").append(recipients[i]).append("\n")
                        .append("Message: ").append(getMessageTextAt(i)).append("\n\n");
            }
        }
        return sb.toString().trim();
    }

// Retrieves the message text corresponding to a global index across sub-arrays
private String getMessageTextAt(int globalIndex) {
    int sIdx = 0, dIdx = 0, stIdx = 0;
    for (int i = 0; i <= globalIndex; i++) {
        if ("Sent".equalsIgnoreCase(flags[i])) {
            if (i == globalIndex) return sentMessages[sIdx];
            sIdx++;
        } else if ("Disregard".equalsIgnoreCase(flags[i])) {
            if (i == globalIndex) return disregardedMessages[dIdx];
            dIdx++;
        } else if ("Stored".equalsIgnoreCase(flags[i])) {
            if (i == globalIndex) return storedMessages[stIdx];
            stIdx++;
        }
    }
    return "";
}
    // Helper methods for JUnit unit tests
    public String[] getSentMessagesArray() {
        return Arrays.copyOf(sentMessages, sentCount);
    }
    public String getMessageIDByContent(String text) {
        for (int i = 0; i < totalCount; i++) {
            if (getMessageTextAt(i).equals(text)) return messageIDs[i];
        }
        return null;
    }
    public String getMessageHashByContent(String text) {
        for (int i = 0; i < totalCount; i++) {
            if (getMessageTextAt(i).equals(text)) return messageHashes[i];
        }
        return null;
    }
}