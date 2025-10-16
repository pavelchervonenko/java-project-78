package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;

import java.util.HashMap;
import java.util.Map;


public class MapSchemaNestedTest {

    @Test
    void basicExample() {
        var v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3));
    }

    @Test
    void missingKey() {
        var v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string());
        schemas.put("email", v.string().required());

        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("firstName", "John");
        assertFalse(schema.isValid(human1));

        schemas.put("email", v.string());

        schema.shape(schemas);

        assertTrue(schema.isValid(human1));
    }

    @Test
    void wrongTypeInValue() {
        var v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("age", v.number().positive());

        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("age", 18);
        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("age", "18");
        assertFalse(schema.isValid(human2));
    }

    @Test
    void sizeofCombination() {
        var v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("first", v.string().required());
        schemas.put("second", v.string().required().minLength(2));

        schema.shape(schemas).sizeof(2);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("first", "A");
        human1.put("second", "BC");
        assertTrue(schema.isValid(human1));

        human1.put("extra", 1);
        assertFalse(schema.isValid(human1));

        human1.remove("extra");
        human1.put("last", "B");
        assertFalse(schema.isValid(human1));
    }

    @Test
    void override() {
        var v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas1 = new HashMap<>();
        schemas1.put("first", v.string().required());

        Map<String, BaseSchema<?>> schemas2 = new HashMap<>();
        schemas2.put("first", v.string().required().minLength(3));

        schema.shape(schemas1);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("first", "ab");
        assertTrue(schema.isValid(human1));

        schema.shape(schemas2);
        assertFalse(schema.isValid(human1));

        human1.put("first", "abc");
        assertTrue(schema.isValid(human1));
    }

    @Test
    void notMap() {
        var v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("first", v.string().required());

        schema.shape(schemas);

        assertFalse(schema.isValid(123));
        assertFalse(schema.isValid("not a map"));
        assertFalse(schema.isValid(new Object()));
    }
}
