### Hexlet tests and linter status:
[![Actions Status](https://github.com/pavelchervonenko/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/pavelchervonenko/java-project-78/actions) [![Java CI](https://github.com/pavelchervonenko/java-project-78/actions/workflows/main.yml/badge.svg)](https://github.com/pavelchervonenko/java-project-78/actions/workflows/main.yml) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=pavelchervonenko_java-project-78&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=pavelchervonenko_java-project-78)

## Описание
Валидатор данных – библиотека, с помощью которой можно проверять корректность любых данных.
- За основу для проекта взята библиотека [yup.](https://github.com/jquense/yup).

## Пример использования
```Java
import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;

Validator v = new Validator();

// Строки
StringSchema schema = v.string().required();

schema.isValid("what does the fox say"); // true
schema.isValid(""); // false

// Числа
NumberSchema schema = v.number().required().positive();

schema.isValid(-10); // false
schema.isValid(10); // true

// Объект Map с поддержкой проверки структуры
Map<String, BaseSchema<String>> schemas = new HashMap<>();
schemas.put("firstName", v.string().required());
schemas.put("lastName", v.string().required().minLength(2));

MapSchema schema = v.map().sizeof(2).shape(schemas);

Map<String, Object> human1 = new HashMap<>();
human1.put("firstName", "John");
human1.put("lastName", "Smith");
schema.isValid(human1); // true

Map<String, Object> human2 = new HashMap<>();
human2.put("firstName", "Anna");
human2.put("lastName", "B");
schema.isValid(human2); // false
```
