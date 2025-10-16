package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class MapSchema implements BaseSchema<Map<?, ?>> {

    private boolean required = false;
    private Integer size = null;
    private Map<String, BaseSchema<?>> shapes = null;

    @Override
    public MapSchema required() {
        this.required = true;
        return this;
    }

    @Override
    public boolean isNullAllowed() {
        return !required;
    }

    @Override
    public Map<?, ?> cast(Object value) {
        if (value instanceof Map<?, ?> m) {
            return m;
        } else {
            return null;
        }
    }

    @Override
    public boolean test(Map<?, ?> map) {
        if (size != null && map.size() != size) {
            return false;
        }

        if (shapes != null && !shapes.isEmpty()) {
            for (var entry : shapes.entrySet()) {
                var key = entry.getKey();
                var schema = entry.getValue();
                var value = map.get(key);

                if (!schema.isValid(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public MapSchema sizeof(int n) {
        this.size = n;
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> data) {
        var copy = new HashMap<String, BaseSchema<?>>(data.size());

        for (var e : data.entrySet()) {
            copy.put(e.getKey(), e.getValue());
        }

        this.shapes = copy;
        return this;
    }

}
