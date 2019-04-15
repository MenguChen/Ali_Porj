package AliProjTest;
import java.io.*;
import java.util.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.Reader;
import org.apache.commons.csv.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class Merge{

    private Queue<person> queue_person = new PriorityQueue<>(perComparator) ;
    public  Reader[] readerset ;   //n个文件的Reader集合
    public  CSVParser[] csvParser ;   //n个CSV文件的Parser集合
    public  int perdata = 100000 ;   //每次从csv中读取的数据量，默认设定为100000 
    public  int persheet = 1000000 ;   //每张sheet表中可以存入的数据量
    public  int n ;   //需要归并的文件数
    public  int[] datafromfile ;

    //类似于主函数，传入值n为需要归并排序的文件数量
    public Merge(int file_num, String TargetFileType) {
        
        this.n = file_num ;
        this.readerset = new Reader[n] ;
        this.csvParser = new CSVParser[n] ;
        this.datafromfile = new int [n] ;

        InitQueue() ; 

        String TargetFilePath = "F:\\Ali_Proj\\ExcelSet\\Merge."+TargetFileType ;
        
        if(TargetFileType=="csv")
            WriteToCsv(TargetFilePath) ;
        else
            WriteToExcel(TargetFilePath) ;

    }
    
    public void InitQueue(){

        //初始化csvreader和csvparser
        for(int i = 0 ; i < n ; i ++){
            try{
                readerset[i] = Files.newBufferedReader(Paths.get("F:\\Ali_Proj\\ExcelSet\\Thread-"+i+".csv")) ;
                csvParser[i] = new CSVParser(readerset[i], CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()) ;
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        for(int j = 0 ; j < n  ; j ++)
            csvParser[j] = ReadData(csvParser[j], j, perdata) ;
    }

    public void show(){
        for(int i = 0 ; i < n ; i ++)
            System.out.print(" " + datafromfile[i]);
        System.out.println() ;    
    }


    public void WriteToCsv(String Path){

        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(Path)) ;
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id","name","age","country")) ;
            int fileindex ;
            person per ;
            System.out.println("开始写入");
            while(!queue_person.isEmpty())
            {
                per = queue_person.poll() ;
                csvPrinter.printRecord(per.id, per.name, per.age, per.country);
                
                fileindex = per.file_index ;
                datafromfile[fileindex] ++ ;   //统计数据来自某个源文件的次数，当次数达到perdata时，说明可以重新从该源文件中载入数据了
                if(datafromfile[fileindex]==perdata){
                    // show() ;
                    csvParser[fileindex] = ReadData(csvParser[fileindex], fileindex, perdata) ;
                    datafromfile[fileindex] = 0 ;
                    csvPrinter.flush();    //重新载入数据后也可以适当地清空一下缓存
                }
            }
            csvPrinter.flush();
            csvPrinter.close();
            System.out.println("写入成功");
        } catch (Exception e){
            System.out.println("写入失败");
            e.printStackTrace();
        }
    }

    public void WriteToExcel(String Path){

        SXSSFWorkbook wb = new SXSSFWorkbook() ;
        try{
            FileOutputStream out = new FileOutputStream(Path);
            wb.setCompressTempFiles(true);
            Sheet sheet ;
            Row row ;
            Cell cell ;

            int fileindex ;
            int rowindex = 0 ;
            person per ;

            System.out.println("开始写入");

            for(int i = 0 ; i < 10 ; i ++){
                sheet = wb.createSheet("sheet"+i) ;
                rowindex = 0 ;
                while(!queue_person.isEmpty() && rowindex<persheet)
                {
                    per = queue_person.poll() ;
                    row = sheet.createRow(rowindex++) ;
                    row.createCell(0).setCellValue(per.id);
                    row.createCell(1).setCellValue(per.name);
                    row.createCell(2).setCellValue(per.age);
                    row.createCell(3).setCellValue(per.country);
                    
                    fileindex = per.file_index ;
                    datafromfile[fileindex] ++ ;   //统计数据来自某个源文件的次数，当    次数达到perdata时，说明可以重新从该源文件中载入数据了
                    if(datafromfile[fileindex]==perdata){
                        // show() ;
                        csvParser[fileindex] = ReadData(csvParser[fileindex], fileindex, perdata) ;
                        datafromfile[fileindex] = 0 ;
                    }
                }
            }

            wb.write(out) ;
            out.close() ;
        } catch (Exception e){
            System.out.println("写入失败");
            e.printStackTrace();
        } finally{
            if(wb != null){
                wb.dispose() ; //删除临时表
            }
        }

    }

    public static Comparator<person> perComparator = new Comparator<person>(){
        @Override
        public int compare(person o1, person o2) {
            if(o1.age==o2.age)
                return o1.id-o2.id ;
            return o1.age-o2.age ;
        }
    };

    //从指定csvParser中读取perdata行数据
    public CSVParser ReadData(CSVParser csvParser, int index, int perdata){
        int count = 0 ;
        for(CSVRecord csvRecord : csvParser){
            person per = new person(Integer.parseInt(csvRecord.get("id")), csvRecord.get("name"), Integer.parseInt(csvRecord.get("age")), csvRecord.get("country"), index) ;
            queue_person.add(per) ;
            // System.out.println(per.id);
            count ++ ;
            if(count==perdata)
                break ;
        }
        return csvParser ;
    }
}