package org.example.storage;

import lombok.Getter;
import org.example.model.Entity;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Database {
    private final Map<String, Entity> storage = new HashMap<>();
}
