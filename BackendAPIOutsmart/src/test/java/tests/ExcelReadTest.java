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
}
