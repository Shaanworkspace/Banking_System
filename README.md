
# Banking System JDBC PROJECT

There are 4 classes ,The driver class is bankingApp.java will use all the rest of classes.


## Parent class bankingApp.java

    Credentials -> Driver Upload -> Connection using credentials ->Statement or preparedStatement->resultSet

-Step 1 :  After connection block inside try create instance of all rest classes and pass connection and sccanner as to avoid sab aapne aapna scanner,connection (Learn from hotel managment project) bnay

    User user = new User(connection,scanner);
    Accounts accounts = new Accounts(connection,scanner);
    AccountManager accountManager = new AccountManager(connection,scanner);

And in very class Create two private initialisation of cnnection and scanner and make a constructor in each of class like:

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

This Class have three functions
1.login()
2.register()
     also UserExist()


-public String login(){ becouse we need to return mail so that it can access by all

    ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getString("email");
            else return null;



## getAccountNumber(String email)

1. return type is long to get account number 
focus on

    if(resultSet.next()){
                return resultSet.getLong("account_number");
            }
2. if no return statement we can throw exception

        throw new RuntimeException("Account not found");
## Account Class

-GenerateAccountnumber()
AGenda : to fetch the last account number(by query) and increment by one

To gemerate we have query that -> inside accounts table the field account_number we create a decending order format 

To specify descending order in an SQL query, use the keyword "DESC" after the column name in the "ORDER BY" clause; 

for example: "SELECT * FROM table_name ORDER BY column_name DESC;" will sort the results based on the specified column in descending order 

    ResultSet resultSet = statement.executeQuery("SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1");
        if(resultSet.next()){
            long lastAccountNumber = resultSet.getLong("account_number");
             return lastAccountNumber+1;
        }else return 10000100;

Limit 1 means only last one element willmove upward and this method will fetch last and increment it and if it is the first enty it will return the else part 
## AccountManager Class

1. private void debitMoney()

we are doing transfe handeling so we need to flase the autoCommit 
    connection.setAutoCommit(false);
so until our transection is not completed it will not commit

to commit

                    int rowAffected = creditPreparedStatement.executeUpdate();
                        if(rowAffected > 0){
                            System.out.println("Debited successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }
                    else {
                            System.out.println("Debited failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return;
                        }
## Learning

-   Ascending in SQL : 

To format a SQL query to return results in ascending order, use the "ORDER BY" clause with the "ASC" keyword after the column name you want to sort by; 

for example, "SELECT * FROM table_name ORDER BY column_name ASC" will return results with the specified column sorted from lowest to highest value (ascending order). 

Key points about ascending order in SQL:

Default behavior: If you don't specify "ASC" in the "ORDER BY" clause, the results will be sorted in ascending order by default.

Syntax: SELECT * FROM table_name ORDER BY column_name ASC;


-   Decending order

To specify descending order in an SQL query, use the keyword "DESC" after the column name in the "ORDER BY" clause; for example, "SELECT * FROM table_name ORDER BY column_name DESC;" will sort the results based on the specified column in descending order.

Key points about descending order in SQL:
Keyword: "DESC" 

Usage: "ORDER BY column_name DESC" 

Function: Sorts data from highest to lowest value (for numeric data) or from Z to A (for text data). 
Example:
Code

SELECT * FROM customers 
ORDER BY purchase_amount DESC;

This query will retrieve all customer data from the "customers" table, sorted by their "purchase_amount" in descending order, meaning the customers with the highest purchase amounts will appear first

- if no return statement we can throw exception

        throw new RuntimeException("Account not found");
