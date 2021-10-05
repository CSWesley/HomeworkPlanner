package HomeworkPlanner;

import HomeworkPlanner.Validation.LoginAndSignup;

import javax.swing.*;
import java.awt.*;

/**
 * This class handles the main frame when application is opened. It contains the "login" and "signup" buttons.
 */

public class MainFrame {

    private Font mainTitleFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    private Font subTitleFont = new Font("Arial", Font.PLAIN, 20);
    private LoginAndSignup loginAndSignup = new LoginAndSignup();

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MainFrame mainFrame = new MainFrame();

        mainFrame.openLoginPage();
    }

    private void openLoginPage() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame loginAndSignupFrame = new JFrame("Login and Signup");
        loginAndSignupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginAndSignupFrame.setResizable(false);
        Container c = loginAndSignupFrame.getContentPane();
        c.setLayout(null);

        // Main Title
        JLabel mainTitle = new JLabel("Homework Planner");
        mainTitle.setFont(this.mainTitleFont);
        mainTitle.setSize(300, 30);
        mainTitle.setLocation(95, 10);
        c.add(mainTitle);

        // Sub-Title
        JLabel subTitle = new JLabel("Login or Signup");
        subTitle.setFont(this.subTitleFont);
        subTitle.setSize(200, 25);
        subTitle.setLocation(170, 60);
        c.add(subTitle);

        // Handles the login and signup protocols and adds components to frame.
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        loginAndSignup.loginAndSignup(tabbedPane, loginAndSignupFrame);

        loginAndSignupFrame.setSize(500, 500);
        loginAndSignupFrame.setLocationRelativeTo(null);
        loginAndSignupFrame.setVisible(true);
    }
}
