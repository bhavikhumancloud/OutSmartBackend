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

    public void connectionToExcelFile(String excelPath, String sheetName) throws IOException {
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

    public String writeDataToExcel(int rowNum, int colNum, String value,String excelPath) throws IOException {
        Row row  = getRow(rowNum);
       Cell cell = getCell(row,colNum);
       cell.setCellValue(value);
        writeExcelFile(excelPath);
       return getCellData(rowNum,colNum);
    }


    public Row getRow(int rowNum){
        Row row  = sheet.getRow(rowNum);
        if(row == null){
            row = sheet.createRow(rowNum);
        }
        return row;
    }

    public Cell getCell(Row row,int colNum){
        Cell cell = row.getCell(colNum);
        if(cell == null){
            cell = row.createCell(colNum);
        }
        return cell;
    }


    public String getCellStringValue(int rowNum, int colNum){
        try {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                return null;
            }

            Cell cell = row.getCell(colNum);
            if (cell == null) {
                return null;
            }
            return cell.getStringCellValue();
        }catch (Exception e){
            e.printStackTrace();
            return "Exception occurred: " + e.getMessage();
        }
    }
}