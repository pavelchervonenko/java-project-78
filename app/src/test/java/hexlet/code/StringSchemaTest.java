package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import hexlet.code.schemas.StringSchema;

public class StringSchemaTest {

    @Test
    void withoutRequired() {
        StringSchema schema = new StringSchema();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void withRequired() {
        StringSchema schema = new StringSchema();
        schema.required();

        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(" "));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void minLength() {
        StringSchema schema = new StringSchema();
        schema.minLength(5);

        assertFalse(schema.isValid("1234"));
        assertTrue(schema.isValid("12345"));
        assertTrue(schema.isValid("123456"));

        schema.minLength(3);
        assertFalse(schema.isValid("12"));
        assertTrue(schema.isValid("123"));
        assertTrue(schema.isValid("1234"));
    }

    @Test
    void contains() {
        StringSchema schema = new StringSchema();
        schema.contains("hey").contains("bip");

        assertFalse(schema.isValid("hey bbb ccc"));
        assertTrue(schema.isValid("hey bbb bip ccc"));

        schema.contains("wiwi");
        assertFalse(schema.isValid("hey bbb bip ccc"));
        assertTrue(schema.isValid("hey bbb bip ccc wiwi"));
    }

    @Test
    void containsNull() {
        StringSchema schema = new StringSchema();
        schema.contains(null);

        assertTrue(schema.isValid("anything"));
        assertTrue(schema.isValid(""));
    }

    @Test
    void notStringType() {
        StringSchema schema = new StringSchema();

        assertFalse(schema.isValid(123));
        assertFalse(schema.isValid(true));
        assertFalse(schema.isValid(new Object()));
    }

    @Test
    void mixed() {
        StringSchema schema = new StringSchema();
        schema.minLength(10);
        schema.required();
        schema.contains(".com");

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("hex.com"));
        assertFalse(schema.isValid("hexlet online"));
        assertTrue(schema.isValid("hexlet.com"));
    }
}
