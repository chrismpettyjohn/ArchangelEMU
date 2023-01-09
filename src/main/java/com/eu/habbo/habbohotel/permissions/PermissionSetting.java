package com.eu.habbo.habbohotel.permissions;

public enum PermissionSetting {
    DISALLOWED,
    ALLOWED,
    ROOM_OWNER;

    public static PermissionSetting fromString(String value) {
        return switch (value) {
            case "1" -> ALLOWED;
            case "2" -> ROOM_OWNER;
            default -> DISALLOWED;
        };
    }
}
