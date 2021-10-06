package HomeworkPlanner.MainApplication.ActionListeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ActionListeners {

    private JButton saveButton;

    public ActionListener createSubjectListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                // Create subject:

                // This creates a connection to SQL, and adds the name of the newly-created subject to the category of 'class'.
                // The other categories (ex. those not being 'class') will be left as null. To access the class names, iterate
                // through each entry and create a list of each class. To see # of assignments in a specific class, check number
                // of times the specified class is found in all entries. Another note: check 'status' column to make sure the assignments
                // are marked as 'incomplete'.

                try {
                    // Step 1: Connect to SQL
                    String pass = "";
                    Map<String, String> env = System.getenv();

                    for (String envName : env.keySet()) {
                        if (envName.equalsIgnoreCase("SQLP")) {
                            pass = env.get(envName);
                        }
                    }

                    Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);

                    // Step 2: Display GUI to choose name of class.
                    createSubjectFrame(al -> {
                        if (al.getSource() == this.saveButton) {
                            System.out.println("Save button was clicked.");
                        }
                    });


                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    private void createSubjectFrame(ActionListener actionListener) {
        JFrame newSubjectFrame = new JFrame("Create New Subject");
        newSubjectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = newSubjectFrame.getContentPane();
        c.setLayout(null);
        newSubjectFrame.setResizable(false);

        JLabel nameOfSubject = new JLabel("Name of Subject");
        nameOfSubject.setSize(300, 100);
        nameOfSubject.setLocation(50, -25);
        nameOfSubject.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        c.add(nameOfSubject);

        JTextField enterSubject = new JTextField();
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
