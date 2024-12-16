package com.us.archangel.store.entity;

import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.store.enums.StoreProductOfferStatus;
import com.us.archangel.store.enums.StoreProductType;
import com.us.archangel.store.enums.converter.StoreProductOfferStatusConverter;
import com.us.archangel.store.enums.converter.StoreProductTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_store_product_offers", indexes = {
        @Index(name = "idx_recipient_players_id", columnList = "recipient_players_id"),
        @Index(name = "idx_employee_players_id", columnList = "employee_players_id"),
        @Index(name = "idx_store_product_id", columnList = "store_product_id")
})
public class StoreProductOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "recipient_players_id", nullable = false)
    private int recipientUserId;

    @Column(name = "employee_players_id", nullable = false)
    private int employeeUserId;

    @Column(name = "store_product_id", nullable = false)
    private int productId;

    @Column(name = "store_product_cost", nullable = false)
    private int productCost;

    @Convert(converter = StoreProductTypeConverter.class)
    @Column(name = "store_product_type", nullable = false)
    private StoreProductType productType;

    @Convert(converter = StoreProductOfferStatusConverter.class)
    @Column(name = "offer_status", nullable = false)
    private StoreProductOfferStatus offerStatus;
}
