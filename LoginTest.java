
import static org.junit.jupiter.api.Assertions.*;
        import org.junit.jupiter.api.Test;

public class LoginTest {

    private final Login l = new Login();

    @Test
    public void testUserNameCorrect() {
        // Test data from the brief: "kyl_1" is valid
        assertTrue(l.checkUserName("kyl_1"));
    }

    @Test
    public void testUserNameIncorrect() {
        // Test data from the brief: "kyle!!!!!!!" is invalid (too long, no underscore)
        assertFalse(l.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordComplexityCorrect() {
        // Test data from the brief: Valid complex password
        assertTrue(l.checkPasswordComplexity("Ch&&&sec@ke99!"));
    }

    @Test
    public void testPasswordComplexityIncorrect() {
        // Test data from the brief: Invalid password (no uppercase, numbers, or symbols)
        assertFalse(l.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneNumberCorrect() {
        // Test data from the brief: Valid South African international format
        assertTrue(l.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneNumberIncorrect() {
        // Test data from the brief: Invalid format (missing international code)
        assertFalse(l.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginStatusSuccess() {
        // Initializing the object state with valid mock data for login testing
        l.setFirstAndLastName("Kyle", "Smith");
        l.setCredentials("kyl_1", "Ch&&&sec@ke99!", "+27838968976");

        // Verifying that authentication returns true for matching credentials
        assertTrue(l.loginUser("kyl_1", "Ch&&&sec@ke99!"));

        // Verifying that the return message exactly matches the brief's punctuation requirements
        String expectedMessage = "Welcome Kyle, Smith it is great to see you again.";
        assertEquals(expectedMessage, l.returnLoginStatus(true));
    }
}