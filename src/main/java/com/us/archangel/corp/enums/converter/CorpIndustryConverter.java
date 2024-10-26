package com.us.archangel.corp.enums.converter;

import com.us.archangel.corp.enums.CorpIndustry;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CorpIndustryConverter implements AttributeConverter<CorpIndustry, String> {
    @Override
    public String convertToDatabaseColumn(CorpIndustry attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public CorpIndustry convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CorpIndustry.fromString(dbData);
    }
}
