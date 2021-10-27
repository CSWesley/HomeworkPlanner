package HomeworkPlanner.MainApplication.Create.Subject;

import HomeworkPlanner.Utils.Utilities;

import java.sql.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubjectDetails {

    public HashSet<String> getSubjects() throws SQLException {
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

        HashSet<String> classes = new HashSet<>();
        while (rs.next()) {
            classes.add(rs.getString("class"));
        }

        connection.close();

        return classes;
    }

    public int getNumberOfSubjects() throws SQLException {
        return getSubjects().size();
    }
}
