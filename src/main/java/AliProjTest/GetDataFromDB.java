package AliProjTest;
import AliProjTest.GetDataThread ;

public class GetDataFromDB{

    public GetDataFromDB(String driver, String url, String user, String password){

        String sql ;
        int start, end ;
        for(int i = 0 ; i < 30 ; i ++){
            //定义查询的SQL语句
            start = i*1000000+1 ;
            end = (i+1)*1000000 ;

            //定义sql查询语句
            sql = "select id,name,age,country from info where id between '"+start+"' and '"+end+"' order by age " ;
            new GetDataThread(driver, url, user, password, sql).start(); 
        }
    
    }
}