package util;

import dao.CodeResponseRepository;
import entity.CodeResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author namvdo
 */
public class Util {
    public static final String API_CODE = "/api/code/";
    public static final String WEB_CODE = "/code/";

    public static final String API_CODE_NEW = "/api/code/new";
    public final String WEB_CODE_NEW = "/code/new";

    public static final String API_LATEST = "/api/code/latest";
    public static final String WEB_LATEST = "/code/latest";
    public static final String CODE_STATUS = "code-status";
    public static final int NOT_FOUND = 404;
    public static final String DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSSSSS";

    private Util() {

    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return dateTime.format(format);
    }

    public static List<CodeResponse> sortDateDescending(List<CodeResponse> codes) {
        codes.sort((a, b) -> -1 * (a.getDate().compareTo(b.getDate())));
        return codes;
    }


    public static String getRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
