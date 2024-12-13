package com.us.archangel.player.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class PlayerKillHistoryModel {
    private int id;
    private int attackerUserId;
    private int victimUserId;
    private int attackerWeaponId;
    private LocalDateTime createdAt;

    public PlayerKillHistoryModel() {
    }
}
