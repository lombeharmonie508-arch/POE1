
import java.util.regex.Pattern;

public class Login {

    // Attributes to save user data in memory (used by the Main class)
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // Getter method to access cell phone number and clear the unused field warning
    public String getCellPhoneNumber() {
        return this.cellPhoneNumber;
    }

    // Getters and Setters for interaction with Main class
    public void setFirstAndLastName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setCredentials(String username, String password, String cellPhoneNumber) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // 1. Method to check the username format (Max 5 characters and must contain an underscore)
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    // 2. Method to check password complexity rules
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        String specialChars = "[~!@#$%^&*()_+{}|:\"<>?`\\-=\\[\\]\\;',./]";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (specialChars.contains(String.valueOf(c))) hasSpecial = true;
        }

        return hasUppercase && hasDigit && hasSpecial;
    }

    // 3. Method to check the South African phone number format using a Regex
    public boolean checkCellPhoneNumber(String cellPhone) {
        if (cellPhone == null) return false;
        String phoneRegex = "^\\+27\\d{9}$";
        return Pattern.matches(phoneRegex, cellPhone);
    }

    // 4. Registration method required by the brief (returns precise status messaging)
    public String registerUser(String username, String password, String cellPhone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters long.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "Username successfully captured.";
    }

    // 5. Method to verify entered login details against saved credentials
    public boolean loginUser(String enteredUser, String enteredPass) {
        if (enteredUser == null || enteredPass == null) return false;
        return enteredUser.equals(this.username) && enteredPass.equals(this.password);
    }

    // 6. Method to return the exact login status message with strict punctuation compliance
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + this.firstName + ", " + this.lastName + " It is great to see you again!";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}