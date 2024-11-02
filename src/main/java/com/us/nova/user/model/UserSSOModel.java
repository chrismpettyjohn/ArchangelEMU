package com.us.nova.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSSOModel {

    private int id;
    private int userId;
    private String ssoToken;
    private int expiresAt;
    private Integer activatedAt;
    private String ipAddress;

    public UserSSOModel() {
    }

}