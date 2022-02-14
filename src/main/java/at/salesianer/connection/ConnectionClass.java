package at.salesianer.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    public Connection connection;
    public Connection getConnection(){
        String dbName = "Salesianer";
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/"+ dbName;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}