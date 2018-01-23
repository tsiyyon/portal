package me.tzion.portal.addressables;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import me.tzion.portal.core.Request;

import static com.jayway.jsonpath.Configuration.defaultConfiguration;
import static com.jayway.jsonpath.JsonPath.using;

public class JsonPathAddressable<T> implements Request.Addressable<T> {
    private String expression;

    private JsonPathAddressable(String expression) {
        this.expression = expression;
    }

    public static <T> Request.Addressable<T> jpath(String path) {
        return new JsonPathAddressable<>(path);
    }

    @Override
    public <R> R locate(Object context) {
        Configuration config = defaultConfiguration()
                .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
                .addOptions(Option.SUPPRESS_EXCEPTIONS);

        return using(config).parse(context).read(expression);
    }
}
