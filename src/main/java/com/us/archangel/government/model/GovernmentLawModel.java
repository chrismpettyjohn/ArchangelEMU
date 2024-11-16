package com.us.archangel.government.model;

import com.us.archangel.government.enums.LawStatus;
import com.us.archangel.government.enums.LawType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GovernmentLawModel {
    private int id;
    private String displayName;
    private String description;
    private String content;
    private int proposedByUserId;
    private LawStatus status;
    private LawType type;

}