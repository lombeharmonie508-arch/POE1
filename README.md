
# QuickChat - User Registration, Login & Messaging System

A comprehensive Java console application featuring a secure user session authentication architecture and an advanced text message management platform with automated validation, metadata hashing, mock JSON exportation, and strict multi-class unit testing verification (JUnit 5).

## Features

1. User Registration & Authentication (Login.java)
 *State Management*: Persists user session records (firstName, lastName, username, password, cellPhoneNumber) directly within volatile memory components.
*Input Enforcement*: Continuous operational loops prompt the user until all structural security and formatting requirements are met.
*Username Formatting: Ensures constraints where a user identification string must be **5 characters or less* and strictly feature an underscore (_) symbol.
 *Complex Password Validation Engine*: Analyzes character structures iteratively to ensure high security thresholds:
  * Mandatory baseline length of *at least 8 characters*.
  * Contains at least one *uppercase letter* (Character.isUpperCase).
  * Contains at least one *numerical digit* (Character.isDigit).
  * Contains at least one *special character* matched against an internal target matrix ([-!@#\$%^&*()_+{}|:\"<>?`1\\-=\\[\\]\\\\ Lord;',./]).

2. Login Management
*Credentials Lookup Verification*: Evaluates runtime console credential strings against cached data array parameters.
*Precise Status Reporting*: Emits user-specific status string events confirming system acceptance or custom error sequences based on conditional outcomes:
*Success Route*: Returns a custom interface sequence matching formatting requirements: "Welcome <firstName>, <lastName> It is great to see you again!".
*Failure Route*: Emits clear alerts: "Username or password incorrect, please try again.".

3. QuickChat Messaging Engine (Message.java)
*Bulk Processing*: Captures the exact total number of messages the user intends to handle sequentially within the loop cycle.
*Automated Content Control*:
*Length Boundary: Enforces a strict maximum space boundary of **250 characters* per message body.
*International Telephony Validation: Utilizes explicit Regex parameters targeting South African phone formats requiring a mandatory +27 country code prefix followed by exactly **9 digits* (^\+27\d{9}$).
*Automatic Metadata Generation*:
*Message ID: Auto-generates a unique **10-digit random tracking string* per instantiation.
*Custom Message Hash*: Creates a structured key token based on the specific design rule: [First 2 digits of ID]:[Index Counter]:[FIRST & LAST WORD IN ALL CAPS].
*Action Selector Workflow*: Processes messaging object allocations based on a sub-menu state selector:
Option 1 (Send)*: Assigns status "Send Message", increments tracking logs, and registers the data.
Option 3 (Store)*: Assigns status "Store Message" for safe local database replication.
Other Options (Disregard)*: Flags the data block as "Disregard Message".

4. Telemetry, Formatting & Logs
* *Formatted Log Tracking*: Compiles human-readable execution history outputs listing ID, custom hash, recipient phone number, transmission status, and body content.
* *Global Tracking Counters*: Maintains a synchronized state of active processed components across the runtime session.
* *JSON Research Exportation*: Outputs data directly mapped into a structured mock JSON payload syntax for external database compatibility.

#A.Automated Testing Suite (JUnit 5)

The entire business logic repository is guarded by comprehensive unit tests to isolate bugs and secure code requirements.

### A. Authentication Tests (LoginTest.java)
** testUserNameCorrect / testUserNameIncorrect*: Ensures usernames conform to the length limit (≤ 5 characters) and correctly identify the mandatory underscore constraint.
** testPasswordComplexityCorrect / testPasswordComplexityIncorrect*: Verifies password structural checks against weak passwords lacking uppercase letters, numbers, or symbols.
* *testCellPhoneNumberCorrect / testCellPhoneNumberIncorrect*: Enforces verification boundaries for international South African phone formatting (+27), rejecting numbers missing the global routing code.
* *testLoginStatusSuccess*: Tests matching login credential validations and asserts precise custom welcome sequence string formatting.

#B. Messaging Tests (MessageTest.java)
* *testMessageLength_Success*: Validates character limit controls ensuring texts below the threshold pass smoothly.
* *testRecipientCell_Success / testRecipientCell_Failure*: Confirms valid routing outcomes for international phone records, and checks error capturing on invalid formats.
* *testSentMessage_Options*: Tests sub-menu state machines:
  * *Choice 1*: Asserts target confirmation for standard transmission messaging flags.
  * *Choice 2*: Confirms safe drop handling indicators and prompt deletion notifications.
  * *Choice 3*: Validates internal storage flag allocations.

## How to Run

1. Ensure you have *Java JDK* and the *JUnit 5 library* available on your machine.
2. Compile all internal source files simultaneously:
   bash
   javac Main.java Login.java Message.java
   
3. Boot the application console:
   bash
   java Main
