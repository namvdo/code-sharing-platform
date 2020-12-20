package service;

import com.google.gson.JsonObject;
import entity.CodeResponse;
import org.springframework.stereotype.Service;
import util.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author namvdo
 */
@Service
public class CodeSharingService {
    private final Map<Integer, CodeResponse> codes = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    public List<CodeResponse> getTop10Latest() {
        List<CodeResponse> top10Latest = new ArrayList<>(codes.values()).subList(Math.max(0, codes.size() - 10), codes.size());
        return Util.sortDateDescending(top10Latest);
    }

    public boolean addNewCode(CodeResponse newCode) {
        String code = newCode.getCode() == null ? "" : newCode.getCode();
        LocalDateTime time = newCode.getDate() == null ? LocalDateTime.now() : LocalDateTime.parse(newCode.getDate(),
                DateTimeFormatter.ofPattern(Util.DATETIME_FORMAT));
        CodeResponse codeObj = new CodeResponse.CodeBuilder(code).dateTime(time).build();
        codes.put(currentId.get(), codeObj);
        currentId.getAndIncrement();
        return true;
    }

    public String responseToAddCode(int id) {
        JsonObject json = new JsonObject();
        json.addProperty("id", String.valueOf(id));
        return json.toString();
    }

    public String getCodeAtPos(int pos) {
        CodeResponse code = codes.get(pos);
        String codeFragment = code.getCode();
        String time = code.getDate();
        return Util.injectCodeTimeToHtml(codeFragment, time);
    }

    public String getCodeAtPostJson(int pos) {
        JsonObject json = new JsonObject();
        CodeResponse code = codes.get(pos);
        String codeFragment = code.getCode();
        String time = code.getDate();
        json.addProperty("code", codeFragment);
        json.addProperty("date", time);
        return json.toString();
    }

    public int getLatestId() {
        return currentId.get();
    }
}
