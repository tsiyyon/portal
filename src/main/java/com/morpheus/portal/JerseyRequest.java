package com.morpheus.portal;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

public class JerseyRequest<T> implements Request<T> {
    @Context
    private UriInfo uriInfo;
    T raw;

    JerseyRequest(T raw) {
        this.raw = raw;
    }

    @Override
    public T get() {
        return raw;
    }

    @Override
    public <V> V field(String filter) {
        if (Map.class.isAssignableFrom(raw.getClass())) {
            return (V) ((Map) raw).get("notes");
        }
        return null;
    }

    @Override
    public void execute(Request.Operation<T> operation) {
        operation.execute(consumer -> consumer.accept(JerseyRequest.this.raw));
    }


}
