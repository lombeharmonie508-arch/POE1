import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {
    private MessageManager manager;

    @BeforeEach
    public void setUp() {
        manager = new MessageManager(5);

        // Populate arrays dynamically using the exact PoE test data (Messages 1 to 5)
        Message m1 = new Message(0, "+27834557896", "Did you get the cake?");
        Message m2 = new Message(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message m3 = new Message(2, "+27834484567", "Yohoooo, I am at your gate.");
        Message m4 = new Message(3, "0838884567", "It is dinner time !");
        Message m5 = new Message(4, "+27838884567", "Ok, I am leaving without you.");

        manager.addMessage("+27831112222", m1, "Sent");
        manager.addMessage("+27831112222", m2, "Stored");
        manager.addMessage("+27831112222", m3, "Disregard");
        manager.addMessage("+27831112222", m4, "Sent");
        manager.addMessage("+27831112222", m5, "Stored");
    }

    // 1. Verify that the Sent Messages array is correctly populated
    @Test
    public void testSentMessagesArrayPopulated() {
        String[] expected = {"Did you get the cake?", "It is dinner time !"};
        assertArrayEquals(expected, manager.getSentMessagesArray());
    }

    // 2. Display the longest message across test messages 1 to 4
    @Test
    public void testDisplayLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, manager.getLongestMessage());
    }

    // 3. Search for a message by Message ID (Test Data: Message 4)
    @Test
    public void testSearchForMessageID() {
        String msg4ID = manager.getMessageIDByContent("It is dinner time !");
        assertEquals("0838884567, It is dinner time !", manager.searchByMessageID(msg4ID));
    }

    // 4. Search all messages sent or stored regarding a particular recipient (+27838884567)
    @Test
    public void testSearchByRecipient() {
        String expected = "\"Where are you? You are late! I have asked you to be on time.\", \"Ok, I am leaving without you.\"";
        assertEquals(expected, manager.searchByRecipient("+27838884567"));
    }

    // 5. Delete a message using its Message Hash (Test Data: Test Message 2)
    @Test
    public void testDeleteByMessageHash() {
        String msg2Hash = manager.getMessageHashByContent("Where are you? You are late! I have asked you to be on time.");
        String expected = "Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        assertEquals(expected, manager.deleteByMessageHash(msg2Hash));
    }

    // 6. Display Report showing all sent messages with Message Hash, Recipient, and Message
    @Test
    public void testDisplayReport() {
        String report = manager.displaySentReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
        assertTrue(report.contains("Recipient: 0838884567"));
        assertTrue(report.contains("Message: It is dinner time !"));
    }
}