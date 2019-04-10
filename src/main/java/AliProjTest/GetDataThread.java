package AliProjTest;
import java.sql.* ;
import java.util.Date;

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
        
                beginTime = new Date().getTime();//开始计时
                
                // 定义sql
                String sql = "select id,name,age,country from info_part where id between '"+start+"' and '"+end+"' and sex = 'male' order by age " ;
                ResultSet rs = stmt.executeQuery(sql) ;
                
                while(rs.next())
                {
                    int id = rs.getInt("id") ;
                    String name = rs.getString("name") ;
                    int age = rs.getInt("age") ;
                    String country = rs.getString("country") ;
                    // System.out.print("id: " + id);
                    // System.out.print(", name: " + name) ;
                    // System.out.print(", age: " + age) ;
                    // System.out.println(", country: " + country);
                }
                rs.close() ;
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


        
