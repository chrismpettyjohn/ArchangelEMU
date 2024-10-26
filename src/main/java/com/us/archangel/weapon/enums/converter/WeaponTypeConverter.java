package com.us.archangel.weapon.enums.converter;

import com.us.archangel.weapon.enums.WeaponType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WeaponTypeConverter implements AttributeConverter<WeaponType, String> {
    @Override
    public String convertToDatabaseColumn(WeaponType attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public WeaponType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : WeaponType.fromString(dbData);
    }
}
