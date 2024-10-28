package com.us.archangel.player.entity;

import com.us.archangel.corp.entity.CorpEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_players_banks")
public class PlayerBankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @Column(name = "corps_id", nullable = false)
    private int corpId;;

    @Column(name = "account_balance", nullable = false)
    private int accountBalance;

    @Column(name = "created_at", nullable = false)
    private int createdAt;

    @Column(name = "updated_at", nullable = false)
    private int updatedAt;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false, insertable=false, updatable=false)
    private PlayerEntity player;

    @ManyToOne
    @JoinColumn(name = "corps_id", nullable = false, insertable=false, updatable=false)
    private CorpEntity corp;


}
