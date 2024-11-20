import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class bankingApp {
    //Credential set
    private static final String url ="jdbc:mysql://localhost:3306/banking_system";
    private static final String username="root";
    private static final String password="1234";
    //we are making all private -> not to give access to anyone else

    public static void main(String[] args) {
        //Load Driver Class
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected to database");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            //set Connection
            Connection connection= DriverManager.getConnection(url,username,password);
            Scanner scanner = new Scanner(System.in);
            User user = new User(connection,scanner);
            Accounts accounts = new Accounts(connection,scanner);
            AccountManager accountManager = new AccountManager(connection,scanner);

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
