
       import java.util.Scanner;

            void main(String[] args) {
                // Prevent unused parameter warning
                if (args.length > 0) {
                    System.out.println("Arguments provided.");
                }

                Scanner sc = new Scanner(System.in);
                Login userLogin = new Login();

                System.out.print("--- Registration ---");
                System.out.print("Please enter your first name: ");
                String firstName = sc.nextLine();
                System.out.print("Please enter your last name: ");
                String lastName = sc.nextLine();

                userLogin.setFirstAndLastName(firstName, lastName);

                String username;
                while (true) {
                    System.out.print("Please enter your username: ");
                    username = sc.nextLine();
                    if (userLogin.checkUserName(username)) {
                        System.out.print("Username successfully captured.");
                        break;
                    } else {
                        System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters long.");
                    }
                }

                String phoneNumber;
                while (true) {
                    System.out.print("Please enter your phone number: ");
                    phoneNumber = sc.nextLine();
                    if (userLogin.checkCellPhoneNumber(phoneNumber)) {
                        System.out.print("Cell number successfully captured.");
                        break;
                    } else {
                        System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
                    }
                }

                String password;
                while (true) {
                    System.out.print("Please enter your password: ");
                    password = sc.nextLine();
                    if (userLogin.checkPasswordComplexity(password)) {
                        System.out.print("Password successfully captured.");
                        break;
                    } else {
                        System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                    }
                }

                userLogin.setCredentials(username, password, phoneNumber);

                String status = userLogin.registerUser(username, password, phoneNumber);
                System.out.print("----------------- " + status + " ------------------");
System.out.print("\n--- Login part ---");

            boolean loginResult = false;

// The loop repeats AS LONG AS loginResult remains false
while (!loginResult) {
                    System.out.print("Please login your details:");
    System.out.print("Username: ");
            String loginUser = sc.nextLine();

    System.out.print("Password: ");
            String loginPass = sc.nextLine();

            loginResult = userLogin.loginUser(loginUser, loginPass);

    System.out.print("--------------------Login Status--------------------");
    System.out.print(userLogin.returnLoginStatus(loginResult));
                    System.out.println(); // Adds a blank line for cleaner spacing if it loops
}

// This line executes only AFTER a successful login
        System.out.print("Verified Phone: " + userLogin.getCellPhoneNumber());

                    sc.close();
                    }
