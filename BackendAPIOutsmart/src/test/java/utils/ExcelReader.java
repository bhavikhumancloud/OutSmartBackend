package utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public static List<Map<String, String>> readSheet(String path, String sheetName) throws IOException {

        List<Map<String, String>> rows = new ArrayList<>();

        FileInputStream fis = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);

        }

        Row headerRow = sheet.getRow(0);

        int totalCols = headerRow.getPhysicalNumberOfCells();
        int totalRows = sheet.getPhysicalNumberOfRows();

        for (int r = 1; r < totalRows; r++) {

            Row row = sheet.getRow(r);
            if (row == null) continue;

            Map<String, String> map = new HashMap<>();

            for (int c = 0; c < totalCols; c++) {

                Cell headerCell = headerRow.getCell(c);
                Cell dataCell = row.getCell(c);

                String key = headerCell.getStringCellValue();
                String value = getCellValue(dataCell);

                map.put(key, value);
            }

            rows.add(map);
        }

        workbook.close();
        fis.close();
        return rows;
    }

    private static String getCellValue(Cell cell) {

        if (cell == null) return "";

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }

        else if (cell.getCellType() == CellType.NUMERIC) {

            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toString();
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }

        }

        else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }

        else if (cell.getCellType() == CellType.FORMULA) {
            return cell.getCellFormula();
        }

        else {
            return "";
        }

    }

}





