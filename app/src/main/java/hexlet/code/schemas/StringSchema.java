package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema {
    private boolean required = false;
    private Integer minLength = null;
    private final List<String> textContains = new ArrayList<>();

    public StringSchema required() {
        this.required = true;
        return this;
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

    public boolean isValid(Object value) {
        if (value == null) {
            return !required;
        }

        String s;
        if (value instanceof String) {
            s = (String) value;
        } else {
            return false;
        }

        if (s.isEmpty()) {
            return !required;
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
}
