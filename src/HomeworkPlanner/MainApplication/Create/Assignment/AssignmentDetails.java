package HomeworkPlanner.MainApplication.Create.Assignment;

import HomeworkPlanner.Utils.Utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class AssignmentDetails {

    public ArrayList<String> getAssignments() throws SQLException {
        String pass = "";
        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("SQLP")) {
                pass = env.get(envName);
            }
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM " + Utilities.username);

        ArrayList<String> assignments = new ArrayList<>();
        while (rs.next()) {
            assignments.add(rs.getString("title") + rs.getString("dueDate"));
        }

        // TODO CHECK THIS WITH BUTTON CLICK (add listener)


        return assignments;
    }
}
