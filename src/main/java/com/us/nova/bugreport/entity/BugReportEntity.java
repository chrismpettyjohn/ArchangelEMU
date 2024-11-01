package com.us.nova.bugreport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nova_bug_reports")
public class BugReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "created_by_users_id", nullable = false)
    private int createdByUserId;

    @Column(name = "created_at", nullable = false)
    private int createdAt;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "closed_at")
    private Integer closedAt;

    @Column(name = "closed_by_user_id")
    private Integer closedByUserId;


}
