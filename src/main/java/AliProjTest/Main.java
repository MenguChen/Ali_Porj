package AliProjTest;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.*;
import java.util.Date;
import java.util.concurrent.Semaphore;

import com.mysql.fabric.xmlrpc.base.Data;


public class Main
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/person?useUnicode=true&characterEncoding=utf-8&useSSL=false" ;
    static final String USER = "root";
    static final String PASS = "123456";
    static long begintime, endtime ;
    static GetDataThread[] thread = new GetDataThread [10] ;

    public static void main(String[] args) {

        //初始化随机生成实验数据，默认设定为3000W条，执行一次即可
        // InsertReconds insertdata = new InsertReconds(JDBC_DRIVER, DB_URL, USER, PASS) ;   
        
        begintime = new Date().getTime();

        //多线程调用GetDataThread函数来实现具体的查询操作，可以给每个查询自定义不同的DB_URL，USER，PASS和SQL查询语句
        String sql ;
        int start, end ;
        for(int i = 0 ; i < 10 ; i ++){
                //设置查询区间，模拟分布式查询
                start = i*1000000+1 ;
                end = (i+1)*1000000 ;

                //定义SQL查询语句，在该样例中规定查询的id范围（模拟分布式查询）
                sql = "select id,name,age,country from info_part where id between '"+start+"' and '"+end+"' order by age " ;
                //实现具体的查询操作，并对查询的结果写入子查询文件
                thread[i] = new GetDataThread(JDBC_DRIVER, DB_URL, USER, PASS, sql) ;
                thread[i].start();
        }

        for(int i = 0 ; i < 10 ; i ++){
            try{
                thread[i].join();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        endtime = new Date().getTime() ;
        System.out.println("Select Time Cost: " + (endtime-begintime) + " ms.");
        
        //初始化Merge对象时需要说明需要归并的子文件数量和指定的生成文件格式
        Merge mer = new Merge(10,"xlsx") ;

        endtime = new Date().getTime() ;
        System.out.println("Total Time Cost: " + (endtime-begintime) + " ms.");
     }
}
