package HomeworkPlanner.Loading;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoadingScreen {

    public JFrame frame;

    public void loadScreen() {
        frame = new JFrame("Loading...");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        Container c = frame.getContentPane();
        c.setLayout(new FlowLayout());

        JLabel loadingLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/HomeworkPlanner/images/logo.png"))));
        c.add(loadingLabel);

        frame.setSize(200, 225);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
