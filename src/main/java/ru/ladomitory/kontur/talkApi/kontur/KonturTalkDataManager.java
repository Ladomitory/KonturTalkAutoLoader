package ru.ladomitory.kontur.talkApi.kontur;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ladomitory.kontur.talkApi.excel.ExcelDataManager;
import ru.ladomitory.kontur.talkApi.jsonModel.Conference;
import ru.ladomitory.kontur.talkApi.jsonModel.Participant;

import static ru.ladomitory.kontur.talkApi.util.PropertiesReader.getStringProperty;
import static ru.ladomitory.kontur.talkApi.util.PropertiesReader.getIntProperty;

public class KonturTalkDataManager extends ExcelDataManager {
    private static final Logger logger = LogManager.getLogger(KonturTalkDataManager.class);

    private static final String FILE_NAME = getStringProperty("dataManager.file.name");
    private static final String SHEET_NAME = getStringProperty("dataManager.sheet.name");
    private static final int CONFERENCES_ROW = getIntProperty("dataManager.conference.row");
    private static final int PARTICIPANT_NAME_COLUMN = getIntProperty("dataManager.participant.name.column");
    private static final int PARTICIPANT_ID_COLUMN = getIntProperty("dataManager.participant.id.column");
    private static final int HORIZONTAL_INDENT = getIntProperty("dataManager.indent.horizontal");
    private static final int VERTICAL_INDENT = getIntProperty("dataManager.indent.vertical");

    public KonturTalkDataManager() {
        super(FILE_NAME, SHEET_NAME);

        logger.log(Level.INFO, "Instance of KonturTalkDataManager is init");
    }

    public int addConference(Conference conference) {
        int lastConferenceColumn = findLastInRowAfter(CONFERENCES_ROW, HORIZONTAL_INDENT);
        setCellValue(0, lastConferenceColumn + 1, conference.toString());
        return lastConferenceColumn + 1;
    }

    public void addParticipant(Participant participant, int conferenceColumnNumber) {
        int participantRow = findDoublesValueInColumnsAfter(PARTICIPANT_ID_COLUMN, PARTICIPANT_NAME_COLUMN,
                participant.getId(), participant.getName(), VERTICAL_INDENT);
        if (participantRow == -1) {
            participantRow = Math.max(findLastInColumnAfter(PARTICIPANT_ID_COLUMN, VERTICAL_INDENT),
                    findLastInColumnAfter(PARTICIPANT_NAME_COLUMN, VERTICAL_INDENT)) + 1;
            setCellValue(participantRow, PARTICIPANT_ID_COLUMN, participant.getId());
            setCellValue(participantRow, PARTICIPANT_NAME_COLUMN, participant.getName());
        }
        setCellValue(participantRow, conferenceColumnNumber, 1.0);
    }
}
