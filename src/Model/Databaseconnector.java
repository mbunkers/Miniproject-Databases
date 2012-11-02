/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Database;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marc Bunkers, Fabian Claasen
 */
public class Databaseconnector {
    
    public Databaseconnector(){
        
    }
    
    public void search(String str){
        System.out.println(str);
        
        try{
            java.sql.Connection con = Database.getConnection();
            Statement stmt = (com.mysql.jdbc.Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from thoughts where notes = " + str + ";");
            
            System.out.println(rs.getString(2));
       }catch(SQLException ex){
            System.err.println("can't find the file");
       }
    }
}
