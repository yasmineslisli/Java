package db;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {

    public static Statement connectToDb() {
        java.sql.Connection connection = null;
        Statement statement;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrap","root","");
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);

        }

        return statement;
    }







}
