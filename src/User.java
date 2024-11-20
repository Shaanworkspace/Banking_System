import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class User {
    private Connection connection;
    private Scanner scanner;

    //Constructor
    public User(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }


    public void register(){
        scanner.nextLine();
        System.out.println("Full Name : ");
        String name = scanner.nextLine();
        System.out.println("Email Address : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();
        if(userExist(email)){
            System.out.println("Username already exists.");
            return;
        }
        String register_query = "INSERT INTO user (email,password,full_name) VALUES (?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,name);
            int affectedRow = preparedStatement.executeUpdate();
            if(affectedRow > 0) System.out.println("User registered successfully.");
            else System.out.println("User not registered.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public String login(){
        scanner.nextLine();
        System.out.println("Email Address : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();

        String loginQuery = "SELECT * FROM user WHERE email = (?) AND password = (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(loginQuery);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getString("email");
            else return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userExist(String email){
        String sql = "SELECT * FROM user WHERE email = (?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
