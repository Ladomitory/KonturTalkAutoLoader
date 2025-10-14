package ru.ladomitory.kontur.talkApi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ladomitory.kontur.talkApi.excel.ExcelDataManager;
import ru.ladomitory.kontur.talkApi.excel.ExcelFileManager;
import ru.ladomitory.kontur.talkApi.excel.KonturTalkDataManager;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    static void main() {
        KonturTalkDataManager manager = new KonturTalkDataManager();

    }
}
