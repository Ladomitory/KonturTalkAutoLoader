package ru.ladomitory.kontur.talkApi;

import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ladomitory.kontur.talkApi.jsonModel.Conference;
import ru.ladomitory.kontur.talkApi.jsonModel.ConferenceHistory;
import ru.ladomitory.kontur.talkApi.jsonModel.Participant;
import ru.ladomitory.kontur.talkApi.jsonModel.ParticipantsReport;
import ru.ladomitory.kontur.talkApi.kontur.KonturTalkDataManager;
import ru.ladomitory.kontur.talkApi.kontur.KonturTalkLoader;

import static ru.ladomitory.kontur.talkApi.util.PropertiesReader.getStringProperty;

public class CentralManager {
    private static final Logger logger = LogManager.getLogger(CentralManager.class);

    private final String SPACE = getStringProperty("kontur.talk.space");
    private final String TOKEN = getStringProperty("kontur.talk.token");
    private final KonturTalkDataManager DATA_MANAGER;
    private final KonturTalkLoader LOADER;
    private final Gson JSON_CONVERTER;

    public CentralManager() {
        DATA_MANAGER = new KonturTalkDataManager();
        LOADER = new KonturTalkLoader(SPACE, TOKEN);
        JSON_CONVERTER = new Gson();
    }

    public void start() {
        String historyJSON = LOADER.getConferenceHistory();
        if (historyJSON != null) {
            ConferenceHistory history = JSON_CONVERTER.fromJson(historyJSON, ConferenceHistory.class);
            for (Conference conference : history.getList()) {
                int conferenceColumn = DATA_MANAGER.addConference(conference);
                String participantsJSON = LOADER.getParticipantsReport(conference.getKey());
                if (participantsJSON != null) {
                    ParticipantsReport report = JSON_CONVERTER.fromJson(participantsJSON, ParticipantsReport.class);
                    for (Participant participant : report.getList()) {
                        DATA_MANAGER.addParticipant(participant, conferenceColumn);
                    }
                } else {
                    logger.log(Level.INFO, "ParticipantsReport is null");
                }
            }
        } else {
            logger.log(Level.INFO, "ConferenceHistory is null");
        }
    }
}
