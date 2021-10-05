package HomeworkPlanner.Validation.LoginUtils;

import java.sql.*;
import java.util.Map;

public class AccountLogin {

    public boolean matches(String username, String password) {
        try {
            String pass = "";
            Map<String, String> env = System.getenv();

            for (String envName : env.keySet()) {
                if (envName.equalsIgnoreCase("SQLP")) {
                    pass = env.get(envName);
                }
            }

            Connection connection = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3440249", "sql3440249", pass);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE username='" + username + "' AND password='" + password + "';");

            boolean toReturn = rs.next();

            connection.close();

            return toReturn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
