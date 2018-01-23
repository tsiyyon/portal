package me.tzion.portal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.tzion.portal.core.Request;
import me.tzion.portal.requests.JerseyRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.tzion.portal.RequestBodyReader.Json.fromJson;

@Provider
public class RequestBodyReader implements MessageBodyReader<Request> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Request.class.isAssignableFrom(type);
    }

    @Override
    public Request readFrom(Class<Request> type,
                            Type genericType,
                            Annotation[] annotations,
                            MediaType mediaType,
                            MultivaluedMap<String, String> httpHeaders,
                            InputStream entityStream) throws WebApplicationException {

        ParameterizedType pt = (ParameterizedType) genericType;
        Type actualType = pt.getActualTypeArguments()[0];
        Class<?> entityClass = actualType instanceof Class ? ((Class) actualType) : (Class) ((ParameterizedType) actualType).getRawType();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(entityStream));
        String collect = buffer.lines().collect(Collectors.joining());
        if (entityClass.isAssignableFrom(Map.class)) {
            return new JerseyRequest<Map>(fromJson(collect, new TypeReference<Map<String, Object>>() {
            }));
        } else if (entityClass.isAssignableFrom(List.class)) {
            return new JerseyRequest<List>(fromJson(collect, new TypeReference<List<Object>>() {
            }));
        }

        return Request.EMPTY;
    }

    public static class Json {
        static final ObjectMapper mapper = new ObjectMapper();
        static <T> T fromJson(String blob, TypeReference<T> type) {
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
}
