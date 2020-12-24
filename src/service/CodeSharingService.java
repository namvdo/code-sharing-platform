package service;

import com.google.gson.JsonObject;
import entity.CodeResponse;

import java.util.List;

/**
 * The interface Code sharing service.
 */
interface CodeSharingService {
    /**
     * Gets top 10 latest.
     *
     * @return the top 10 latest
     */
    List<CodeResponse> getTop10Latest();

    /**
     * Add new code boolean.
     *
     * @param code the code
     * @return the boolean
     */
    boolean addNewCode(CodeResponse code);

    /**
     * Response to add code string.
     *
     * @param id the id
     * @return the string
     */
    JsonObject responseToAddCode(int id);


    /**
     * Gets code at post json.
     *
     * @param pos the pos
     * @return the code at post json
     */
    JsonObject getCodeAtPostJson(int pos);

    /**
     * Gets latest id.
     *
     * @return the latest id
     */
    int getLatestId();
}
