
       import java.util.Scanner;

            void main(String[] args){
                    // Prevent unused parameter warning
                    if(args.length>0){
                    System.out.println("Arguments provided.");
                    }

                    Scanner sc=new Scanner(System.in);
                    Login userLogin=new Login();

                    System.out.print("--- Registration ---");
                    System.out.print("Please enter your first name: ");
                    String firstName=sc.nextLine();
                    System.out.print("Please enter your last name: ");
                    String lastName=sc.nextLine();

                    userLogin.setFirstAndLastName(firstName,lastName);

                    String username;
                    while(true){
                    System.out.print("Please enter your username: ");
                    username=sc.nextLine();
                    if(userLogin.checkUserName(username)){
                    System.out.print("Username successfully captured.");
                    break;
                    }else{
                    System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters long.");
                    }
                    }

                    String phoneNumber;
                    while(true){
                    System.out.print("Please enter your phone number: ");
                    phoneNumber=sc.nextLine();
                    if(userLogin.checkCellPhoneNumber(phoneNumber)){
                    System.out.print("Cell number successfully captured.");
                    break;
                    }else{
                    System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
                    }
                    }

                    String password;
                    while(true){
                    System.out.print("Please enter your password: ");
                    password=sc.nextLine();
                    if(userLogin.checkPasswordComplexity(password)){
                    System.out.print("Password successfully captured.");
                    break;
                    }else{
                    System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                    }
                    }

                    userLogin.setCredentials(username,password,phoneNumber);

                    String status=userLogin.registerUser(username,password,phoneNumber);
                    System.out.print("----------------- "+status+" ------------------");
                    System.out.print("\n--- Login part ---");

                    boolean loginResult=false;

// The loop repeats AS LONG AS loginResult remains false
                    while(!loginResult){
                    System.out.print("Please login your details:");
                    System.out.print("Username: ");
                    String loginUser=sc.nextLine();

                    System.out.print("Password: ");
                    String loginPass=sc.nextLine();

                    loginResult=userLogin.loginUser(loginUser,loginPass);

                    System.out.print("--------------------Login Status--------------------");
                    System.out.print(userLogin.returnLoginStatus(loginResult));
                    System.out.println(); // Adds a blank line for cleaner spacing if it loops
                    }

// This line executes only AFTER a successful login
                    System.out.print("Verified Phone: "+userLogin.getCellPhoneNumber());


// ==========================================
                //  START OF PART 2 (CONTINUITY)
                // ==========================================
                // Mandatory welcome phrase verified by assignment prompt matching
                System.out.print("Welcome to QuickChat.");

                System.out.print("How many messages do you wish to enter? ");
                int totalLoops = sc.nextInt();
                sc.nextLine(); // Clear the scanner buffer stream line tracking

                ArrayList<Message> messageList = new ArrayList<>();

                // Collect message variables sequentially matching user loop request limit size
                for (int i = 0; i < totalLoops; i++) {
                    System.out.print("\n--- Entering Details for Message " + (i + 1) + " ---");

                    System.out.print("Enter Recipient Cell Number: ");
                    String cell = sc.nextLine();

                    System.out.print("Enter Message Content: ");
                    String content = sc.nextLine();

                    // Creates message object element triggering auto hashes
                    Message msg = new Message(i, cell, content);

                    // Display verification check feedback items to console
                    System.out.print(msg.checkRecipientCell());
                    System.out.print(msg.validateMessageLength());

                    // Inner processing sub-menu action choices selection
                    System.out.print("\nSelect Action:\n1) Send Message\n2) Disregard Message\n3) Store Message");
                    System.out.print("Choice: ");
                    int menuAction = sc.nextInt();
                    sc.nextLine(); // Clear scanner buffer track

                    System.out.print

                            (msg.SentMessage(menuAction));
                    messageList.add(msg);
                }

                // Top-level application functional looping choices menu interface
                while (true) {
                    System.out.print("\n--- Numeric Menu Selection ---");
                    System.out.print("1) Show all final message status details");
                    System.out.print("2) Show recently sent messages");
                    System.out.print("3) Quit");
                    System.out.print("Select choice: ");
                    int option = sc.nextInt();

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
                    }
                }

                sc.close();
            }

