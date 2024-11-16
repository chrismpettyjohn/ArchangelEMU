package com.us.nova.user.model;

import com.us.nova.user.service.UserGuestbookService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserGuestbookModel {
    private int id;
    private int postedOnUsersId;
    private int postedByUsersId;
    private String message;
    private int createdAt;
    private int updatedAt;

    public void update() {
        UserGuestbookService.getInstance().update(this.id, this);
    }
}