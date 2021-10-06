package HomeworkPlanner.MainApplication.ActionListeners;

import HomeworkPlanner.Utils.Utilities;
import jdk.jshell.execution.Util;

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
                        String pass = "";
                        Map<String, String> env = System.getenv();

                        for (String envName : env.keySet()) {
                            if (envName.equalsIgnoreCase("SQLP")) {
                                pass = env.get(envName);
                            }
                        }

                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
                            Statement statement = connection.createStatement();

                            String username = Utilities.username;

                            // First, check if the class already exists. If it does, throw error. If not, execute insert statement.
                            ResultSet rs = statement.executeQuery("SELECT * FROM " + username + ";");

                            while (rs.next()) {
                                if (rs.getString("class").equals(this.enterSubject.getText())) {
                                    System.out.println("Equals");
                                }
                            }

                            statement.execute("INSERT INTO " + username + " (class)" +
                                    "VALUES ('" + this.enterSubject.getText() + "');");

                            connection.close();

                            this.newSubjectFrame.dispose();
                            JOptionPane.showMessageDialog(null, "<html>Subject: <b>" + enterSubject.getText() + "</b> has been created!</html>");

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        };
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
