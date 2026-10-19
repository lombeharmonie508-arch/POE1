
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLength_Success() {
        // Checking formatting returns for character length limits below 250 words limits
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.validateMessageLength());
    }

    @Test
    public void testRecipientCell_Success() {
        // Verifying validation logic handles international South Africa numbers cleanly
        Message msg = new Message(0, "+27718693002", "Valid text summary info");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientCell_Failure() {
        // Confirms formatting errors catch phone entries lacking national code elements
        Message msg = new Message(1, "0857575889", "Invalid context summary info");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an International code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testSentMessage_Options() {
        Message msg = new Message(0, "+27718693002", "Checking choices inputs validation values");

        // Asserting choice integer option 1 registers a clean Send text phrase
        assertEquals("Message successfully sent", msg.SentMessage(1));

        // Asserting choice integer option 2 registers Delete warning text strings
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));

        // Asserting choice integer option 3 maps Storage status statements
        assertEquals("Message successfully stored", msg.SentMessage(3));
    }
}