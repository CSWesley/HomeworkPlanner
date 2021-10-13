package HomeworkPlanner.MainApplication.View.Subject;

import HomeworkPlanner.Utils.Utilities;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Map;

public class VSListener {

    public ActionListener listen(JButton button, JButton subjButton) {

        return e -> {
            if (e.getSource() == button) {
                // 1. Using input JOptionPane, say "write yes or no". Also: say "note this will delete all assignments in class"
                // 2. If no close. If yes, delete all entries containing class "test".

                String input = JOptionPane.showInputDialog(null, "Are you sure you want to delete this subject? It will delete all assignments within this class. Please write 'yes' or 'no'.", "Are you sure?", JOptionPane.INFORMATION_MESSAGE);
                if (input.equalsIgnoreCase("yes")) {
                    try {
                        deleteClass(subjButton.getText());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                } else {
                    // Maybe display a message like "Your class is still saved! :)" or something.
                }
            }
        };
    }

    private void deleteClass(String subjectName) throws SQLException {
        String pass = "";
        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("SQLP")) {
                pass = env.get(envName);
            }
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM " + Utilities.username + " WHERE class='" + subjectName + "';");

        while (rs.next()) {
            statement.execute("DELETE FROM " + Utilities.username + " WHERE class='" + subjectName + "';");
        }

        // Throws error "Operation not allowed after ResultSet closed" but it works so don't u dare touch it.

        connection.close();
    }
}
