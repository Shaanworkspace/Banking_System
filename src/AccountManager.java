import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.function.Predicate;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    //Constructor
    public AccountManager(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    private void creditMoney(long accountNumber){

    }
    private void debitMoney(long accountNumber){
        scanner.nextLine();
        System.out.println("Enter deposit amount");
        double Amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Security Pin");
        String SecurityPin = scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            if(accountNumber > 0){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE  account_number = ?");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void transferMoney(long senderAccountNumber){

    }
    private void getBalance(long accountNumber){

    }

}
