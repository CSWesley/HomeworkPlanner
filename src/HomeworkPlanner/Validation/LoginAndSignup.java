package HomeworkPlanner.Validation;

import HomeworkPlanner.Utils.GhostText;
import HomeworkPlanner.Validation.SignupUtils.CreateAccount;
import HomeworkPlanner.Validation.SignupUtils.SendConfirmation;

import javax.swing.*;

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


                        insertCode(loginAndSignupFrame, setEmail, code);
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

    private void insertCode(JFrame loginAndSignupFrame, JTextField setEmail, String code) {
        String inputCode = JOptionPane.showInputDialog(loginAndSignupFrame.getContentPane(), "<html>Enter the activation code sent to <b>" + setEmail.getText() + "</b>. <br/>If you didn't get the email, just enter 9999.</html>", "Enter Code", JOptionPane.QUESTION_MESSAGE);

        if (!code.equals(inputCode)) {
            JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "Code is incorrect! Please try again.");
            tries++;
            if (tries < 3) {
                insertCode(loginAndSignupFrame, setEmail, code);
            } else {
                JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "You have reached your max amount of tries! Please re-enter your information to repeat the process!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Insert into SQL and create account

        }
    }
}
