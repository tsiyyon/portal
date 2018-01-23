package com.morpheus.portal;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.morpheus.portal.JsonPathAddressable.jpath;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JerseyRequestTest {
    @Test
    public void should_able_to_get_jersey_test() {
        HashMap<String, Object> original = new HashMap<>();
        Request<Map<String, Object>> mapped = new JerseyRequest<>(original);
        mapped.execute(o -> o.operate(t -> t.put("test", "test")));

        assertThat(original.get("test"), is("test"));
    }

    @Test
    public void should_able_to_get_field() {
        HashMap<String, Object> original = new HashMap<>();
        original.put("test", "test");

        Request<Map<String, Object>> mapped = new JerseyRequest<>(original);
        String field = mapped.field(jpath("$.test"));
        assertThat(field, is("test"));
    }
}