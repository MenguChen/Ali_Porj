package AliProjTest;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.io.Reader;
import org.apache.commons.csv.*;

public class read_write_csv{
    
    public String path ;

    public read_write_csv(String path){
        this.path = path ;
    }

    public void read(){
        try{
            Reader reader = Files.newBufferedReader(Paths.get(path)) ;
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("id","name","age","country").withIgnoreHeaderCase().withTrim());
        
            for(CSVRecord csvRecord : csvParser){
                System.out.print(csvRecord.get("id"));
                System.out.print(csvRecord.get("name"));
                System.out.print(csvRecord.get("age"));
                System.out.println(csvRecord.get("country"));
            }
            System.out.println("读取成功") ;
        } catch (Exception e){
            System.out.println("读取失败");
            e.printStackTrace();
        }
        
    }

    public void write(ResultSet rs){
        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path)) ;
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id","name","age","country")) ;
            
            while(rs.next())
            {
                csvPrinter.printRecord(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("country"));
            }
            csvPrinter.flush();
            csvPrinter.close();
            System.out.println("写入成功");
        } catch (Exception e){
            System.out.println("写入失败");
            e.printStackTrace();
        }
    }
}
