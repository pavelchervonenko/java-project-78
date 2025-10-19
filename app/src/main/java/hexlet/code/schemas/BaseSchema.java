package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> checks = new LinkedHashMap<>();
    protected boolean required = false;

    protected final void addCheck(String name, Predicate<T> validate) {
        checks.put(name, validate);
    }

    public final boolean isValid(T value) {
        if (value == null) {
            return !required;
        }
        for (var p: checks.values()) {
            if (!p.test(value)) {
                return false;
            }
        }
        return true;
    }

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }
}
