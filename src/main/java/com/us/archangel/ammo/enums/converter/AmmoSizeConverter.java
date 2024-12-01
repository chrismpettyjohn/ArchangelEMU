package com.us.archangel.ammo.enums.converter;

import com.us.archangel.ammo.enums.AmmoSize;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AmmoSizeConverter implements AttributeConverter<AmmoSize, String> {
    @Override
    public String convertToDatabaseColumn(AmmoSize attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public AmmoSize convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AmmoSize.fromString(dbData);
    }
}
