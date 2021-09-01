package org.example.storage.dao;

import org.example.model.Entity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Dao {
    Entity save(org.example.model.Entity entity);

    Entity update(org.example.model.Entity entity);

    boolean delete(long entityId);

    Map<String, org.example.model.Entity> getStorage();

    default List<Entity> getPage(List<Entity> entities, int pageNum, int pageSize) {
        if (pageSize <= 0 || pageNum <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (pageNum - 1) * pageSize;
        if (entities == null || entities.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return entities.subList(fromIndex, Math.min(fromIndex + pageSize, entities.size()));
    }
}
