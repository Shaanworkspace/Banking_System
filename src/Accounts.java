import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    //Constructor
    public Accounts(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public long openAccount(String email){
        if(!accountExist(email)){
            scanner.nextLine();
            System.out.println("Enter Full name : ");
            String name = scanner.nextLine();
            System.out.println("Enter Initial Balance : ");
            double initialBalance = scanner.nextDouble();
            System.out.println("Enter Security Pin : ");
            String securityPin = scanner.nextLine();
            String open_account_query = "INSERT INTO accounts (account_number,email,full_name,balance,security_pin) VALUES (?,?,?,?,?)";
            try{
                long accountNumber = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(open_account_query);
                preparedStatement.setLong(1,accountNumber);
                preparedStatement.setString(2,email);
                preparedStatement.setString(3,name);
                preparedStatement.setDouble(4,initialBalance);
                preparedStatement.setString(5,securityPin);
                int rowAffected= preparedStatement.executeUpdate();

                if(rowAffected>0){
                    return accountNumber;
                }else{
                    throw new SQLException("Error in open account query");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        throw new RuntimeException("Account already exists");
    }
    public void closeAccount(String email){
        if(!accountExist(email)){
            System.out.println("Not exist account ");
            return;
        }

        String delete_account_query = "DELETE FROM accounts WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(delete_account_query);
            preparedStatement.setString(1,email);
            int rowAffected= preparedStatement.executeUpdate();
            if(rowAffected>0){
                System.out.println("Account deleted successfully");
            }else System.out.println("Account not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public long getAccountNumber(String email){
        String query = "select accountNumber from accounts where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("account_number");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //
        throw new RuntimeException("Account not found");
    }
    private long generateAccountNumber(){
        try{
            Statement statement = connection.createStatement();
            //will extract only account number
            ResultSet resultSet = statement.executeQuery("SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1");
            if(resultSet.next()){
                long lastAccountNumber = resultSet.getLong("account_number");
                return lastAccountNumber+1;
            }else return 10000100;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean accountExist(String email) {
        String sql = "SELECT account_number FROM accounts WHERE email = (?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
