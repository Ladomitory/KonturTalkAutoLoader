package ru.ladomitory.kontur.talkApi.excel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelFileManager {
    private static final Logger logger = LogManager.getLogger(ExcelFileManager.class);

    private static final String FILE_DIR = "result/";
    private static final String FILE_TYPE = ".xlsx";

    private final FileInputStream INPUT_FILE;
    private final FileOutputStream OUTPUT_FILE;

    protected XSSFWorkbook book;

    public ExcelFileManager(String fileName) {
        String fileFullName = FILE_DIR + fileName + FILE_TYPE;
        File file = new File(fileFullName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error of create file");
            throw new RuntimeException(e);
        }
        try {
            INPUT_FILE = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, "Input File " + fileName + " not found");
            throw new RuntimeException(e);
        }
        logger.log(Level.INFO, "Init Input File " + fileName);
        try {
            OUTPUT_FILE = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, "Output File " + fileName + " not found");
            throw new RuntimeException(e);
        }
        logger.log(Level.INFO, "Init Output File " + fileName);

        try {
            book = new XSSFWorkbook(INPUT_FILE);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error of init New Workbook");
            throw new RuntimeException(e);
        }

        logger.log(Level.INFO, "ExcelFileManager is init");
    }

    public void close() {
        try {
            book.write(OUTPUT_FILE);
            book.close();
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error of close Workbook");
            throw new RuntimeException(e);
        }
    }
}
