/*
 * @(#)Database.java 1.1 2006/09/15
 *
 * Class database can be used to load the proper driver and make a connection
 * You should first set the connection info by calling Database.init(..)
 * If you later need the connection, just call the method Database.getConnection()
 * Note that getConnection could return null, so be prepared for this!
 *
 */

package Controller;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String _Server;
    private static String _DBName;
    private static String _UserName;
    private static String _PassWord;
    private static Connection con ;
    
    private Database()	//private constructor, no need to create instances!
    {
    }
    
    // this method should be called in the beginning of the program
    // it remembers all info for connecting to the database
    //
    public static void init( String Server, String DBName, String UserName, String PassWord ) {
        _Server = Server;
        _DBName = DBName;
        _UserName = UserName;
        _PassWord = PassWord;
    }
    
    // call Database.getConnection() to retrieve the one and only connection
    // to the database, previously specified and remembered in this class
    public static Connection getConnection() {
        if( con == null ) // never called before?
        {
            // first try to load the SQL driver
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch( java.lang.ClassNotFoundException e ) {
                System.err.print("ClassNotFoundException: ");
                System.err.println(e.getMessage());
                System.exit(-1);	// a rather fatal exit
            }
            // build the url string from the stored info and try to connect
            String connectionUrl = "jdbc:mysql://" + _Server +
                    "/" + _DBName ;
            try {
                con = DriverManager.getConnection(connectionUrl, _UserName, _PassWord);
            } catch(SQLException ex) {
                System.err.println("SQLException: " + ex.getMessage());
                con = null ;
            }
        }
        return con ;
    }
}
