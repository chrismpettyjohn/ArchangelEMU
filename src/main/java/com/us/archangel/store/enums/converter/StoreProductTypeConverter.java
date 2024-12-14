package com.us.archangel.store.enums.converter;

import com.us.archangel.store.enums.StoreProductType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StoreProductTypeConverter implements AttributeConverter<StoreProductType, String> {
    @Override
    public String convertToDatabaseColumn(StoreProductType attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public StoreProductType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : StoreProductType.fromString(dbData);
    }
}
