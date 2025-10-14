package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import hexlet.code.schemas.MapSchema;

import java.util.HashMap;

public class MapSchemaTest {

    @Test
    void withoutRequired() {
        MapSchema schema = new MapSchema();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    void withRequired() {
        MapSchema schema = new MapSchema();
        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    void wrongType() {
        MapSchema schema = new MapSchema();

        assertFalse(schema.isValid(123));
        assertFalse(schema.isValid("priv"));
        assertFalse(schema.isValid(new Object()));

        schema.required();

        assertFalse(schema.isValid(123));
    }

    @Test
    void size() {
        MapSchema schema = new MapSchema();
        var data = new HashMap<String, String>();

        schema.sizeof(0);

        assertTrue(schema.isValid(data));

        data.put("key1", "value1");

        assertFalse(schema.isValid(data));

        schema.sizeof(2);

        assertFalse(schema.isValid(data));

        data.put("key2", "value2");

        assertTrue(schema.isValid(data));
    }

    @Test
    void sizeOverride() {
        MapSchema schema = new MapSchema();
        var data = new HashMap<String, String>();
        schema.sizeof(1);
        schema.sizeof(3);

        data.put("key1", "value1");
        data.put("key2", "value2");

        assertFalse(schema.isValid(data));

        data.put("key3", "value3");

        assertTrue(schema.isValid(data));
    }

    @Test
    void mixed() {
        MapSchema schema = new MapSchema();
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        schema.sizeof(1);

        assertTrue(schema.isValid(data));
        assertTrue(schema.isValid(null));

        schema.sizeof(2);
        schema.required();

        assertFalse(schema.isValid(data));
        assertFalse(schema.isValid(null));

        data.put("key2", "value2");

        assertTrue(schema.isValid(data));
    }
}
