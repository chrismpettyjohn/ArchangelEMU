package com.us.archangel.weapon.enums.converter;

import com.us.archangel.weapon.enums.WeaponEffect;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WeaponEffectConverter implements AttributeConverter<WeaponEffect, String> {
    @Override
    public String convertToDatabaseColumn(WeaponEffect attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public WeaponEffect convertToEntityAttribute(String dbData) {
        return dbData == null ? null : WeaponEffect.fromString(dbData);
    }
}
