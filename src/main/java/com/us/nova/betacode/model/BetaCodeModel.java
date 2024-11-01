package com.us.nova.betacode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BetaCodeModel {

    private int id;
    private String code;
    private Integer userId;
    private Integer claimedAt;
    private int createdAt;

}