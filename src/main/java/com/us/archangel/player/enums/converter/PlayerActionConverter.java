package com.us.archangel.player.enums.converter;

import com.us.archangel.player.enums.PlayerAction;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PlayerActionConverter implements AttributeConverter<PlayerAction, String> {
    @Override
    public String convertToDatabaseColumn(PlayerAction attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public PlayerAction convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PlayerAction.fromString(dbData);
    }
}
