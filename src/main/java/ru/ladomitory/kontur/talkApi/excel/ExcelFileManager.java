package ru.ladomitory.kontur.talkApi.excel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EmptyFileException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import static ru.ladomitory.kontur.talkApi.util.PropertiesReader.getStringProperty;

public class ExcelFileManager {
    private static final Logger logger = LogManager.getLogger(ExcelFileManager.class);

    private static final String FILE_DIR = getStringProperty("fileManager.directory");
    private static final String FILE_TYPE = getStringProperty("filaManager.file.type");

    private final File OUTPUT_FILE;

    protected XSSFWorkbook book;

    public ExcelFileManager(String fileName) {
        String fileFullName = FILE_DIR + fileName + FILE_TYPE;
        OUTPUT_FILE = new File(fileFullName);
        //TODO: Сделать нормальное создание файлов (сейчас выпадает ошибка EmptyFileException)
//        try {
//            inputFile.createNewFile();
//        } catch (IOException e) {
//            logger.log(Level.ERROR, "Error of create input file");
//            throw new RuntimeException(e);
//        }
//        try {
//            outputFile.createNewFile();
//        } catch (IOException e) {
//            logger.log(Level.ERROR, "Error of create output file");
//            throw new RuntimeException(e);
//        }

        try {
            book = new XSSFWorkbook();
        } catch (EmptyFileException e) {
            logger.log(Level.ERROR, "Error of init New Workbook");
            throw new RuntimeException(e);
        }

        logger.log(Level.INFO, "Instance of ExcelFileManager is init");
    }

    public void save() {
        try {
            book.write(new FileOutputStream(OUTPUT_FILE));
        } catch (IOException e) {
            logger.log(Level.ERROR, "Save error");
            throw new RuntimeException(e);
        }
    }

    public void closeAndSave() {
        try {
            book.write(new FileOutputStream(OUTPUT_FILE));
            book.close();
            logger.log(Level.INFO, "Successful close files");
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error of close Workbook");
            throw new RuntimeException(e);
        }
    }
}
