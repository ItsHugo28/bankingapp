/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment6Controller;

import Assignment6Controller.*;
import Assignment6Model.*;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.util.List;


/**
 *
 * @author karunmehta
 */

public class CustomerDAO implements DAOInterface<BankCustomer> {

    static Connection connection = null;
    PreparedStatement pStatement;
    ResultSet result;
    
    CustomerDAO() {

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
    public int create(BankCustomer cust) throws SQLException {
        
        int res = -1;
        pStatement = connection.prepareStatement(CustomerDataConnection.getInsert());
        pStatement.setString(1, cust.getFirstName());
        pStatement.setString(2, cust.getLastName());
        pStatement.setString(3, cust.getEmail());
        pStatement.setString(4, cust.getPhone());
        pStatement.setString(5, cust.getBirthday());
        res = pStatement.executeUpdate();
        disconnect();
        
        return res;
    }

    // Method to retrieve a user from the database by ID
    @Override
    public BankCustomer get(int anID) throws SQLException {

        pStatement = connection.prepareStatement(CustomerDataConnection.getSelect());
        pStatement.setInt(1,anID);
        result = pStatement.executeQuery();
        
        BankCustomer updatedCust = null;
        if (result.next()) {
            updatedCust = new BankCustomer( result.getInt("id"), result.getString("first_name"), result.getString("last_name"));
            updatedCust.setEmail(result.getString("email"));
            updatedCust.setEmail(result.getString("phone"));
        }


        
        return updatedCust;
    }

    // Method to update a user in the database
    @Override
    public int update(BankCustomer cust) throws SQLException {
        int result = -1;
        pStatement = connection.prepareStatement(CustomerDataConnection.getUpdate());
        pStatement.setString(1, cust.getFirstName());
        pStatement.setString(2, cust.getLastName());
        pStatement.setString(3, cust.getEmail());
        pStatement.setString(4, cust.getPhone());
        pStatement.setInt(5, cust.getCustomerNumber());
        result = pStatement.executeUpdate();
        pStatement.close();
        return result;
    }

    // Method to delete a user from the database
    @Override
    public int delete(BankCustomer cust) throws SQLException {
        
        int res = -1;
        
        pStatement = connection.prepareStatement(CustomerDataConnection.getDelete());
        pStatement.setInt(1, cust.getCustomerNumber());
        res = pStatement.executeUpdate();
        
        return res;
    }
    
    public HashMap validateLogin(String id) {
        
        HashMap hm = null;
        
        try {
            
            pStatement = connection.prepareStatement(CustomerDataConnection.getAdmin());
            pStatement.setString(1, id);
            result = pStatement.executeQuery();
            
            if (result.next()) {
                hm = new HashMap();
                hm.put("ID", result.getString("userid"));
                hm.put("PWD", result.getString("pwd"));
            }
            
        } catch( Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage() + " Try again..");
        }
        
        return hm;
    }
    public static List<BankCustomer> searchCustomersByIds(List<Integer> customerIds) throws SQLException {
        List<BankCustomer> customers = new ArrayList<>();
        if (customerIds.isEmpty()) {
            return customers;
        }

        StringBuilder queryBuilder = new StringBuilder(CustomerDataConnection.GET_CUSTOMERS_BY_IDS_QUERY);
        queryBuilder.append("(");
        for (int i = 0; i < customerIds.size(); i++) {
            queryBuilder.append("?");
            if (i < customerIds.size() - 1) {
                queryBuilder.append(",");
            }
        }
        queryBuilder.append(")");

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
            for (int i = 0; i < customerIds.size(); i++) {
                preparedStatement.setInt(i + 1, customerIds.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BankCustomer customer = new BankCustomer(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name")
                    );
                    customer.setEmail(resultSet.getString("email"));
                    customer.setPhone(resultSet.getString("phone"));
                    customer.setBirthday(resultSet.getString("birthday"));
                    customers.add(customer);
                }
            }
        }
        return customers;
    }
}
