package platform;

import entity.CodeResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.CodeSharingService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author namvdo
 */
@SpringBootApplication
@Controller
public class CodeSharingPlatform {
    private final CodeSharingService service = new CodeSharingService();

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(path = "/code/{n}")
    public @ResponseBody
    String responseWithHtml(HttpServletResponse response, @PathVariable String n) {
        response.setContentType("text/html");
        int index = Integer.parseInt(n);
        return service.getCodeAtPos(index);
    }

    @GetMapping(path = "/api/code/{n}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String responseWithJson(@PathVariable String n) {
        int index = Integer.parseInt(n);
        return service.getCodeAtPostJson(index);
    }

    @GetMapping(path = "/code/new")
    public String responseWithForm() {
        return "/post_code";
    }

    @PostMapping(path = "/api/code/new", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String addNewCodeWithJson(@RequestBody CodeResponse body) {
        if (service.addNewCode(body)) {
            return service.responseToAddCode(service.getLatestId() - 1);
        }
        return "";
    }

    @PostMapping(path = "/api/code/new", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody
    String addNewCodeWithForm(CodeResponse newCode) {
        if (service.addNewCode(newCode)) {
            return service.responseToAddCode(service.getLatestId() - 1);
        }
        return "";
    }

    @GetMapping(path = "/code/latest")
    public String getTop10LatestCode(@ModelAttribute("model") ModelMap model) {
        model.put("codes", service.getTop10Latest());
        return "index";
    }

    @GetMapping(path = "/api/code/latest", produces = "application/json")
    public @ResponseBody
    List<CodeResponse> getTop10LatestCodeJson() {
        return service.getTop10Latest();
    }

}
