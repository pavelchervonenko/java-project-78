package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import hexlet.code.schemas.NumberSchema;

public class NumberSchemaTest {

    @Test
    void withoutRequired() {
        NumberSchema schema = new NumberSchema();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(5L));
        assertTrue(schema.isValid(3.14));
        schema.positive();
        assertTrue(schema.isValid(null));
    }

    @Test
    void withRequired() {
        NumberSchema schema = new NumberSchema();
        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(10));
        assertTrue(schema.isValid(0));
    }

    @Test
    void positive() {
        NumberSchema schema = new NumberSchema();
        schema.positive();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(1));
        assertTrue(schema.isValid(0.0001));
        assertFalse(schema.isValid(-1));
        assertFalse(schema.isValid(-0.0001));
        assertFalse(schema.isValid(0));
    }

    @Test
    void range() {
        NumberSchema schema = new NumberSchema();
        schema.range(5, 10);

        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertTrue(schema.isValid(7));
        assertTrue(schema.isValid(5.0));
        assertTrue(schema.isValid(10.0));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(10.00001));
        assertFalse(schema.isValid(11));
    }

    @Test
    void rangeOverride() {
        NumberSchema schema = new NumberSchema();
        schema.range(0, 100);

        assertTrue(schema.isValid(5));

        schema.range(6, 10);

        assertFalse(schema.isValid(5));
        assertFalse(schema.isValid(55));
        assertTrue(schema.isValid(7));
    }

    @Test
    void mixed() {
        NumberSchema schema = new NumberSchema();
        schema.required();
        schema.positive();
        schema.range(5, 10);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-3));
        assertFalse(schema.isValid(-7));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));
    }
}
