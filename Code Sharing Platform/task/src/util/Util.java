package util;

import entity.CodeResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author namvdo
 */
public class Util {
    public final String API_CODE = "/api/code/";
    public final String WEB_CODE = "/code/";

    public final String API_CODE_NEW = "/api/code/new";
    public final String WEB_CODE_NEW = "/code/new";

    public final String API_LATEST = "/api/code/latest";
    public final String WEB_LATEST = "/code/latest";

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

    public static String injectCodeTimeToHtml(String code, String timeFormatted) {
        return "<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <span id=\"load_date\">" + timeFormatted +
                "</span>\n" +
                "    <pre id=\"code_snippet\">" + code + "</pre>\n" +
                "</body>\n" +
                "</html>";
    }

}
