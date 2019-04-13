package AliProjTest;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.*;

public class App 
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/world?useUnicode=true&characterEncoding=utf-8&useSSL=false" ;

    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {

        Merge mer = new Merge(2,"csv") ;

    //     try{
    //         read_write_csv testcsv = new read_write_csv(path) ;
    //         testcsv.write();
    //         testcsv.read();
    //    } catch (Exception e){
    //        System.out.println("写入失败");
    //        e.printStackTrace();
    //    }



        // Connection conn = null;
        // Statement stmt = null;
        // try{
        //    //STEP 2: Register JDBC driver
        //    Class.forName(JDBC_DRIVER);
     
        //    //STEP 3: Open a connection
        //    System.out.println("Connecting to a selected database...");
        //    conn = DriverManager.getConnection(DB_URL, USER, PASS);
        //    System.out.println("Connected database successfully...");
     
        //    //STEP 4: Execute a query
        //    System.out.println("Creating statement...");
        //    stmt = conn.createStatement();
     
        //    String sql = "SELECT Code, Name FROM country";
        //    ResultSet rs = stmt.executeQuery(sql);
        //    //STEP 5: Extract data from result set
        //     while(rs.next()){
        //       //Retrieve by column name
        //       String Code  = rs.getString("Code");
        //       String Name = rs.getString("Name");

        //       //Display values
        //       System.out.print("Code: " + Code);
        //       System.out.println(", Name: " + Name);
        //     }
        //     rs.close();
        // }catch(SQLException se){
        //    //Handle errors for JDBC
        //    se.printStackTrace();
        // }catch(Exception e){
        //    //Handle errors for Class.forName
        //    e.printStackTrace();
        // }finally{
        //    //finally block used to close resources
        //    try{
        //       if(stmt!=null)
        //          conn.close();
        //    }catch(SQLException se){
        //    }// do nothing
        //    try{
        //       if(conn!=null)
        //          conn.close();
        //    }catch(SQLException se){
        //       se.printStackTrace();
        //    }//end finally try
        // }//end try
        // System.out.println("That is all!");
     }
}
