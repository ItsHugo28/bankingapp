package Assignment6Controller;


import Assignment6Model.BankAccount;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountDTO {


    private static AccountDAO accountDAO;

    public AccountDTO() {
    }

    public int createAccount(BankAccount account) {
        try {
            return accountDAO.create(account);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }


    public static List<BankAccount> getAccountsByCustomerId(int customerId) {
        accountDAO = new AccountDAO();
        List<BankAccount> accounts = new ArrayList<>();
        try {
            List<BankAccount> customerAccounts = accountDAO.getAccountsByCustomerId(customerId);
            if (customerAccounts != null) {
                accounts.addAll(customerAccounts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }




    public static int updateAccount(BankAccount account) {
        try {
            return accountDAO.update(account);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int deleteAccount(BankAccount account) {
        try {
            return accountDAO.delete(account);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}