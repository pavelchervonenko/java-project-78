package hexlet.code.schemas;

public class NumberSchema implements BaseSchema<Number> {
    private boolean required = false;
    private boolean positive = false;
    private Integer min = null;
    private Integer max = null;

    @Override
    public NumberSchema required() {
        this.required = true;
        return this;
    }

    @Override
    public boolean isNullAllowed() {
        return !required;
    }

    @Override
    public Number cast(Object value) {
        if (value instanceof Number n) {
            return n;
        } else {
            return null;
        }
    }

    @Override
    public boolean test(Number n) {
        double v = n.doubleValue();

        if (positive && v <= 0.0) {
            return false;
        }

        if (min != null && v < min) {
            return false;
        }

        if (max != null && v > max) {
            return false;
        }

        return true;
    }

    public NumberSchema positive() {
        this.positive = true;
        return this;
    }

    public NumberSchema range(int minValue, int maxValue) {
        this.min = minValue;
        this.max = maxValue;
        return this;
    }
}
