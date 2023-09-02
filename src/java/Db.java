import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Redloaded
 */
public class Db {
    
    static Db instance = null;
    Connection conn = null;
    
    private Db() 
        throws ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");            
    }
    
    
    public static Db getDb() {
        if (instance == null){
            try {
                instance  =new Db();
                

            } catch (ClassNotFoundException e) {
                System.out.println("Failed to Create DB Connection: Driver Not Found");
            }
            
        }
        
        return instance;
    }  
    
    public Connection getConnection() {
        if (conn == null) {
            Db.getDb();

            try {
                conn = DriverManager.getConnection(
                     "jdbc:mysql://localhost:3306/workshopdb",
                     "dev",
                     "realdev"
                 ); 
            } catch(Exception e) {
                System.out.println("Connection Failed: "+ e);
            }
        }
        
        return conn;
        
    }

    
    public int addUser(String username, String dept) throws SQLException{
        PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO user VALUES (? , ?)");
        stmt.setString(1, username);
        stmt.setString(2, dept);

        int res =  stmt.executeUpdate();
        return res;
    }
    
    
    public ResultSet getAllUsers() throws SQLException{
        
        return getConnection()
                .createStatement()
                .executeQuery("SELECT * FROM user");
    }
    
    public ResultSet getAllUsers(String dept) throws SQLException {
        PreparedStatement stmt =  getConnection()
                .prepareStatement("SELECT * FROM user WHERE dept=?");
        stmt.setString(1, dept);
        return stmt.executeQuery();
    }
    
    
}
