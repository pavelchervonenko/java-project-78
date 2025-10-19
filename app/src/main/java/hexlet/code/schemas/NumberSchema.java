package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Number> {

    @Override
    public NumberSchema required() {
        super.required();
        return this;
    }

    public NumberSchema positive() {
        Predicate<Number> check = new Predicate<Number>() {
            @Override
            public boolean test(Number n) {
                return n.doubleValue() > 0.0;
            }
        };

        addCheck("positive", check);
        return this;
    }

    public NumberSchema range(int min, int max) {
        Predicate<Number> check = new Predicate<Number>() {
            @Override
            public boolean test(Number n) {
                double v = n.doubleValue();
                return v >= min && v <= max;
            }
        };

        addCheck("range", check);
        return this;
    }
}
