package HomeworkPlanner.Validation;

import HomeworkPlanner.Utils.GhostText;
import HomeworkPlanner.Validation.SignupUtils.CreateAccount;
import HomeworkPlanner.Validation.SignupUtils.SendConfirmation;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * This class handles the logging in and signing up of the user. It also adds the components to the frame.
 */

public class LoginAndSignup {

    public void loginAndSignup(JTabbedPane tabbedPane, JFrame loginAndSignupFrame) {
        // Tabbed pane for login and signup

        /** Start handling signup */

        JPanel signup = new JPanel();

        JTextField createUsername = new JTextField("", 13);
        GhostText ghostText = new GhostText(createUsername, "Username...");
        signup.add(createUsername);

        JPasswordField createPassword = new JPasswordField("", 13);
        GhostText ghostText2 = new GhostText(createPassword, "Password");
        signup.add(createPassword);

        JPasswordField confirmPassword = new JPasswordField("", 13);
        GhostText ghostText3 = new GhostText(confirmPassword, "Password");
        signup.add(confirmPassword);

        JTextField setEmail = new JTextField("", 15);
        GhostText ghostText4 = new GhostText(setEmail, "Email...");
        signup.add(setEmail);

        JButton confirmSignup = new JButton("Confirm Signup");
        confirmSignup.addActionListener(e -> {
            if (e.getSource() == confirmSignup) {
                // Send email to confirm.
                CreateAccount createAccount = new CreateAccount();
                boolean exists = createAccount.checkIfExists(createUsername.getText(), setEmail.getText());

                if (exists) {
                    JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "An account already exists with this username or email!", "Error", JOptionPane.ERROR_MESSAGE);

                } else {
                    // Email sending
                    if (!setEmail.getText().contains("@") || createUsername.getText().isEmpty() || createPassword.getText().isEmpty() || confirmPassword.getText().isEmpty() && createPassword.getText().equals(confirmPassword.getText())) {
                        JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "One or more fields are filled out incorrectly!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        SendConfirmation sendConfirmation = new SendConfirmation();
                        sendConfirmation.send(setEmail.getText());
                        String code = sendConfirmation.getCode();

                        try {
                            createAccount(loginAndSignupFrame, setEmail, code, createUsername.getText(), createPassword.getText(), setEmail.getText());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        signup.add(confirmSignup);

        /** End handling signup */

        // ******** //

        /** Start handling login */

        JPanel login = new JPanel();

        JTextField enterUsername = new JTextField("", 13);
        GhostText ghostText5 = new GhostText(enterUsername, "Username...");
        login.add(enterUsername);

        JPasswordField enterPass = new JPasswordField("", 13);
        GhostText ghostText6 = new GhostText(enterPass, "Password");
        login.add(enterPass);

        JButton confirmLogin = new JButton("Confirm Login");
        confirmLogin.addActionListener(e -> {
            if (e.getSource() == confirmLogin) {
                // Confirm login and show home page.
            }
        });
        login.add(confirmLogin);

        /** End handling login */

        // Add components to MainFrame
        tabbedPane.setSize(300, 300);
        tabbedPane.setLocation(90, 100);
        tabbedPane.addTab("Login", login);
        tabbedPane.addTab("Signup", signup);

        // Add the signup and login tabbed pane to the frame.
        loginAndSignupFrame.getContentPane().add(tabbedPane);
    }

    int tries = 0;

    private void createAccount(JFrame loginAndSignupFrame, JTextField setEmail, String code, String username, String password, String email) throws SQLException {
        String inputCode = JOptionPane.showInputDialog(loginAndSignupFrame.getContentPane(), "<html>Enter the activation code sent to <b>" + setEmail.getText() + "</b>. <br/>If you didn't get the email, just enter 9999.</html>", "Enter Code", JOptionPane.QUESTION_MESSAGE);

        if (!code.equals(inputCode)) {
            JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "Code is incorrect! Please try again.");
            tries++;
            if (tries < 3) {
                createAccount(loginAndSignupFrame, setEmail, code, username, password, email);
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
                    "title, dueDate, teacher, class, link, eta);");

            connection.close();

            JOptionPane.showMessageDialog(null, "Account has been created! Please go login now.", "Success!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
