package service;

import com.google.gson.JsonObject;
import dao.CodeResponseRepository;
import entity.CodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author namvdo
 */
@Service
public class CodeSharingServiceImpl implements CodeSharingService {
    @Autowired
    private CodeResponseRepository repo;


    @Override
    public List<CodeResponse> getTop10Latest() {
        List<CodeResponse> top10Latest = getLast10Ids();
        return Util.sortDateDescending(top10Latest);
    }

    @Override
    public boolean addNewCode(CodeResponse newCode) {
        int id = repo.count() == 0 ? 0 : (int) repo.count();
        String code = newCode.getCode() == null ? "" : newCode.getCode();
        int seconds = newCode.getTime();
        LocalDateTime date = newCode.getDate() == null ? LocalDateTime.now() : LocalDateTime.parse(newCode.getDate());
        int views = newCode.getViews();
        boolean isRestricted = false;
        if (seconds > 0 || views > 0) {
            isRestricted = true;
        }
        CodeResponse codeObj = new CodeResponse.CodeBuilder(code).id(id).view(views)
                .timeInSecond(seconds).codePublic(isRestricted).dateTime(date).createdTime(System.currentTimeMillis() / 1000).build();
        repo.save(codeObj);
        return true;
    }

    @Override
    public JsonObject responseToAddCode(int id) {
        JsonObject json = new JsonObject();
        String randomUUID = Util.getRandomUUID();
        json.addProperty("id", randomUUID);
        return json;
    }


    @Override
    public JsonObject getCodeAtPostJson(int pos) {
        Optional<CodeResponse> record = repo.findById(pos);
        repo.findAll().iterator().forEachRemaining(System.out::println);
        JsonObject json = new JsonObject();
        if (record.isPresent()) {
            CodeResponse code = record.get();
            if (!code.isCodePublic()) {

                long currentTimeInSec = System.currentTimeMillis() / 1000;
                int limitedTime = code.getTime();
                int view = code.getViews();
                if (currentTimeInSec > code.getCreatedTime() + limitedTime || view == 0) {
                    json.addProperty("code-status", 404);
                } else {
                    json.addProperty("code", code.getCode());
                    json.addProperty("date", code.getDate());
                    json.addProperty("time", code.getTime());
                    json.addProperty("views", code.getViews());
                    updateLeftViewTime(code);
                }
            } else {
                json.addProperty("code", code.getCode());
                json.addProperty("date", code.getDate());
                json.addProperty("time", code.getTime());
                json.addProperty("views", code.getViews());
            }
            return json;


        }
        json.addProperty(Util.CODE_STATUS, Util.NOT_FOUND);
        return json;
    }

    @Override
    public synchronized int getLatestId() {
        return (int) repo.count();
    }

    private List<CodeResponse> getLast10Ids() {
        List<CodeResponse> top10Latest = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(top10Latest::add);
        System.out.println(repo.count());
        var codePublic = top10Latest.subList(Math.max(0, (int) repo.count() - 10), (int) repo.count());
        System.out.println("code: " + codePublic.toString());
        codePublic.removeIf(code -> code.getViews() > 0 || code.getTime() > 0);
        return codePublic;
    }

    private synchronized void updateLeftViewTime(CodeResponse code) {
        int elapsedTime = (int) ((int) (System.currentTimeMillis() / 1000) - code.getCreatedTime());
        code.setViews(code.getViews() - 1);
        code.setTime(code.getTime() - elapsedTime);
    }

}
