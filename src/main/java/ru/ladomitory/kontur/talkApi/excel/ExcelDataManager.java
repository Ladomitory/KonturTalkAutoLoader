package ru.ladomitory.kontur.talkApi.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class ExcelDataManager extends ExcelFileManager {
    private static final Logger logger = LogManager.getLogger(ExcelDataManager.class);

    private Sheet sheet;

    private List<List<String>> data;

    public ExcelDataManager(String fileName, String sheetName) {
        super(fileName);
        sheet = book.getSheet(sheetName);
        data = new ArrayList<>();
        int rowCounter = 0;
        int cellCounter;
        for (Row r : sheet) {
            data.add(rowCounter, new ArrayList<String>());
            cellCounter = 0;
            for (Cell c : r) {
                data.get(rowCounter).add(cellCounter, c.getStringCellValue());
                cellCounter++;
            }
            rowCounter++;
        }
    }
}
