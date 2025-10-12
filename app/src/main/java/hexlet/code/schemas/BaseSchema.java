package hexlet.code.schemas;

public interface BaseSchema<T> {
    BaseSchema<T> required();

    boolean isNullAllowed();

    T cast(Object value);

    boolean test(T value);

    default boolean isValid(Object value) {
        if (value == null) {
            return isNullAllowed();
        }

        T v = cast(value);
        if (v == null) {
            return false;
        }

        return test(v);
    }

}
