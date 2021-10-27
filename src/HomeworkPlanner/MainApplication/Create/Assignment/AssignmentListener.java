package HomeworkPlanner.MainApplication.Create.Assignment;

import HomeworkPlanner.MainApplication.Create.Subject.SubjectDetails;
import HomeworkPlanner.Utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashSet;
import java.util.Map;

public class AssignmentListener {

    private JTextField teacher;
    private JTextField link;
    private JTextField enterAssignment;
    private JTextField dueDate;
    private JTextField estimateAmountTime;
    private JComboBox<Object> chooseSubject;
    private JFrame newAssignmentFrame;

    public ActionListener createAssignmentListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                // Create assignment

                // This creates a connection to SQL in which user must specify several things to signify an assignment
                // like assignment title, teacher, due date, etc.

                // Step 1: Display GUI to specify options like the above (title, teacher, etc.).
                try {
                    assignmentGUI();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        };
    }

    private void assignmentGUI() throws SQLException {
        newAssignmentFrame = new JFrame("Create New Assignment");
        newAssignmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = newAssignmentFrame.getContentPane();
        c.setLayout(null);
        newAssignmentFrame.setResizable(false);

        JLabel nameOfAssignment = new JLabel("Create a new assignment");
        nameOfAssignment.setSize(300, 100);
        nameOfAssignment.setLocation(125, -25);
        nameOfAssignment.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        c.add(nameOfAssignment);

        chooseSubject = new JComboBox<>();
        chooseSubject.setSize(140, 35);
        chooseSubject.setLocation(255, 200);
        addSubjects(chooseSubject);
        c.add(chooseSubject);

        teacher = new JTextField();
        teacher.setSize(140, 35);
        teacher.setLocation(100, 150);
        teacher.setText("Teacher");
        c.add(teacher);

        link = new JTextField();
        link.setSize(140, 35);
        link.setLocation(100, 200);
        link.setText("Link");
        c.add(link);

        enterAssignment = new JTextField();
        enterAssignment.setSize(140, 35);
        enterAssignment.setLocation(100, 100);
        enterAssignment.setText("Name");
        c.add(enterAssignment);

        dueDate = new JTextField();
        dueDate.setSize(140, 35);
        dueDate.setLocation(255, 100);
        dueDate.setText("Due Date");
        c.add(dueDate);

        estimateAmountTime = new JTextField();
        estimateAmountTime.setSize(140, 35);
        estimateAmountTime.setLocation(255, 150);
        estimateAmountTime.setText("Estimated Time");
        c.add(estimateAmountTime);

        JButton saveNewAssignment = new JButton("Save");
        saveNewAssignment.setSize(110, 40);
        saveNewAssignment.setLocation(195, 275);
        saveNewAssignment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveNewAssignment.addActionListener(saveListener(saveNewAssignment));
        c.add(saveNewAssignment);

        newAssignmentFrame.setSize(500, 369);
        newAssignmentFrame.setLocationRelativeTo(null);
        newAssignmentFrame.setVisible(true);
    }

    private void addSubjects(JComboBox<Object> chooseSubject) throws SQLException {
        // Set contains unique elements only.
        // Add items to this combo box using a HashSet

        SubjectDetails sd = new SubjectDetails();
        HashSet<String> subjects = sd.getSubjects();

        for (String e : subjects) {
            chooseSubject.addItem(e);
        }
    }

    private ActionListener saveListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                // Add many checks here:
                // 1. All fields are filled out.
                // DONE 2. There is at least 1 subject that has been created.

                if (teacher.getText().isEmpty() || link.getText().isEmpty() || enterAssignment.getText().isEmpty() || dueDate.getText().isEmpty() || estimateAmountTime.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "You must fill out all the fields!", "Error", JOptionPane.ERROR_MESSAGE);

                } else {
                    // Create assignment here.

                    try {

                        addAssignment(Utilities.username, chooseSubject.getSelectedItem().toString(), enterAssignment.getText());

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
    }

    private void addAssignment(String username, String classS, String assignmentTitle) throws SQLException {
        String pass = "";
        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("SQLP")) {
                pass = env.get(envName);
            }
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
        Statement statement = connection.createStatement();

        // ------------

        // 1. Check if assignment already exists in specified class. If it does, throw error. If not, proceed to add assignment in SQL.
        // 2. Can't think of any other thing you could do wrong.

        ResultSet rs = statement.executeQuery("SELECT * FROM " + username + " WHERE class='" + classS + "' AND title='" + assignmentTitle + "';");

        // Now check if the result set above returns anything.

        if (rs.next()) {

            // This assignment already exists in that class.
            JOptionPane.showMessageDialog(null, "This assignment already exists in this class. Please choose a new name or a different class.", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            // This assignment doesn't exist, therefore you can create it.
            // TODO: Status column is '0' if complete and '1' if not complete.
            //  Maybe delete this to save space in ur 5 MB max database because you have no money to get a real one.

            statement.execute("INSERT INTO " + username + " (title, dueDate, teacher, class, link, eta)" +
                                   "VALUES ('" + assignmentTitle + "', '" + dueDate.getText() + "', '" + teacher.getText() + "', '" + classS + "', '" + link.getText() + "', '" + estimateAmountTime.getText() + "');");
            // Success message:
            JOptionPane.showMessageDialog(null, "<html>You have successfully created an assignment called <b>" + assignmentTitle + "</b> for your <b>" + classS + "</b> class!</html>", "Success!", JOptionPane.INFORMATION_MESSAGE);

            // Dispose of frame:
            newAssignmentFrame.dispose();
        }

        connection.close();
    }
}
