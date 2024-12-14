package com.us.roleplay.users.models;

import com.us.roleplay.users.enums.StoreProductEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StoreProduct {
    private final int id;
    private final StoreProductEnum type;
}
