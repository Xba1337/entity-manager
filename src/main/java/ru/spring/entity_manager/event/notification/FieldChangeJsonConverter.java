package ru.spring.entity_manager.event.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.util.HashMap;
import java.util.Map;

public class FieldChangeJsonConverter
        implements AttributeConverter<Map<String, FieldChange<?>>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(Map<String, FieldChange<?>> attribute) {
        if (attribute == null) return null;
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Json serialize error", e);
        }
    }

    @Override
    public Map<String, FieldChange<?>> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            JavaType mapType = mapper.getTypeFactory()
                    .constructMapType(
                            HashMap.class,
                            mapper.constructType(String.class),
                            mapper.getTypeFactory()
                                    .constructParametricType(FieldChange.class, Object.class)
                    );

            return mapper.readValue(dbData, mapType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Json deserialize error", e);
        }
    }
}
