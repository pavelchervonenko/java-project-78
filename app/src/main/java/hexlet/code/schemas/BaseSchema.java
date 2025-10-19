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

    /**
     * Marks this schema as required.
     * After calling this method, {@code null} is considered invalid.
     *
     * <p><b>Extension contract:</b></p>
     * <ul>
     *   <li>Subclasses may override this method to add type-specific constraints.</li>
     *   <li>Subclasses must call {@code super.required()} at the beginning of the method.</li>
     *   <li>The method must be idempotent: repeated calls must not change behavior beyond the first call.</li>
     * </ul>
     *
     * @return this schema instance for fluent API chaining
     */
    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }
}
