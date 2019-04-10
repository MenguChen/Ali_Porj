package AliProjTest;
import AliProjTest.GetDataThread ;

public class GetDataFromDB{
    public static void main(String[] args){
        for(int i = 0 ; i < 10 ; i ++){
            new GetDataThread(i*1000000+1,(i+1)*1000000).start(); 
        }
    }
}