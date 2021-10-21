package HomeworkPlanner.MainApplication.Create.Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class AssignmentListener {

    public ActionListener createAssignmentListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                // Create assignment

                // This creates a connection to SQL in which user must specify several things to signify an assignment
                // like assignment title, teacher, due date, etc.

                // Step 1: Display GUI to specify options like the above (title, teacher, etc.).
                assignmentGUI();

            }
        };
    }

    private void assignmentGUI() {
        JFrame newAssignmentFrame = new JFrame("Create New Assignment");
        newAssignmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = newAssignmentFrame.getContentPane();
        c.setLayout(null);
        newAssignmentFrame.setResizable(false);

        JLabel nameOfAssignment = new JLabel("Create a new assignment");
        nameOfAssignment.setSize(300, 100);
        nameOfAssignment.setLocation(125, -25);
        nameOfAssignment.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        c.add(nameOfAssignment);

        JComboBox<Object> chooseSubject = new JComboBox<>();
        chooseSubject.setSize(140, 35);
        chooseSubject.setLocation(255, 200);
        c.add(chooseSubject);

        JTextField teacher = new JTextField();
        teacher.setSize(140, 35);
        teacher.setLocation(100, 150);
        teacher.setText("Teacher");
        c.add(teacher);

        JTextField link = new JTextField();
        link.setSize(140, 35);
        link.setLocation(100, 200);
        link.setText("Link");
        c.add(link);

        JTextField enterAssignment = new JTextField();
        enterAssignment.setSize(140, 35);
        enterAssignment.setLocation(100, 100);
        enterAssignment.setText("Name");
        c.add(enterAssignment);

        JTextField dueDate = new JTextField();
        dueDate.setSize(140, 35);
        dueDate.setLocation(255, 100);
        dueDate.setText("Due Date");
        c.add(dueDate);

        JTextField estimateAmountTime = new JTextField();
        estimateAmountTime.setSize(140, 35);
        estimateAmountTime.setLocation(255, 150);
        estimateAmountTime.setText("Estimated Time");
        c.add(estimateAmountTime);

        JButton saveNewAssignment = new JButton("Save");
        saveNewAssignment.setSize(110, 40);
        saveNewAssignment.setLocation(195, 275);
        saveNewAssignment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.add(saveNewAssignment);

        newAssignmentFrame.setSize(500, 369);
        newAssignmentFrame.setLocationRelativeTo(null);
        newAssignmentFrame.setVisible(true);
    }
}
