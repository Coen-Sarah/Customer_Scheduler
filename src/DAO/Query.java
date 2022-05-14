package DAO;

import java.sql.*;

import static DAO.DBConnection.getConnection;
/**
 * Provides instruction for making queries to the mysql database using the DBConnection class
 * */
public class Query {

    /**
     * String containing the sql query to be made
     * */
    private static String query;

    /**
     * Statement object used to execute the query
     * */
    private static Statement stmt;

    /**
     * ResultSet containing the table returned by the query
     * */
    private static ResultSet result;

    /**
     * @param q the query to be made against the database
     * Makes a query to the database and saves the resulting table to result.
     * */
    public static void makeQuery(String q){
        query = q;
        try{
            stmt = getConnection().createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    /**
     * @return result - the ResultSet object created by the makeQuery() method.
     * */
    public static ResultSet getResult(){
        return result;
    }

}
