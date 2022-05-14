package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides the ability to connect to the mysql database
 * */
public class DBConnection {

    /**
     * The database name
     * */
    private static final String databaseName = "client_schedule";

    /**
     * The database url
     * */
    private static final String DB_URL="jdbc:mysql://localhost/"+ databaseName + "?connectionTimeZone = SERVER";

    /**
     * The database username
     * */
    private static final String username = "sqlUser";

    /**
     * The database password
     * */
    private static String password = "Passw0rd!";
    /**
     * The Connection object that links to the database
     * */
    static Connection conn;

    /**
     * Instantiates links the connection object to the database
     */
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn=(Connection) DriverManager.getConnection(DB_URL,username,password);
    }

    /**
     * @return conn - the connection object
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection() throws ClassNotFoundException,SQLException, Exception{
        try {
            conn.close();
        }catch(Exception e){
            //Do Nothing
        }
    }

}
