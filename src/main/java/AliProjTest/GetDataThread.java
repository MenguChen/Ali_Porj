package AliProjTest;
import java.sql.* ;
import java.util.ArrayList;
import java.util.Date;
// import java.io.FileOutputStream;
import java.io.*;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import java.io.FileOutputStream;

public class GetDataThread extends Thread
{
    public int start ;
    public int end ;

    public GetDataThread(int start, int end)
    {
        this.start = start ;
        this.end = end ;
    }
    public void run() 
    {
        final String driver = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost/person?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        final String user = "root";
        final String password = "123456";

        Connection conn = null;
        Statement stmt = null;
        long beginTime = 0;
        long endTime = 0;

        try 
        {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);//获取连接 
            conn.setAutoCommit(false);//关闭自动提交
            stmt = conn.createStatement();
            
            if(conn != null) 
            {
                System.out.println("获取连接成功");
                
                //初始化写入的文件以及配置
                String path = "F:\\Ali_Proj\\ExcelSet\\" + currentThread().getName() + ".csv" ;
                XSSFWorkbook wb = new XSSFWorkbook() ;
                XSSFSheet sheet = wb.createSheet("sheet1") ;
                XSSFRow row ;
                XSSFCell cell ;

                beginTime = new Date().getTime();//开始计时
                
                // 定义sql
                String sql = "select id,name,age,country from info where id between '"+start+"' and '"+end+"' and sex = 'male' order by age " ;
                ResultSet rs = stmt.executeQuery(sql) ;

                // int index = 0 ;
                // while(rs.next())
                // {
                //     row = sheet.createRow(index++) ;
                //     row.createCell(0).setCellValue(rs.getInt("id"));
                //     row.createCell(1).setCellValue(rs.getString("name"));
                //     row.createCell(2).setCellValue(rs.getInt("age"));
                //     row.createCell(3).setCellValue(rs.getString("country"));
                // }

                read_write_csv csvwrite = new read_write_csv(path) ;
                csvwrite.write(rs);
                rs.close() ;
                // try{
                //     // FileOutputStream outputStream = new FileOutputStream(path) ;
                //     BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path)) ;
                //     // fw = new FileWriter(path) ;
                //     // bw = new BufferedWriter(fw) ;
                //     wb.write(outputStream);
                //     outputStream.flush();
                //     outputStream.close();
                //     System.out.println("写入成功");
                // } catch (Exception e){
                //     System.out.println("写入失败");
                //     e.printStackTrace();
                // }
                endTime = new Date().getTime();//开始计时
                System.out.println(currentThread().getName()+"  开始时间:"+beginTime+"  结束时间:"+endTime+"  用时:"+(endTime-beginTime)+"ms");
                // System.out.println(endTime-beginTime);
            }
            else {
                System.out.println("数据库连接失败");
            }
        } 
        catch (ClassNotFoundException e1) {
        e1.printStackTrace();
        } catch (SQLException e) {
        e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
            if(stmt!=null)
                conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
            if(conn!=null)
                conn.close();
            }catch(SQLException se){
            se.printStackTrace();
            }//end finally try 
        }//end try
        System.out.println("That is all!");
   }
}    


        
