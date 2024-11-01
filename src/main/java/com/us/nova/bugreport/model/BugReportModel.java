package com.us.nova.bugreport.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BugReportModel {

    private int id;
    private int createdByUserId;
    private int createdAt;
    private String displayName;
    private String content;
    private Integer closedAt;
    private Integer closedByUserId;

}