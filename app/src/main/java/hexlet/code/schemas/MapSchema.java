package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

import java.util.function.Predicate;


public final class MapSchema extends BaseSchema<Map<?, ?>> {

    private Map<String, BaseSchema<?>> shapes = null;

    @Override
    public MapSchema required() {
        super.required();
        return this;
    }

    public MapSchema sizeof(int n) {
        Predicate<Map<?, ?>> check = new Predicate<Map<?, ?>>() {
            @Override
            public boolean test(Map<?, ?> m) {
                return m.size() == n;
            }
        };
        addCheck("sizeof", check);
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> data) {
        var copy = new HashMap<String, BaseSchema<?>>(data.size());
        for (var e : data.entrySet()) {
            copy.put(e.getKey(), e.getValue());
        }
        this.shapes = copy;

        Predicate<Map<?, ?>> check = new Predicate<Map<?, ?>>() {
            @Override
            public boolean test(Map<?, ?> m) {
                if (shapes == null || shapes.isEmpty()) {
                    return true;
                }
                for (var e : shapes.entrySet()) {
                    var key = e.getKey();
                    var schema = e.getValue();
                    var value = m.get(key);
                    if (!validateBy(schema, value)) {
                        return false;
                    }
                }
                return true;
            }
        };

        addCheck("shape", check);
        return this;
    }

    private static <T> boolean validateBy(BaseSchema<T> schema, Object value) {
        T v = (T) value;
        return schema.isValid(v);
    }
}
