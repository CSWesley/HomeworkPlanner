package HomeworkPlanner.Validation.SignupUtils;

import javax.swing.*;
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

    int tries = 0;

    public void createAccount(JFrame loginAndSignupFrame, JTextField setEmail, String code, String username, String password, String email, JTabbedPane tabbedPane) throws SQLException
    {
        String inputCode = JOptionPane.showInputDialog(loginAndSignupFrame.getContentPane(), "<html>Enter the activation code sent to <b>" + setEmail.getText() + "</b>. <br/>If you didn't get the email, just enter 9999.</html>", "Enter Code", JOptionPane.QUESTION_MESSAGE);

        if (!code.equals(inputCode)) {
            JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "Code is incorrect! Please try again.");
            tries++;
            if (tries < 3) {
                createAccount(loginAndSignupFrame, setEmail, code, username, password, email, tabbedPane);
            } else {
                JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "You have reached your max amount of tries! Please re-enter your information to repeat the process!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Insert into SQL and create account

            String pass = "";
            Map<String, String> env = System.getenv();

            for (String envName : env.keySet()) {
                if (envName.equalsIgnoreCase("SQLP")) {
                    pass = env.get(envName);
                }
            }

            Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
            Statement statement = connection.createStatement();

            statement.execute("INSERT INTO accounts (username, password, email)" +
                    "VALUES ('" + username + "', '" + password + "', '" + email + "');");

            // Create table for each user in which each one will contain columns for: [assignment title, due date, teacher, class, link, estimate time required].
            statement.execute("CREATE TABLE " + username + " (" +
                    "title VARCHAR(256), " +
                    "dueDate VARCHAR(256), " +
                    "teacher VARCHAR(256), " +
                    "class VARCHAR(256), " +
                    "link VARCHAR(256), " +
                    "eta VARCHAR(256), " +
                    "status TINYINT" +
                    ");");

            connection.close();

            JOptionPane.showMessageDialog(null, "Account has been created! Please go login now.", "Success!", JOptionPane.INFORMATION_MESSAGE);
            tabbedPane.setSelectedIndex(0);
        }
    }
}
