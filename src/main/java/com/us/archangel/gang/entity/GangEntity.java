package com.us.archangel.gang.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_gangs", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_room_id", columnList = "room_id"),
        @Index(name = "idx_display_name", columnList = "display_name")
})
public class GangEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "badge", nullable = false)
    private String badge;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "room_id", nullable = false)
    private int roomId;
}
