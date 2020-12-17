package platform;

import com.google.gson.JsonObject;
import entity.CodeResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import util.Template;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author namvdo
 */
@SpringBootApplication
@RestController
public class CodeSharingPlatform {
    private CodeResponse code;
    Logger logger = Logger.getLogger(CodeSharingPlatform.class.getName());
    @GetMapping(path = "/code")
    public String responseWithHtml(HttpServletResponse response) {
        response.setContentType("text/html");
        String codeFragment = getTheCode();
        String time = getTheTime();

        return injectCodeToHtml(codeFragment, time);
    }

    @GetMapping(path = "/api/code")
    @ResponseStatus(HttpStatus.OK)
    public String responseWithJson(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setStatus(200);
        JsonObject json = new JsonObject();
        String codeFragment = getTheCode();
        String time = getTheTime();
        json.addProperty("code", codeFragment);
        json.addProperty("date", time);
        return json.toString();
    }

    @GetMapping(path = "/code/new")
    public String responseWithForm() {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Create</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form action=\"/api/code/new\" method=\"POST\">\n" +
                "        <textarea name=\"code\" id=\"code_snippet\" cols=\"30\" rows=\"10\"></textarea>\n" +
                "        <button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>\n" +
                "    </form> \n" +
                "    <script src=\"/script/script.js\"></script>\n" +
                "</body>\n" +
                "</html>";
    }

    @PostMapping(path = "/api/code/new")
    public Map<String, String> makeCodeChange(@RequestBody CodeResponse body) {
        if (code == null) {
            code = new CodeResponse();
        }
        code.setCode(body.getCode() == null ? "" : body.getCode());
        code.setDate(body.getDate() == null ? LocalDateTime.now() : body.getDate());
        return new HashMap<>(5);
    }

    private String injectCodeToHtml(String code, String timeFormatted) {
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

    private String getTheCode() {
        String codeTemplate = Template.CODE;
        if (code == null) {
            code = new CodeResponse.CodeBuilder(codeTemplate).dateTime(LocalDateTime.now()).build();
        }
        return code.getCode();
    }

    private String getTheTime() {
        if (code == null) {
            return formatDateTime(LocalDateTime.now());
        } else {
            return formatDateTime(code.getDate());
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(Template.DATETIME_FORMAT);
        return dateTime.format(format);
    }

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

}
