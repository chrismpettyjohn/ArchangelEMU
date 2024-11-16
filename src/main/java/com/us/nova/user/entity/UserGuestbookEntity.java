package com.us.nova.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nova_users_guestbook")
public class UserGuestbookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "posted_on_users_id", nullable = false)
    private int postedOnUsersId;

    @Column(name = "posted_by_users_id", nullable = false)
    private int postedByUsersId;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private int createdAt;

    @Column(name = "updated_at", nullable = false)
    private int updatedAt;

}
