package HomeworkPlanner.MainApplication.Create.Subject;

import HomeworkPlanner.Utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Map;

public class SubjectListener {

    private JButton saveButton;
    private JTextField enterSubject;
    private JFrame newSubjectFrame;

    public ActionListener createSubjectListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                // Create subject:

                // This creates a connection to SQL, and adds the name of the newly-created subject to the category of 'class'.
                // The other categories (ex. those not being 'class') will be left as null. To access the class names, iterate
                // through each entry and create a list of each class. To see # of assignments in a specific class, check number
                // of times the specified class is found in all entries. Another note: check 'status' column to make sure the assignments
                // are marked as 'incomplete'.

                // Step 1: Display GUI to choose name of class.
                createSubjectFrame(al -> {
                    if (al.getSource() == this.saveButton) {
                        // Step 2: Connect to SQL and enter info into database.

                        try {
                            boolean exists = doesExist();

                            if (exists) {
                                JOptionPane.showMessageDialog(null, "<html>The class: <b>" + this.enterSubject.getText() + "</b> could not be created because it already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Inserts given name into table.
                                insertClass();

                                this.newSubjectFrame.setVisible(false);
                                JOptionPane.showMessageDialog(null, "<html>The class: <b>" + this.enterSubject.getText() + "</b> has been created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                this.newSubjectFrame.dispose();
                            }

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    private void insertClass() throws SQLException {
        String pass = "";
        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("SQLP")) {
                pass = env.get(envName);
            }
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO " + Utilities.username + " (class)" +
                "VALUES ('" + this.enterSubject.getText() + "');");

        connection.close();
    }

    private boolean doesExist() throws SQLException {
        String pass = "";
        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("SQLP")) {
                pass = env.get(envName);
            }
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
        Statement statement = connection.createStatement();

        String username = Utilities.username;

        // First, check if the class already exists. If it does, throw error. If not, execute insert statement.
        ResultSet rs = statement.executeQuery("SELECT * FROM " + username + " WHERE class='" + this.enterSubject.getText() + "';");

        boolean toReturn = rs.next();

        connection.close();

        return toReturn;
    }

    private void createSubjectFrame(ActionListener actionListener) {
        newSubjectFrame = new JFrame("Create New Subject");
        newSubjectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = newSubjectFrame.getContentPane();
        c.setLayout(null);
        newSubjectFrame.setResizable(false);

        JLabel nameOfSubject = new JLabel("Name of Subject");
        nameOfSubject.setSize(300, 100);
        nameOfSubject.setLocation(50, -25);
        nameOfSubject.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        c.add(nameOfSubject);

        enterSubject = new JTextField();
        enterSubject.setSize(140, 35);
        enterSubject.setLocation(55, 100);
        c.add(enterSubject);

        saveButton = new JButton("Save");
        saveButton.setSize(100, 40);
        saveButton.setLocation(75, 155);
        saveButton.addActionListener(actionListener);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.add(saveButton);

        newSubjectFrame.setSize(265, 250);
        newSubjectFrame.setLocationRelativeTo(null);
        newSubjectFrame.setVisible(true);
    }
}
