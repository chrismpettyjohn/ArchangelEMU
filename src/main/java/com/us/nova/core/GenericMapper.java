package com.us.nova.core;

public abstract class GenericMapper<Entity, Model> {

    public static <Entity, Model> Model toModel(Entity entity) {
        throw new UnsupportedOperationException("toModel method not implemented");
    }

    public static <Entity, Model> Entity toEntity(Model model) {
        throw new UnsupportedOperationException("toEntity method not implemented");
    }
}
