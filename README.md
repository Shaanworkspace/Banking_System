
# Banking System JDBC PROJECT

![Screenshot 2024-11-20 153223](https://github.com/user-attachments/assets/7d8f7d71-23f6-4748-9018-a14d6c4bf8f2)
![Screenshot 2024-11-20 152904](https://github.com/user-attachments/assets/013ec0a5-2d42-4ffa-8513-499e55a8d510)


There are 4 classes, The driver class is banking app. java, which will use all the rest of the classes.


## Parent class bankingApp.java

    Credentials -> Driver Upload -> Connection using credentials ->Statement or preparedStatement->resultSet

-Step 1:  After the connection block inside try to create an instance of all rest classes and pass the connection and scanner to avoid sab aapne aapna scanner, connection (Learn from hotel management project)by

    User user = new User(connection,scanner);
    Accounts accounts = new Accounts(connection,scanner);
    AccountManager accountManager = new AccountManager(connection,scanner);

In every class Create two private initializations of connection and scanner and make a constructor in each of the classes like:

    public class User / Account / AccountManager {
    private Connection connection;
    private Scanner scanner;

    //Constructor
    public User / Account / AccountManager(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    }


## User Class

This Class has three functions
1.login()
2.register()
     also UserExist()


-public String login(){ because we need to return mail so that it can be accessed by all

    ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getString("email");
            else return null;



## getAccountNumber(String email)

1. The return type is long to get the account number 
focus on

    if(resultSet.next()){
                return resultSet.getLong("account_number");
            }
2. if there is no return statement we can throw an exception

        throw new RuntimeException("Account not found");
## Account Class

-GenerateAccountnumber()
Agenda: to fetch the last account number(by query) and increment by one

To generate we have a query that -> inside accounts table the field account_number we create a descending order format 

To specify descending order in an SQL query, use the keyword "DESC" after the column name in the "ORDER BY" clause; 
For example: "SELECT * FROM table_name ORDER BY column_name DESC;" will sort the results based on the specified column in descending order 

    ResultSet resultSet = statement.executeQuery("SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1");
        if(resultSet.next()){
            long lastAccountNumber = resultSet.getLong("account_number");
             return lastAccountNumber+1;
        }else return 10000100;

Limit 1 means generating one element will move upward and this method will fetch the last and increment it if it is the first entry it will return the other part. 
## AccountManager Class

1. private void debitMoney()

we are doing transfer handling so we need to false the auto-commit 
    connection.setAutoCommit(false);
so until our transaction is not completed it will not commit

to commit

                    int row affected = creditPreparedStatement.executeUpdate();
                        if(row affected > 0){
                            System. out.println("Debited successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }
                    else {
                            System. out.println("Debited failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return;
                        }
## Learning

-   Ascending in SQL : 

To format a SQL query to return results in ascending order, use the "ORDER BY" clause with the "ASC" keyword after the column name you want to sort by; 

For example, "SELECT * FROM table_name ORDER BY column_name ASC" will return results with the specified column sorted from lowest to highest value (ascending order). 

Key points about ascending order in SQL:

Default behavior: If you don't specify "ASC" in the "ORDER BY" clause, the results will be sorted in ascending order by default.

Syntax: SELECT * FROM table_name ORDER BY column_name ASC;


-   Descending Order

To specify descending order in an SQL query, use the keyword "DESC" after the column name in the "ORDER BY" clause; for example, "SELECT * FROM table_name ORDER BY column_name DESC;" will sort the results based on the specified column in descending order.

Key points about descending order in SQL:
Keyword: "DESC" 

Usage: "ORDER BY column_name DESC" 

Function: Sorts data from the highest to lowest value (for numeric data) or from Z to A (for text data). 
Example:
Code

SELECT * FROM customers 
ORDER BY purchase_amount DESC;

This query will retrieve all customer data from the "customers" table, sorted by their "purchase_amount" in descending order, meaning the customers with the highest purchase amounts will appear first.

- if there is no return statement we can throw an exception

        throw new RuntimeException("Account not found");
