package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema implements BaseSchema<String> {

    private boolean required = false;
    private Integer minLength = null;
    private final List<String> textContains = new ArrayList<>();

    @Override
    public StringSchema required() {
        this.required = true;
        return this;
    }

    @Override
    public boolean isNullAllowed() {
        return !required;
    }

    @Override
    public String cast(Object value) {
        if (value instanceof String s) {
            return s;
        } else {
            return null;
        }
    }

    @Override
    public boolean test(String s) {
        if (s.isEmpty()) {
            return isNullAllowed();
        }

        if (minLength != null && s.length() < minLength) {
            return false;
        }

        for (String sub : textContains) {
            if (!s.contains(sub)) {
                return false;
            }
        }

        return true;
    }

    public StringSchema minLength(int lenght) {
        this.minLength = lenght;
        return this;
    }

    public StringSchema contains(String text) {
        if (text != null) {
            this.textContains.add(text);
        }
        return this;
    }
}
