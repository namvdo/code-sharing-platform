package platform;

import com.google.gson.JsonObject;
import entity.CodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.CodeSharingServiceImpl;
import util.Util;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The type Code sharing platform.
 *
 * @author namvdo
 */
@SpringBootApplication
@Controller
@ComponentScan("service") //to scan packages mentioned
@ComponentScan("dao")
@EnableJpaRepositories("dao")
@EntityScan("entity")
public class CodeSharingPlatform {
    @Autowired
    private CodeSharingServiceImpl service;

    private ConcurrentMap<String, Integer> uuidToId = new ConcurrentHashMap<>(5);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    /**
     * Response with html string.
     *
     * @param response the response
     * @param n        the n
     * @return the string
     */
    @GetMapping(path = "/code/{n}")
    public ModelAndView responseWithHtml(HttpServletResponse response, @PathVariable String n) {
        response.setContentType("text/html");
        int index = uuidToId.get(n);
        ModelAndView mav = new ModelAndView();
        JsonObject json = service.getCodeAtPostJson(index);
        if (json.get(Util.CODE_STATUS).getAsInt() != Util.NOT_FOUND) {
            mav.addObject("code", json.get("code").getAsString());
            mav.addObject("date", json.get("date").getAsString());
            mav.addObject("time", json.get("time").getAsString());
            mav.addObject("views", json.get("views").getAsString());
        } else {
            mav.addObject(Util.CODE_STATUS, Util.NOT_FOUND);
        }
        mav.setViewName("get_code");
        return mav;
    }

    /**
     * Response with json string.
     *
     * @param n the n
     * @return the string
     */
    @GetMapping(path = "/api/code/{n}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String responseWithJson(@PathVariable String n) {
        int index = uuidToId.get(n);
        return service.getCodeAtPostJson(index).toString();
    }

    /**
     * Response with form string.
     *
     * @return the string
     */
    @GetMapping(path = "/code/new")
    public String responseWithForm() {
        return "/post_code";
    }

    /**
     * Add new code with json string.
     *
     * @param body the body
     * @return the string
     */
    @PostMapping(path = "/api/code/new", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addNewCodeWithJson(@RequestBody CodeResponse body) {
        return addCodeResponse(body);
    }

    /**
     * Add new code with form string.
     *
     * @param newCode the new code
     * @return the string
     */
    @PostMapping(path = "/api/code/new", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody String addNewCodeWithForm(CodeResponse newCode) {
        return addCodeResponse(newCode);
    }

    private String addCodeResponse(CodeResponse newCode) {
        if (service.addNewCode(newCode)) {
            int id = service.getLatestId();
            JsonObject response = service.responseToAddCode(id);
            String uuid = response.get("id").getAsString();
            uuidToId.put(uuid, id);
            return response.toString();
        }
        return "";
    }

    /**
     * Gets top 10 latest code.
     *
     * @param model the model
     * @return the top 10 latest code
     */
    @GetMapping(path = "/code/latest")
    public String getTop10LatestCode(@ModelAttribute("model") ModelMap model) {
        model.put("codes", service.getTop10Latest());
        return "index";
    }

    /**
     * Gets top 10 latest code json.
     *
     * @return the top 10 latest code json
     */
    @GetMapping(path = "/api/code/latest", produces = "application/json")
    public @ResponseBody
    List<CodeResponse> getTop10LatestCodeJson() {
        return service.getTop10Latest();
    }

}
