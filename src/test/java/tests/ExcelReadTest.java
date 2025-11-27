package tests;

import api.BaseTest;
import org.testng.annotations.Test;
import utils.ExcelUtil;

import java.io.IOException;

public class ExcelReadTest extends BaseTest {

    ExcelUtil excelutil = new ExcelUtil();
    @Test
    public void testExcelRead() throws IOException {
        // Test implementation goes here
        excelutil.ExcelUtils("C:\\Users\\HC-LEN-THINKPAD-02\\OneDrive - Humancloud Technologies Pvt. Ltd\\24Nov\\BackendAPIOutsmart\\src\\test\\resources\\TestDataOutsmart.xlsx","SignUp");
        int romCount = excelutil.getRowCount();
        System.out.println("Total number of rows: " + romCount);
    }

    @Test(description = "write the date in excel file")
    public void testExcelWrite() throws IOException {
        // Test implementation goes here
        excelutil.ExcelUtils("C:\\Users\\HC-LEN-THINKPAD-02\\OneDrive - Humancloud Technologies Pvt. Ltd\\24Nov\\BackendAPIOutsmart\\src\\test\\resources\\TestDataOutsmart.xlsx","SignUp");
        String cellData = excelutil.getCellData(0,1);
        System.out.println("Cell data at (0,1): " + cellData);
        excelutil.setCellValue(2,0,cellData);

        excelutil.writeExcelFile("C:\\Users\\HC-LEN-THINKPAD-02\\OneDrive - Humancloud Technologies Pvt. Ltd\\24Nov\\BackendAPIOutsmart\\src\\test\\resources\\TestDataOutsmart.xlsx");

        String colData = excelutil.getCellData(2,0);
        System.out.println("Cell data at (2,0): " + colData);
    }
}
