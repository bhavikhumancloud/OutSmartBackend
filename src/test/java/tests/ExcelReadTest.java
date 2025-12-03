package tests;

import api.BaseTest;
import api.Config;
import org.testng.annotations.Test;
import utils.ExcelUtil;

import java.io.IOException;

public class ExcelReadTest extends BaseTest {

    ExcelUtil excelutil = new ExcelUtil();
//    String excelPath =  Config.get("excelPath");
//    String rootPath = System.getProperty("user.dir");
    String ExcelFile = System.getProperty("user.dir") + Config.get("excelPath");
    @Test
    public void testExcelRead() throws IOException {
        // Test implementation goes here
        excelutil.connectionToExcelFile(ExcelFile,"SignUp");
        int romCount = excelutil.getRowCount();
        System.out.println("Total number of rows: " + romCount);
    }

    @Test(description = "write the date in excel file")
    public void testExcelWrite() throws IOException {

        excelutil.connectionToExcelFile(ExcelFile,"SignUp");
        String cellData = excelutil.getCellData(0,1);
        System.out.println("Cell data at (0,1): " + cellData);
        excelutil.setCellValue(2,0,cellData);

        excelutil.writeExcelFile(ExcelFile);

        String colData = excelutil.getCellData(2,0);
        System.out.println("Cell data at (2,0): " + colData);

        excelutil.saveExcelFile();
    }

    @Test
    public void readDataFromExcel() throws IOException {
        excelutil.connectionToExcelFile(ExcelFile,"SignUp");
        String value = excelutil.getCellData(2,0);
        System.out.println("value is: "+value);
    }


    @Test
    public void writeDataToExcel() throws IOException {
        try{
            excelutil.connectionToExcelFile(ExcelFile,"SignUp");
            String output = excelutil.writeDataToExcel(5,5,"Hello World",ExcelFile);
            System.out.println("Data written successfully: "+output);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


