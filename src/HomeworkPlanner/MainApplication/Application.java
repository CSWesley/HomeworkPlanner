package HomeworkPlanner.MainApplication;

import HomeworkPlanner.Utils.Utilities;

import javax.swing.*;
import java.awt.*;

public class Application {

    public void openHomeworkPlanner() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame homeworkPlanner = new JFrame("Homework Planner - " + Utilities.username);
        homeworkPlanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeworkPlanner.setResizable(false);
        Container c = homeworkPlanner.getContentPane();
        c.setLayout(null);



        homeworkPlanner.setSize(907, 654);
        homeworkPlanner.setLocationRelativeTo(null);
        homeworkPlanner.setVisible(true);
    }
}
