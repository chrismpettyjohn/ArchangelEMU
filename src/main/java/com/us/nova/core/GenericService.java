package com.us.nova.core;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenericService<Model, Context extends GenericContext<Model>, Repository> {

    protected final Context context;
    protected final Repository repository;
    protected final Class<?> mapperClass;

    public GenericService(Context context, Repository repository, Class<?> mapperClass) {
        this.context = context;
        this.repository = repository;
        this.mapperClass = mapperClass;
        this.loadAll(); // preload all items into the context
    }

    public void create(Model model) {
        Object entity = invokeMapperMethod("toEntity", model);
        context.add(getId(model), model);
        invokeRepositoryMethod("create", entity);
    }

    public void update(int id, Model updatedModel) {
        Object updatedEntity = invokeMapperMethod("toEntity", updatedModel);
        context.update(id, updatedModel);
        invokeRepositoryMethod("updateById", id, updatedEntity);
    }

    public Model getById(int id) {
        Model model = context.get(id);
        if (model != null) {
            return model;
        }
        Object entity = invokeRepositoryMethod("getById", id);
        if (entity != null) {
            model = invokeMapperMethod("toModel", entity);
            context.add(id, model);
        }
        return model;
    }

    public List<Model> getAll() {
        Map<Integer, Model> models = context.getAll();
        if (!models.isEmpty()) {
            return List.copyOf(models.values());
        }
        List<?> entities = (List<?>) invokeRepositoryMethod("getAll");
        List<Model> modelList = entities.stream()
                .map(entity -> invokeMapperMethod("toModel", entity))
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(getId(model), model));
        return modelList;
    }

    public void deleteById(int id) {
        context.delete(id);
        invokeRepositoryMethod("deleteById", id);
    }

    private Object invokeRepositoryMethod(String methodName, Object... args) {
        try {
            Class<?>[] argTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                argTypes[i] = args[i].getClass();
            }
            return repository.getClass().getMethod(methodName, argTypes).invoke(repository, args);
        } catch (Exception e) {
            throw new RuntimeException("Repository method invocation failed", e);
        }
    }

    private int getId(Object obj) {
        try {
            Method getIdMethod = obj.getClass().getMethod("getId");
            return (int) getIdMethod.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ID via getId()", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Model invokeMapperMethod(String methodName, Object param) {
        try {
            Method method = mapperClass.getMethod(methodName, param.getClass());
            return (Model) method.invoke(null, param);
        } catch (Exception e) {
            throw new RuntimeException("Mapper method invocation failed", e);
        }
    }

    private void loadAll() {
        this.getAll();
    }
}
