package me.tzion.portal.requests;

import me.tzion.portal.core.Request;

import static com.jayway.jsonpath.JsonPath.using;

public class JerseyRequest<T> implements Request<T> {
    private T raw;

    public JerseyRequest(T raw) {
        this.raw = raw;
    }

    @Override
    public T get() {
        return raw;
    }

    @Override
    public <V> V field(Addressable<T> filter) {
        return filter.locate(raw);
    }

    @Override
    public void execute(Request.Operation<T> operation) {
        operation.execute(consumer -> consumer.accept(JerseyRequest.this.raw));
    }
}
