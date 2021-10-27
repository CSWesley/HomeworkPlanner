package HomeworkPlanner.MainApplication.View.Subject;

import HomeworkPlanner.MainApplication.Create.Subject.SubjectDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class ViewSubjectListener {

    private final Font titleFont = new Font("Times New Roman", Font.PLAIN, 40);

    public ActionListener createSubjectListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                try {

                    createViewFrame();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public void createViewFrame() throws SQLException {
        JFrame viewFrame = new JFrame("View Subjects");
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = viewFrame.getContentPane();
        c.setLayout(new FlowLayout());
        viewFrame.setResizable(false);

        SubjectDetails sd = new SubjectDetails();
        Set<String> classes = sd.getSubjects();

        int classesNum = classes.size();
        ArrayList<String> ar = new ArrayList<>(classesNum);
        ar.addAll(classes);

        for (int i = 0; i < classes.size(); i++) {
            JButton subjectName = new JButton(ar.get(i));
            subjectName.addActionListener(classActionListener(subjectName, viewFrame));
            c.add(subjectName);
        }

        viewFrame.setSize(450, 450);
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setVisible(true);
    }

    private ActionListener classActionListener(JButton button, JFrame subjectsFrame) {

        return e -> {
            if (e.getSource() == button) {
                // Show these details:
//              |-----------------------|
                // 1. Name of subject.
                // 2. Number of assignments in this subject.
                // 3. Delete subject.
                // Below is a MAYBE
                // 4. View deeper/more. (This will display new window with each individual assignment [clickable]. Perhaps just used viewAssignments [when you make it])

                JFrame frame = new JFrame(button.getText());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                Container c = frame.getContentPane();
                c.setLayout(null);

                // Dispose of the subjects frame.
                subjectsFrame.dispose();

                // Display information about selected subject.
                JLabel name = new JLabel("<html>Subject name: <b>" + button.getText() + "</b></html>");
                name.setSize(500, 100);
                name.setLocation(15, -20);
                name.setFont(this.titleFont);
                c.add(name);

                JLabel numOfAssignments = new JLabel("<html>Number of assignments: <b>" + "add method to get this later." + "</b></html>");
                numOfAssignments.setSize(500, 100);
                numOfAssignments.setLocation(name.getX(), name.getY() + 90);
                numOfAssignments.setFont(this.titleFont);
                c.add(numOfAssignments);

                JButton delSubject = new JButton("<html>Delete <b>" + button.getText() + "</b></html>");
                delSubject.setSize(140, 40);
                delSubject.setLocation(200, 190);
                delSubject.addActionListener(new VSListener().listen(delSubject, button));
                c.add(delSubject);

                frame.setSize(538, 263);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        };
    }
}
