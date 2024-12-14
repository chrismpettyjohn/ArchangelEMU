package com.us.archangel.store.enums.converter;

import com.us.archangel.store.enums.StoreProductOfferStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StoreProductOfferStatusConverter implements AttributeConverter<StoreProductOfferStatus, String> {
    @Override
    public String convertToDatabaseColumn(StoreProductOfferStatus attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public StoreProductOfferStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : StoreProductOfferStatus.fromString(dbData);
    }
}
