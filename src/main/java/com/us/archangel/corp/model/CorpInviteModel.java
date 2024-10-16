package com.us.archangel.corp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CorpInviteModel {

    private int id;
    private int corpId;
    private int corpRoleId;
    private int userId;
}
