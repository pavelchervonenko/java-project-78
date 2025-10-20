package hexlet.code.schemas;

import java.util.Map;

import java.util.function.Predicate;


public final class MapSchema extends BaseSchema<Map<?, ?>> {

    private Map<String, BaseSchema<?>> shapes = null;

    public MapSchema required() {
        this.required = true;
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
        Predicate<Map<?, ?>> check = new Predicate<Map<?, ?>>() {
            @Override
            public boolean test(Map<?, ?> m) {
                if (data == null || data.isEmpty()) {
                    return true;
                }
                for (var e : data.entrySet()) {
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
