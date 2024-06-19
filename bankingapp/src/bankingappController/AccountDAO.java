/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment6Controller;

import Assignment6Controller.*;
import Assignment6Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karunmehta
 */
public class    AccountDAO implements DAOInterface<BankAccount>{

    static Connection connection = null;
    PreparedStatement pStatement;
    ResultSet result; 
    final String checking = "CH";
    final String saving = "SV";

    AccountDAO() {

        connection = DataConnection.getDBConnection();

    }
               
    // Method to disconnect from the database
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Method to insert a user into the database
    @Override
    public int create(BankAccount act) throws SQLException {
        
        int res = -1;
        pStatement = connection.prepareStatement(AccountDataConnection.getInsert());
        pStatement.setInt(1, act.getAccountNum());
        pStatement.setDouble(2, act.getBalance());
        res = pStatement.executeUpdate();
        disconnect();
        
        return res;
    }

    // Method to retrieve a user from the database by ID
    @Override
    public BankAccount get(int anID) throws SQLException {

        pStatement = connection.prepareStatement(AccountDataConnection.getSelect());
        pStatement.setInt(1,anID);
        result = pStatement.executeQuery();
        
        BankAccount updatedAct = null;
        if (result.next()) {
            if(result.getString("acct_type").equalsIgnoreCase(checking))
                updatedAct = new CheckingAccount( result.getInt("acct_num"));
            else
                updatedAct = new SavingsAccount( result.getInt("acct_num"));
            
            updatedAct.setBalance(result.getFloat("balance"));
            LocalDate ld = createLocalDate(result.getString("create_date"));
            updatedAct.setCreateDate(ld);
        }
      
        return updatedAct;
    }

    public List<BankAccount> getAccountsByCustomerId(int custID) throws SQLException {
        List<BankAccount> accounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(AccountDataConnection.getSelect())) {
            statement.setInt(1, custID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int accountNum = resultSet.getInt("acct_num");
                    String accountType = resultSet.getString("acct_type");
                    BankAccount account;
                    if (accountType.equalsIgnoreCase("ch")) {
                        account = new CheckingAccount(accountNum);
                    } else {
                        account = new SavingsAccount(accountNum);
                    }
                    account.setAccountNum(accountNum);
                    account.setCustNum(custID);
                    account.setType(accountType);
                    account.setBalance(resultSet.getFloat("balance"));
                    LocalDate createDate = createLocalDate(resultSet.getString("create_date"));
                    account.setCreateDate(createDate);

                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

    private LocalDate createLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }


    // Method to update a user in the database
    @Override
    public int update(BankAccount act) throws SQLException {
        
        int result = -1;
       
        pStatement = connection.prepareStatement(AccountDataConnection.getUpdate());
        pStatement.setString(1, act.getType());
        pStatement.setInt(2, act.getAccountNum());
        result = pStatement.executeUpdate();
        
        return result;
    }

    // Method to delete a user from the database
    @Override
    public int delete(BankAccount act) throws SQLException {
        
        int res = -1;
        
        pStatement = connection.prepareStatement(AccountDataConnection.getDelete());
        pStatement.setInt(1, act.getAccountNum());
        res = pStatement.executeUpdate();
        
        return res;
    }  
    
}
