package ru.ladomitory.kontur.talkApi.kontur;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ladomitory.kontur.talkApi.httpApi.HttpLoader;
import ru.ladomitory.kontur.talkApi.util.JSONMap;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static ru.ladomitory.kontur.talkApi.util.PropertiesReader.getStringProperty;

public class KonturTalkLoader extends HttpLoader {
    private static final Logger logger = LogManager.getLogger(KonturTalkLoader.class);

    private static final String PROTOCOL = getStringProperty("loader.protocol");
    private static final String DOMAIN_NAME = getStringProperty("loader.domainName");
    private static final String CONFERENCE_HISTORY_PATH = getStringProperty("loader.conferenceHistory.path");
    private static final String CONFERENCE_REPORTS_PATH = getStringProperty("loader.conferenceReport.path");
    private static final String PARTICIPANTS_PATH = getStringProperty("loader.conferenceReport.participants.path");

    public KonturTalkLoader(String space, String authToken) {
        super(PROTOCOL + space + DOMAIN_NAME, authToken);

        logger.log(Level.INFO, "Instance of KonturTalkLoader is init");
    }

    public String getConferenceHistory() {
        return getConferenceHistory(null, null, 0, 100, null);
    }

    public String getConferenceHistory(String fromDate, String toDate, int skip, int take, List<String> roomNames) {
        JSONMap params = new JSONMap();
        if (fromDate != null) {
            params.put("fromDate", "\"" + fromDate + "\"");
        }
        if (toDate != null) {
            params.put("toDate", "\"" + toDate + "\"");
        }
        if (skip > 0) {
            params.put("\"skip\"", Integer.toString(skip));
        }
        if (take != 100) {
            params.put("\"take\"", Integer.toString(take));
        }
        if (roomNames != null && !roomNames.isEmpty()) {
            String array = "[\n";
            for (String roomName : roomNames) {
                array += "\"" + roomName + "\",\n";
            }
            array += "]";
            params.put("\"roomName\"", array);
        }
        HttpResponse<String> response = createGetRequest(CONFERENCE_HISTORY_PATH, params);
        if (response.statusCode() == 200) {
            System.out.println("Response: Status=" + response.statusCode());
            return response.body();
        } else if (response.statusCode() == 302) {
            System.out.println("Response: Status=" + response.statusCode());
            if (response.headers().firstValue("Location").isPresent()) {
                System.out.println("Response: New Location is present:" + response.headers().firstValue("Location").get());
                response = createGetRequest(response.headers().firstValue("Location").get());
                if (response.statusCode() == 200) {
                    System.out.println("Response: Status=" + response.statusCode());
                    return response.body();
                } else {
                    logger.log(Level.WARN, "Response: Status=" + response.statusCode());
                    return null;
                }
            } else {
                logger.log(Level.WARN, "Response: New Location is not present");
                return null;
            }
        } else {
            logger.log(Level.WARN, "Response: Status=" + response.statusCode());
            return null;
        }
    }

    public String getParticipantsReport(String conferenceKey) {
        HttpResponse<String> response = createGetRequest(CONFERENCE_REPORTS_PATH + conferenceKey + PARTICIPANTS_PATH);
        if (response.statusCode() == 200) {
            System.out.println("Response: Status=" + response.statusCode());
            return response.body();
        } else if (response.statusCode() == 302) {
            System.out.println("Response: Status=" + response.statusCode());
            if (response.headers().firstValue("Location").isPresent()) {
                System.out.println("Response: New Location is present:" + response.headers().firstValue("Location").get());
                response = createGetRequest(response.headers().firstValue("Location").get());
                if (response.statusCode() == 200) {
                    System.out.println("Response: Status=" + response.statusCode());
                    return response.body();
                } else {
                    logger.log(Level.WARN, "Response: Status=" + response.statusCode());
                    return null;
                }
            } else {
                logger.log(Level.WARN, "Response: New Location is not present");
                return null;
            }
        } else {
            logger.log(Level.WARN, "Response: Status=" + response.statusCode());
            return null;
        }
    }
}
