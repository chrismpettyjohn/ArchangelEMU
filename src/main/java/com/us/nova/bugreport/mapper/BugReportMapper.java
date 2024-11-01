package com.us.nova.bugreport.mapper;

import com.us.nova.bugreport.entity.BugReportEntity;
import com.us.nova.bugreport.model.BugReportModel;

public class BugReportMapper {

    public static BugReportModel toModel(BugReportEntity entity) {
        if (entity == null) {
            return null;
        }
        return new BugReportModel(
                entity.getId(),
                entity.getCreatedByUserId(),
                entity.getCreatedAt(),
                entity.getDisplayName(),
                entity.getContent(),
                entity.getClosedAt(),
                entity.getClosedByUserId()
        );
    }

    public static BugReportEntity toEntity(BugReportModel model) {
        if (model == null) {
            return null;
        }
        BugReportEntity entity = new BugReportEntity();
        entity.setId(model.getId());
        entity.setCreatedByUserId(model.getCreatedByUserId());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setDisplayName(model.getDisplayName());
        entity.setContent(model.getContent());
        entity.setClosedAt(model.getClosedAt());
        entity.setClosedByUserId(model.getClosedByUserId());
        return entity;
    }
}