package hexlet.code.schemas;

import java.util.Map;

public class MapSchema implements BaseSchema<Map<?, ?>> {

    private boolean required = false;
    private Integer size = null;

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
        return true;
    }

    public MapSchema sizeof(int n) {
        this.size = n;
        return this;
    }
}
