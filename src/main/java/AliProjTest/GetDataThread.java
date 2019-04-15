package AliProjTest;
import java.sql.* ;
import java.util.Date;


public class GetDataThread extends Thread
{
    public int start ;
    public int end ;
    public String driver ;
    public String url ;
    public String user ;
    public String password ;
    public String sql ;
    public GetDataThread(String driver, String url, String user, String password, String sql)
    {
        this.driver = driver ;
        this.url = url ;
        this.user = user ;
        this.password = password ;
        this.sql = sql ;
    }
    public void run() 
    {
        
        Connection conn = null;
        Statement stmt = null;
        long beginTime = 0;
        long endTime = 0;

        try 
        {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            
            if(conn != null) 
            {
                System.out.println("获取连接成功");
                
                //文件存储路径
                String path = "F:\\Ali_Proj\\ExcelSet\\" + currentThread().getName() + ".csv" ;

                //开始计时
                beginTime = new Date().getTime();
                
                //执行查询语句，结果保存在rs内
                ResultSet rs = stmt.executeQuery(sql) ;

                //创建csv文件
                read_write_csv csvwrite = new read_write_csv(path) ;    

                //将查询结果rs写入csv文件中
                csvwrite.write(rs);    
                rs.close() ;

                //结束计时
                endTime = new Date().getTime();
                System.out.println(currentThread().getName()+"  开始时间:"+beginTime+"  结束时间:"+endTime+"  用时:"+(endTime-beginTime)+"ms");
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
            try{
            if(stmt!=null)
                conn.close();
            }catch(SQLException se){
            }
            try{
            if(conn!=null)
                conn.close();
            }catch(SQLException se){
            se.printStackTrace();
            }
        }
        System.out.println("That is all!");
   }
}    


        
