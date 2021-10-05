package HomeworkPlanner.Validation.SignupUtils;

import java.sql.*;
import java.util.Map;

/**
 * This class provides methods to deal with creating accounts.
 */

public class CreateAccount {

    /**
     * This method checks if a given account exists with the details provided in the text fields for the signup.
     *
     * @param username
     * @param email
     * @return Will return if the account exists.
     */

    public boolean checkIfExists(String username, String email) {
        // Initiate SQL. Find online database that goes beyond 5 MB. Well, in the future at least. You have no money to spend on REAL databases you donkey.
        // Find one in the future.
        try {
            String pass = "";
            Map<String, String> env = System.getenv();

            for (String envName : env.keySet()) {
                if (envName.equalsIgnoreCase("SQLP")) {
                    pass = env.get(envName);
                }
            }

            Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE username='" + username + "' AND email='" + email + "';");

            boolean toReturn = rs.next();

            connection.close();

            return toReturn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
