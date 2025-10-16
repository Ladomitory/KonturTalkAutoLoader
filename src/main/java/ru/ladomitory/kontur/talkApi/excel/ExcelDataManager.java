package ru.ladomitory.kontur.talkApi.excel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelDataManager extends ExcelFileManager {
    private static final Logger logger = LogManager.getLogger(ExcelDataManager.class);

    private Sheet sheet;

    public ExcelDataManager(String fileName, String sheetName) {
        super(fileName);

        sheet = book.getSheet(sheetName);
        if (sheet == null) {
            sheet = book.createSheet(sheetName);

        }
        logger.log(Level.INFO, "Init Sheet \"" + sheetName + "\"");

        logger.log(Level.INFO, "Instance of ExcelDataManager is init");
    }

    public String getStringCellValue(int rowNumber, int columnNumber) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            return "";
        } else {
            Cell cell = row.getCell(columnNumber);
            if (cell == null) {
                return "";
            } else {
                CellType type = cell.getCellType();
                return switch (type) {
                    case STRING -> cell.getStringCellValue();
                    case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
                    case NUMERIC -> Double.toString(cell.getNumericCellValue());
                    default -> null;
                };
            }
        }
    }

    public Double getNumericCellValue(int rowNumber, int columnNumber) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            return null;
        } else {
            Cell cell = row.getCell(columnNumber);
            if (cell == null) {
                return null;
            } else {
                CellType type = cell.getCellType();
                return switch (type) {
                    case NUMERIC -> cell.getNumericCellValue();
                    default -> null;
                };
            }
        }
    }

    public void setCellValue(int rowNumber, int columnNumber, String value) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber);
        }
        Cell cell = row.getCell(columnNumber);
        if (cell == null) {
            cell = row.createCell(columnNumber);
        }
        cell.setCellValue(value);
        save();
    }

    public void setCellValue(int rowNumber, int columnNumber, Double value) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber);
        }
        Cell cell = row.getCell(columnNumber);
        if (cell == null) {
            cell = row.createCell(columnNumber);
        }
        cell.setCellValue(value);
        save();
    }

    public int findStringValueInColumn(int columnNumber, String value) {
        for (Row currentRow : sheet) {
            Cell currentCell = currentRow.getCell(columnNumber);
            if (currentCell != null) {
                String currentValue = switch (currentCell.getCellType()) {
                    case NUMERIC -> Double.toString(currentCell.getNumericCellValue());
                    case BOOLEAN -> Boolean.toString(currentCell.getBooleanCellValue());
                    case STRING -> currentCell.getStringCellValue();
                    default -> "";
                };
                if (currentValue.equals(value)) {
                    return currentRow.getRowNum();
                }
            }
        }
        return -1;
    }

    public int findDoublesValueInColumns(int firstColumnNumber, int secondColumnNumber, String firstValue, String secondValue) {
        return findDoublesValueInColumnsAfter(firstColumnNumber, secondColumnNumber, firstValue, secondValue, 0);
    }

    public int findDoublesValueInColumnsAfter(int firstColumnNumber, int secondColumnNumber, String firstValue, String secondValue, int startRowNumber) {
        for (Row currentRow : sheet) {
            if (currentRow.getRowNum() >= startRowNumber) {
                String firstCurrentValue;
                Cell fistCurrentCell = currentRow.getCell(firstColumnNumber);
                if (fistCurrentCell == null) {
                    firstCurrentValue = "";
                } else {
                    firstCurrentValue = switch (fistCurrentCell.getCellType()) {
                        case NUMERIC -> Double.toString(fistCurrentCell.getNumericCellValue());
                        case BOOLEAN -> Boolean.toString(fistCurrentCell.getBooleanCellValue());
                        case STRING -> fistCurrentCell.getStringCellValue();
                        default -> "";
                    };
                }
                String secondCurrentValue;
                Cell secondCurrentCell = currentRow.getCell(secondColumnNumber);
                if (secondCurrentCell == null) {
                    secondCurrentValue = "";
                } else {
                    secondCurrentValue = switch (secondCurrentCell.getCellType()) {
                        case NUMERIC -> Double.toString(secondCurrentCell.getNumericCellValue());
                        case BOOLEAN -> Boolean.toString(secondCurrentCell.getBooleanCellValue());
                        case STRING -> fistCurrentCell.getStringCellValue();
                        default -> "";
                    };
                }
                if (firstCurrentValue.equals(firstValue) && secondCurrentValue.equals(secondValue)) {
                    return currentRow.getRowNum();
                }
            }
        }
        return startRowNumber - 1;
    }

    public int findLastInRow(int rowNumber) {
        return findLastInRowAfter(rowNumber, 0);
    }

    public int findLastInRowAfter(int rowNumber, int startColumnNumber) {
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            return startColumnNumber - 1;
        } else if (row.getLastCellNum() >= startColumnNumber) {
            return row.getLastCellNum();
        } else {
            return startColumnNumber - 1;
        }
    }

    public int findLastInColumn(int columnNumber) {
        int lastRowNumber = -1;
        for (Row row : sheet) {
            Cell cell = row.getCell(columnNumber);
            if (cell != null) {
                lastRowNumber = row.getRowNum();
            }
        }
        return lastRowNumber;
    }

    public int findLastInColumnAfter(int columnNumber, int startRowNumber) {
        int lastRowNumber = startRowNumber - 1;
        for (Row row : sheet) {
            if (row.getRowNum() >= startRowNumber) {
                Cell cell = row.getCell(columnNumber);
                if (cell != null) {
                    lastRowNumber = row.getRowNum();
                }
            }
        }
        return lastRowNumber;
    }
}
