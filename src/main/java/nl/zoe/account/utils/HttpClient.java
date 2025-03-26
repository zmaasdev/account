package nl.zoe.account.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class HttpClient {

    public static String asJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}
