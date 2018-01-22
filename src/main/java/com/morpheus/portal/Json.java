package com.morpheus.portal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class Json {
    public static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(Blob blob, TypeReference<T> type) {
        try {
            return mapper.readValue(blob.getBinaryStream(), type);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String blob, TypeReference<T> type) {
        try {
            return mapper.readValue(blob, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static {
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
                WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
