package utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtil {

    private Sheet sheet;
    private Workbook workbook;
    private FileInputStream fis;
    private FileOutputStream fos;

    public void ExcelUtils(String excelPath, String sheetName) throws IOException {
        this.fis = new FileInputStream(excelPath);
        this.workbook = new XSSFWorkbook(fis);
        this.sheet = workbook.getSheet(sheetName);
    }

    public void writeExcelFile(String excelPath) throws IOException {
        this.fos = new FileOutputStream(excelPath);
        workbook.write(fos);
    }

    public void saveExcelFile() throws IOException {
        fos.close();
        workbook.close();

    }
    // -----------------------------
    // Read single cell value
    // -----------------------------
    public String getCellData(int rowNum, int colNum) {
        Cell cell = sheet.getRow(rowNum).getCell(colNum);
        return cell.toString();
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public void setCellValue(int rowNum,int colNum, String value){
        Row row = sheet.getRow(rowNum);
        if(row == null){
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if(cell == null){
            cell = row.createCell(colNum);
        }

        cell.setCellValue(value);
    }
}