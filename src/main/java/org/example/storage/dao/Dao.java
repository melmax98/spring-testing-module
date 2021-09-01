package org.example.storage.dao;

import org.example.model.Entity;

import java.util.Map;

public interface Dao {
    Entity save(Entity entity);
    Entity update(Entity entity);
    boolean delete (long entityId);
    Map<String, Entity> getStorage();
}
