package com.us.archangel.player.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PlayerKillHistoryModel {
    private int id;
    private int attackerUserId;
    private int victimUserId;
    private LocalDateTime createdAt;

    public PlayerKillHistoryModel() {
    }
}
