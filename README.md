# Banking Application README

## Overview
This application is a Java-based banking system that allows users to manage bank accounts, view transactions, and update customer information. It utilizes a MySQL database for data storage and JDBC for database connectivity.

## Features
- **Customer Management**:
  - Create, update, and delete customer profiles.
  - View customer details including name, address, email, and phone number.

- **Account Management**:
  - Create checking and savings accounts for customers.
  - Perform transactions such as deposits, withdrawals, and transfers between accounts.

- **Transaction Management**:
  - Record and view transaction history for each account.
  - Retrieve transaction details including date, type, amount, and description.

- **GUI Interface**:
  - Provides a user-friendly graphical interface built using Java Swing.
  - Allows intuitive navigation and interaction with various functionalities.

## Technologies Used
- **Java**: Core programming language for application logic.
- **MySQL**: Database management system for storing customer and account data.
- **JDBC (Java Database Connectivity)**: API for connecting Java applications to databases.
- **Java Swing**: GUI toolkit for developing desktop applications in Java.

## Setup Instructions
1. **Database Setup**:
   - Install MySQL Server and create a database named `banking_app`.
   - Execute the SQL scripts provided in the `sql` folder to create tables (`customers`, `accounts`, `transactions`) and insert sample data.

2. **Application Setup**:
   - Clone the repository from https://github.com/ItsHugo28/bankingapp/tree/main.
   - Open the project in your Java IDE (e.g., IntelliJ IDEA, Eclipse).
   - Configure JDBC connection settings in `src/Assignment6Model/AccountDataConnection.java` and `src/Assignment6Model/TransactionDataConnection.java`.

3. **Run the Application**:
   - Compile and run the `Main.java` class to start the application.
   - Follow the GUI prompts to interact with the banking functionalities.

## Usage
- **Customer Operations**:
  - Use the Customer Management GUI to add, update, or delete customer information.
  - View and edit customer addresses.

- **Account Operations**:
  - Navigate through Account Management GUI to create checking or savings accounts for customers.
  - Perform transactions such as deposits, withdrawals, and transfers.

- **Transaction History**:
  - Access Transaction Management GUI to view transaction history for each account.
  - Filter transactions by account ID, date range, or transaction type.

## Troubleshooting
- **Database Connectivity Issues**:
  - Ensure MySQL Server is running and configured correctly.
  - Check JDBC connection string and credentials in `AccountDataConnection.java` and `TransactionDataConnection.java`.

- **GUI Display Issues**:
  - Verify Java Swing components and layout settings in respective GUI classes (`CustomerDetail.java`, `AccountDetail.java`, `TransactionSummary.java`).

