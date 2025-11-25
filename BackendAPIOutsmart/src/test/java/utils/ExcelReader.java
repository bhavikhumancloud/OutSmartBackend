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
            workbook.close();
            fis.close();
            throw new RuntimeException("Sheet not found: " + sheetName);
        }

        Row headerRow = sheet.getRow(0);
        int totalCols = headerRow.getPhysicalNumberOfCells();
        int totalRows = sheet.getPhysicalNumberOfRows();
        DataFormatter formatter = new DataFormatter(); // Formatter to get cell as String

        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;

            Map<String, String> map = new HashMap<>();
            for (int c = 0; c < totalCols; c++) {
                Cell headerCell = headerRow.getCell(c);
                Cell dataCell = row.getCell(c);
                String key = formatter.formatCellValue(headerCell);
                String value = formatter.formatCellValue(dataCell);
                map.put(key, value);
            }
            rows.add(map);
        }

        workbook.close();
        fis.close();
        return rows;
    }
}






