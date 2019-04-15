package AliProjTest;
import java.sql.* ;
import java.text.SimpleDateFormat;
import java.util.Date;



public class InsertReconds
{
    
    public InsertReconds(String driver, String url, String user, String password) {

        Connection conn = null;
        PreparedStatement pst =  null;
        long beginTime = 0;
        long endTime = 0;
        try {
            Class.forName(driver);   //连接数据库
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null) {
                System.out.println("获取连接成功");
                
                //自定义一些变量的可选值
                String name = "abcdefghijklmnopqrstuvwsyz" ;
                String [] sex = {"male","female"} ;
                String [] country = {"China","USA","UK","China Hongkong","China Taiwan","Canada","Japan","Australia","India","Russia","France","Italy","German","Ice Land","China Macau"} ;
                SimpleDateFormat ft = new SimpleDateFormat ("YYYY-MM-dd hh:mm:ss");

                //开始计时
                beginTime = new Date().getTime();
                
                 //保存sql前缀
                String sqlPrefix = "insert into info_part (id,name,sex,phone,join_time,age,country) values ";
               
                StringBuffer suffix = new StringBuffer();
                
                //设置事务为非自动提交
                conn.setAutoCommit(false);
                
                //准备执行语句
                pst = (PreparedStatement) conn.prepareStatement("");
                
                // 外层循环，总提交事务次数，当初始化攒够10万条数据时向数据库提交一次，初始化3000W条数据，每条数据的id是唯一的
                for (int i = 0; i < 300 ; i++) {
                    suffix = new StringBuffer();
                    Date Join_Time = new Date() ;
                    for (int j = 1; j <= 100000; j++) {
                        //构建sql后缀，生成一个0-14的随机数
                        int num = (int)(Math.random()*15) ;
                        int phone = (int)(Math.random()*999999999) ;
                        suffix.append("('"+ (i*100000+j) +"','"+ name.substring(num, num+6) +"','"+ sex[num%2] +"','"+ phone +"','"+ ft.format(Join_Time) +"','"+ num*7 +"','"+ country[num] +"'"+"),");
                    }

                    //组建完整SQL
                    String sql = sqlPrefix + suffix.substring(0, suffix.length() - 1);
                    //添加执行SQL
                    pst.addBatch(sql);
                    //执行操作
                    pst.executeBatch();
                    //提交事务
                    conn.commit();
                    //清空上一次添加的数据
                    suffix = new StringBuffer();
                }
                //计时结束
                endTime = new Date().getTime();

            }else {
                System.out.println("数据库连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("com.mysql.jdbc.Driver驱动类没有找到");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库地址错误");
        }finally {
            System.out.println("插入成功，所有时间："+ (endTime-beginTime));
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}
