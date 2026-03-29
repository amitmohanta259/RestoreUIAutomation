package com.restoreSkill.Automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtils {
    private ExcelUtils() {
    }

    public static Object[][] readSheetData(String excelPath, String sheetName) {
        try {
            ensureExcelExists(excelPath, sheetName);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create default Excel test data at: " + excelPath, e);
        }

        try (FileInputStream fis = new FileInputStream(excelPath); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                throw new IllegalArgumentException("No test data rows found in sheet: " + sheetName);
            }

            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
            Object[][] data = new Object[rowCount - 1][colCount];

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    data[i - 1][j] = getCellValueAsString(row, j);
                }
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read Excel file: " + excelPath, e);
        }
    }

    private static String getCellValueAsString(Row row, int colIndex) {
        if (row == null || row.getCell(colIndex) == null) {
            return "";
        }
        if (row.getCell(colIndex).getCellType() == CellType.STRING) {
            return row.getCell(colIndex).getStringCellValue();
        }
        return row.getCell(colIndex).toString();
    }

    private static void ensureExcelExists(String excelPath, String sheetName) throws IOException {
        Path path = Paths.get(excelPath);
        if (Files.exists(path)) {
            return;
        }

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (Workbook workbook = new XSSFWorkbook(); OutputStream outputStream = Files.newOutputStream(path)) {
            Sheet sheet = workbook.createSheet(sheetName);
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("username");
            header.createCell(1).setCellValue("password");

            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue("Admin");
            row.createCell(1).setCellValue("admin123");

            workbook.write(outputStream);
        }
    }
}
