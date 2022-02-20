package hack.badgemeal.apis.domain.draw.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmptyStringToNullConverter implements AttributeConverter<Character, Character> {
    @Override
    public Character convertToDatabaseColumn(Character attribute) {
        return attribute == ' ' ? null : attribute;
    }

    @Override
    public Character convertToEntityAttribute(Character dbData) {
        return dbData;
    }
}
