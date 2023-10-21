package homin.chun.contactlist.adapter.in.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InputValidator {
    private final ObjectMapper objectMapper;
    public InputValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public  boolean isValidJson(String json) {
        objectMapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        try {
            objectMapper.readTree(json);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
