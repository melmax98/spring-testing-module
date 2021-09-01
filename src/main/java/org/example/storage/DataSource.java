package org.example.storage;

import lombok.Getter;
import org.example.model.Entity;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DataSource {
    private final Map<String, Entity> storage = new HashMap<>();
}
