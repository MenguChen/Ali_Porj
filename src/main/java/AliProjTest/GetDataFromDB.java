package AliProjTest;
import AliProjTest.GetDataThread ;

public class GetDataFromDB{
    public static void main(String[] args){
        for(int i = 0 ; i < 5 ; i ++){
            new GetDataThread(i*100+1,(i+1)*100).start(); 
        }
    }
}