package com.us.archangel.ammo.enums.converter;

import com.us.archangel.ammo.enums.AmmoType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AmmoTypeConverter implements AttributeConverter<AmmoType, String> {
    @Override
    public String convertToDatabaseColumn(AmmoType attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public AmmoType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AmmoType.fromString(dbData);
    }
}
