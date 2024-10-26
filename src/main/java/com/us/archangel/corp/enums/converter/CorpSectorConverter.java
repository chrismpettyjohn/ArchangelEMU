package com.us.archangel.corp.enums.converter;

import com.us.archangel.corp.enums.CorpSector;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CorpSectorConverter implements AttributeConverter<CorpSector, String> {
    @Override
    public String convertToDatabaseColumn(CorpSector attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public CorpSector convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CorpSector.fromString(dbData);
    }
}
