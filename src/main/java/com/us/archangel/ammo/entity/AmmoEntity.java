package com.us.archangel.ammo.entity;

import com.us.archangel.ammo.enums.AmmoSize;
import com.us.archangel.ammo.enums.AmmoType;
import com.us.archangel.ammo.enums.converter.AmmoSizeConverter;
import com.us.archangel.ammo.enums.converter.AmmoTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_ammo")
public class AmmoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "unique_name", nullable = false)
    private String uniqueName;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "size")
    @Convert(converter = AmmoSizeConverter.class)
    private AmmoSize size;

    @Column(name = "type")
    @Convert(converter = AmmoTypeConverter.class)
    private AmmoType type;

    @Column(name = "value")
    private int value;

}
