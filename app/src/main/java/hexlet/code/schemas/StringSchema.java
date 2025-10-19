package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        super.required();

        Predicate<String> check = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s != null && !s.isEmpty();
            }
        };

        addCheck("required:notEmpty", check);
        return this;
    }

    public StringSchema minLength(int n) {
        Predicate<String> check = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                if (s == null || s.isEmpty()) {
                    return !required;
                }
                return s.length() >= n;
            }
        };

        addCheck("minLength", check);
        return this;
    }

    public StringSchema contains(String text) {
        if (text == null) {
            return this;
        }

        Predicate<String> check = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                if (s == null || s.isEmpty()) {
                    return !required;
                }
                return s.contains(text);
            }
        };

        addCheck("contains:" + text, check);
        return this;
    }
}
