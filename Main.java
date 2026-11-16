import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login userLogin = new Login();

        System.out.print("=== Registration ===");
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
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        String phoneNumber;
        while (true) {
            System.out.print("Please enter your phone number: ");
            phoneNumber = sc.nextLine();
            if (userLogin.checkCellPhoneNumber(phoneNumber)) {
                System.out.print("Cell phone number successfully captured.");
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
        System.out.print(status);
        System.out.print("\n--- Login part ---");

        boolean loginResult = false;
        while (!loginResult) {
            System.out.print("Please login your details:");
            System.out.print("Username: ");
            String loginUser = sc.nextLine();
            System.out.print("Password: ");
            String loginPass = sc.nextLine();

            loginResult = userLogin.loginUser(loginUser, loginPass);
            System.out.print("---------- Login Status ----------");
            System.out.print(userLogin.returnLoginStatus(loginResult));
            System.out.println();
        }

        System.out.print("Verified Phone: " + userLogin.getCellPhoneNumber());

        // ================= START OF PART 2 & PART 3 =================
        System.out.print("Welcome to QuickChat.");

        System.out.print("How many messages do you wish to enter? ");
        int totalLoops = sc.nextInt();
        sc.nextLine();

        ArrayList<Message> messageList = new ArrayList<>();
        MessageManager manager = new MessageManager(totalLoops);

        for (int i = 0; i < totalLoops; i++) {
            System.out.print("\n--- Entering Details for Message " + (i + 1) + " ---");
            String cell;
            while (true) {
                System.out.print("Enter Recipient Cell Number: ");
                cell = sc.nextLine();
                Message tempCheck = new Message(i, cell, "");
                String cellStatus = tempCheck.checkRecipientCell();
                System.out.print(cellStatus);
                if (cellStatus.equals("Cell phone number successfully captured.")) {
                    break;
                }
            }

            String content;
            Message msg;
            while (true) {
                System.out.print("Enter Message Content: ");
                content = sc.nextLine();
                msg = new Message(i, cell, content);
                String lenStatus = msg.validateMessageLength();
                System.out.print(lenStatus);
                if (lenStatus.equals("Message ready to send.")) {
                    break;
                }
            }
            System.out.print("\nSelect Action:\n1) Send Message\n2) Disregard Message\n3) Store Message");
            System.out.print("Choice: ");
            int menuAction = sc.nextInt();
            sc.nextLine();

            System.out.print(msg.SentMessage(menuAction));
            messageList.add(msg);

            // Dynamically populate Part 3 arrays based on user selection
            if (menuAction == 1) {
                manager.addMessage(userLogin.getCellPhoneNumber(), msg, "Sent");
            } else if (menuAction == 2) {
                manager.addMessage(userLogin.getCellPhoneNumber(), msg, "Disregard");
            } else if (menuAction == 3) {
                manager.addMessage(userLogin.getCellPhoneNumber(), msg, "Stored");
            }
        }

        // Load stored messages from JSON file into the storedMessages array
        manager.loadStoredMessagesFromJSON("storedMessages.json", userLogin.getCellPhoneNumber());

        // Top-level application functional looping choices menu interface
        while (true) {
            System.out.print("\n--- Numeric Menu Selection ---");
            System.out.print("1) Show all final message status details");
            System.out.print("2) Show recently sent messages");
            System.out.print("3) Quit");
            System.out.print("4) Stored Messages");
            System.out.print("Select choice: ");
            int option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                System.out.println("\n--- Full Message Log History ---");
                for (Message m : messageList) {
                    System.out.println(m.printMessages());
                }
                if (!messageList.isEmpty()) {
                    System.out.println("Total Accumulated Messages Processed Count: " + messageList.get(0).returnTotalMessages());
                }
            } else if (option == 2) {
                System.out.println("Coming Soon.");
            } else if (option == 3) {
                System.out.print("Exiting Application. Goodbye!");
                break; // Gracefully terminates the application returning exit code 0
            } else if (option == 4) {
                // Part 3 Stored Messages sub-menu (options a to f)
                while (true) {
                    System.out.print("\n=== STORED MESSAGES MENU ===");
                    System.out.print("a) Display sender and recipient of all stored messages");
                    System.out.print("b) Display the longest message");
                    System.out.print("c) Search for a message ID");
                    System.out.print("d) Search for all messages sent or stored for a recipient");
                    System.out.print("e) Delete a message using the message hash");
                    System.out.print("f) Display full report of all stored messages");
                    System.out.print("g) Return to Main Menu");
                    System.out.print("Select option (a-g): ");
                    String subOption = sc.nextLine().trim().toLowerCase();

                    if (subOption.equals("a")) {
                        System.out.println(manager.displaySendersAndRecipients());
                    } else if (subOption.equals("b")) {
                        System.out.println("Longest Message: " + manager.getLongestMessage());
                    } else if (subOption.equals("c")) {
                        System.out.print("Enter Message ID: ");
                        String searchId = sc.nextLine().trim();
                        System.out.println(manager.searchByMessageID(searchId));
                    } else if (subOption.equals("d")) {
                        System.out.print("Enter Recipient Cell Number: ");
                        String searchRec = sc.nextLine().trim();
                        System.out.println(manager.searchByRecipient(searchRec));
                    } else if (subOption.equals("e")) {
                        System.out.print("Enter Message Hash to delete: ");
                        String delHash = sc.nextLine().trim();
                        System.out.println(manager.deleteByMessageHash(delHash));
                    } else if (subOption.equals("f")) {
                        System.out.println(manager.displayFullStoredReport());
                    } else if (subOption.equals("g")) {
                        break;
                    } else {
                        System.out.println("Invalid option. Please choose a-g.");
                    }
                }
            }
        }

        sc.close();
    }
}
