package ru.ladomitory.kontur.talkApi.excel;

public class KonturTalkDataManager extends ExcelDataManager{
    private static final String FILE_NAME = "data";
    private static final String SHEET_NAME = "KonturTalk";

    public KonturTalkDataManager() {
        super(FILE_NAME, SHEET_NAME);
    }
}
